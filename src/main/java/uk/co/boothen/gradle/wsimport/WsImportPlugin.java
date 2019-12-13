/*
 * Copyright 2019 Boothen Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.co.boothen.gradle.wsimport;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.logging.Logger;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.JavaPluginConvention;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.util.GradleVersion;

import java.io.File;

public class WsImportPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {

        if (GradleVersion.current().compareTo(GradleVersion.version("5.6")) < 0) {
            Logger logger = project.getLogger();
            logger.error("Plugin requires Gradle 5.6 or greater.");
            throw new IllegalStateException("Plugin requires Gradle 5.6 or greater.");
        }

        project.getExtensions().add("wsimport", WsImportPluginExtension.class);

        project.afterEvaluate(action -> {
            WsImportPluginExtension wsImportPluginExtension = action.getExtensions().getByType(WsImportPluginExtension.class);

            Configuration jaxWsTools = project.getConfigurations().create("jaxWsTools");
            project.getDependencies().add("jaxWsTools", "com.sun.xml.ws:jaxws-tools:2.3.2");
            project.getDependencies().add("implementation", "javax.xml.bind:jaxb-api:2.3.1");
            project.getDependencies().add("implementation", "javax.xml.ws:jaxws-api:2.3.1");
            project.getDependencies().add("implementation", "javax.jws:javax.jws-api:1.1");

            if (!project.getPlugins().hasPlugin(JavaPlugin.class)) {
                Logger logger = project.getLogger();
                logger.error("No java plugin detected. Enable java plugin");
                throw new IllegalStateException("No java plugin detected. Enable java plugin.");
            }

            JavaPluginConvention javaPluginConvention = project.getConvention().getPlugin(JavaPluginConvention.class);
            SourceSet javaMain = javaPluginConvention.getSourceSets().getByName(SourceSet.MAIN_SOURCE_SET_NAME);

            String wsdlSourceRoot = Util.mergePaths(project.getProjectDir().getAbsolutePath(), wsImportPluginExtension.getWsdlSourceRoot());
            File generatedSourceRoot = Util.mergeFile(project.getBuildDir(), wsImportPluginExtension.getGeneratedSourceRoot());
            File generatedClassesRoot = Util.mergeFile(project.getBuildDir(), wsImportPluginExtension.getGeneratedClassesRoot());
            javaMain.getJava().srcDir(generatedSourceRoot);

            int count = 1;
            TaskContainer tasks = project.getTasks();
            for (Wsdl wsdl : wsImportPluginExtension.getWsdls()) {
                WsImportTask wsImportTask = project.getTasks().create("wsImport" + count++, WsImportTask.class);
                wsImportTask.getKeep().set(wsImportPluginExtension.getKeep());
                wsImportTask.getExtension().set(wsImportPluginExtension.getExtension());
                wsImportTask.getVerbose().set(wsImportPluginExtension.getVerbose());
                wsImportTask.getQuiet().set(wsImportPluginExtension.getQuiet());
                wsImportTask.getDebug().set(wsImportPluginExtension.getDebug());
                wsImportTask.getXnocompile().set(wsImportPluginExtension.getXnocompile());
                wsImportTask.getXadditionalHeaders().set(wsImportPluginExtension.getXadditionalHeaders());
                wsImportTask.getTarget().set(wsImportPluginExtension.getTarget());
                wsImportTask.getEncoding().set(wsImportPluginExtension.getEncoding());
                wsImportTask.getXNoAddressingDatabinding().set(wsImportPluginExtension.getXNoAddressingDatabinding());
                wsImportTask.getXdebug().set(wsImportPluginExtension.getXdebug());
                wsImportTask.getWsdl().set(wsdl);
                wsImportTask.getJaxwsToolsConfiguration().set(jaxWsTools);
                wsImportTask.getWsdlSourceRoot().set(wsdlSourceRoot);
                wsImportTask.getGeneratedSourceRoot().set(generatedSourceRoot);
                wsImportTask.getGeneratedClassesRoot().set(generatedClassesRoot);
                tasks.getByName(JavaPlugin.COMPILE_JAVA_TASK_NAME).dependsOn(wsImportTask);
            }

        });
    }

}
