package uk.co.boothen.gradle.wsimport;

import org.gradle.api.provider.Property;
import org.gradle.workers.WorkParameters;

import java.io.File;

public interface WsImportWorkParameters extends WorkParameters {

    Property<Boolean> getKeep();
    Property<Boolean> getExtension();
    Property<Boolean> getVerbose();
    Property<Boolean> getQuiet();
    Property<Boolean> getDebug();
    Property<Boolean> getXnocompile();
    Property<Boolean> getXadditionalHeaders();
    Property<Boolean> getXNoAddressingDatabinding();
    Property<Boolean> getXdebug();

    Property<String> getTarget();
    Property<String> getEncoding();
    Property<String> getWsdlSourceRoot();
    Property<File> getGeneratedSourceRoot();
    Property<File> getGeneratedClassesRoot();
    Property<File> getProjectRoot();
    Property<Wsdl> getWsdl();

}
