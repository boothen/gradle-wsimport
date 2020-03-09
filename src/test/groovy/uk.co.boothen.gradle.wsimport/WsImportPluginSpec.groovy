package uk.co.boothen.gradle.wsimport

import org.gradle.api.Project
import org.gradle.api.plugins.PluginManager
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

class WsImportPluginSpec extends Specification {

    def 'Plugin WsImport should be applied'() {
        when:
            Project project = ProjectBuilder.builder().build()
            PluginManager pluginManager = project.getPluginManager()
            pluginManager.apply("uk.co.boothen.gradle.wsimport")
        then:
            pluginManager.hasPlugin("uk.co.boothen.gradle.wsimport")
            project.getExtensions().getByType(WsImportPluginExtension.class) instanceof WsImportPluginExtension
    }

}
