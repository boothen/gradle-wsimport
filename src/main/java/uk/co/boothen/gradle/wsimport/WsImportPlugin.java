package uk.co.boothen.gradle.wsimport;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.logging.Logger;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.JavaPluginConvention;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.TaskContainer;

public class WsImportPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {

        Logger logger = project.getLogger();

        if (!project.getPlugins().hasPlugin(JavaPlugin.class)) {
            logger.error("No java plugin detected. Enable java plugin");
            throw new IllegalStateException("No java plugin detected. Enable java plugin.");
        }

        Configuration jaxWsTools = project.getConfigurations().create("jaxWsTools" );
        project.getDependencies().add( "jaxWsTools", "com.sun.xml.ws:jaxws-tools:2.2.10" );

        TaskContainer tasks = project.getTasks();

        tasks.withType(WsImport.class, task -> {

            JavaPluginConvention javaPluginConvention = project.getConvention().getPlugin(JavaPluginConvention.class);
            SourceSet javaMain = javaPluginConvention.getSourceSets().getByName(SourceSet.MAIN_SOURCE_SET_NAME);

            if( task.getJaxwsToolsConfiguration() == null ) {
                task.setJaxwsToolsConfiguration( jaxWsTools );
            }

            javaMain.getJava().srcDir(task.getGeneratedSourceRoot());

            tasks.getByName(JavaPlugin.COMPILE_JAVA_TASK_NAME).dependsOn(task);
        });


    }
}
