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

public class WsImportPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {

        if (GradleVersion.current().compareTo(GradleVersion.version("5.6")) < 0) {
            Logger logger = project.getLogger();
            logger.error("Plugin requires Gradle 5.6 or greater.");
            throw new IllegalStateException("Plugin requires Gradle 5.6 or greater.");
        }

        Configuration jaxWsTools = project.getConfigurations().create("jaxWsTools" );
        project.getDependencies().add( "jaxWsTools", "com.sun.xml.ws:jaxws-tools:2.3.2" );
        project.getDependencies().add( "implementation", "javax.xml.bind:jaxb-api:2.3.1" );
        project.getDependencies().add( "implementation", "javax.xml.ws:jaxws-api:2.3.1" );
        project.getDependencies().add( "implementation", "javax.jws:javax.jws-api:1.1" );

        TaskContainer tasks = project.getTasks();

        tasks.withType(WsImport.class, task -> {

            if (!project.getPlugins().hasPlugin(JavaPlugin.class)) {
                Logger logger = project.getLogger();
                logger.error("No java plugin detected. Enable java plugin");
                throw new IllegalStateException("No java plugin detected. Enable java plugin.");
            }

            JavaPluginConvention javaPluginConvention = project.getConvention().getPlugin(JavaPluginConvention.class);
            SourceSet javaMain = javaPluginConvention.getSourceSets().getByName(SourceSet.MAIN_SOURCE_SET_NAME);

            if (task.getJaxwsToolsConfiguration() == null) {
                task.setJaxwsToolsConfiguration(jaxWsTools);
            }

            javaMain.getJava().srcDir(task.getGeneratedSourceRoot());

            tasks.getByName(JavaPlugin.COMPILE_JAVA_TASK_NAME).dependsOn(task);
        });


    }
}
