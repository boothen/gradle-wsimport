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
import org.gradle.api.UnknownTaskException;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.logging.Logger;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.JavaPluginExtension;
import org.gradle.api.tasks.SourceSet;
import org.gradle.util.GradleVersion;
import org.jetbrains.annotations.NotNull;

import java.io.File;


/**
 * A Gradle plugin that provides wsimport task functionality for generating JAX-WS portable artifacts.
 * This plugin adds configurations for JAX-WS tools, sets up source directories for generated code,
 * and configures wsimport task with customizable options through the wsimport extension.
 * Requires Gradle 9.0 or higher and the Java plugin to be applied.
 */
public class WsImportPlugin implements Plugin<@NotNull Project> {

    @Override
    public void apply(@NotNull Project project) {

        if (GradleVersion.current().compareTo(GradleVersion.version("9.0")) < 0) {
            Logger logger = project.getLogger();
            logger.error("Plugin requires Gradle 9.0 or greater.");
            throw new IllegalStateException("Plugin requires Gradle 9.0 or greater.");
        }

        project.getExtensions().add("wsimport", WsImportPluginExtension.class);

        Configuration jaxWsTools = project.getConfigurations().create("jaxWsTools");
        jaxWsTools.defaultDependencies(dependencies -> dependencies.add(project.getDependencies().create("com.sun.xml.ws:jaxws-tools:4.0.3")));

        project.afterEvaluate(action -> {
            WsImportPluginExtension wsImportPluginExtension = action.getExtensions().getByType(WsImportPluginExtension.class);

            if (wsImportPluginExtension.getIncludeDependencies()) {
                project.getDependencies().add("implementation", "jakarta.xml.bind:jakarta.xml.bind-api:4.0.4");
                project.getDependencies().add("implementation", "jakarta.xml.ws:jakarta.xml.ws-api:4.0.2");
                project.getDependencies().add("implementation", "jakarta.jws:jakarta.jws-api:3.0.0");
            }

            if (!project.getPlugins().hasPlugin(JavaPlugin.class)) {
                Logger logger = project.getLogger();
                logger.error("No java plugin detected. Enable java plugin");
                throw new IllegalStateException("No java plugin detected. Enable java plugin.");
            }

            String wsdlSourceRoot = wsImportPluginExtension.getWsdlSourceRoot().matches("(?i)^(http|https)://.*") ? wsImportPluginExtension.getWsdlSourceRoot()
                                                                                                                  : Util.mergePaths(project.getProjectDir().getAbsolutePath(),
                                                                                                                                    wsImportPluginExtension.getWsdlSourceRoot());

            File buildDir = project.getLayout().getBuildDirectory().getAsFile().get();
            File generatedSourceRoot = Util.mergeFile(buildDir, wsImportPluginExtension.getGeneratedSourceRoot());
            File generatedClassesRoot = Util.mergeFile(buildDir, wsImportPluginExtension.getGeneratedClassesRoot());

            JavaPluginExtension javaPluginExtension = project.getExtensions().getByType(JavaPluginExtension.class);
            SourceSet javaMain = javaPluginExtension.getSourceSets().getByName(SourceSet.MAIN_SOURCE_SET_NAME);
            javaMain.getJava().srcDir(generatedSourceRoot);

            WsImportTask wsImport = project.getTasks().register("wsImport", WsImportTask.class, wsImportTask -> {

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
                wsImportTask.getWsdls().set(wsImportPluginExtension.getWsdls());
                wsImportTask.getJaxwsToolsConfiguration().from(jaxWsTools);
                wsImportTask.getWsdlSourceRoot().set(wsdlSourceRoot);
                wsImportTask.getGeneratedSourceRoot().set(generatedSourceRoot);
                wsImportTask.getGeneratedClassesRoot().set(generatedClassesRoot);
                wsImportTask.getProjectDir().set(project.getProjectDir());

            }).getOrNull();

            project.getTasks().getByName(JavaPlugin.COMPILE_JAVA_TASK_NAME).dependsOn(wsImport);
            try {
                project.getTasks().getByName(javaMain.getSourcesJarTaskName()).dependsOn(wsImport);
            } catch (UnknownTaskException ignored) {

            }
        });
    }

}
