package uk.co.boothen.gradle.wsimport;

import groovy.lang.Closure;

import org.gradle.api.DefaultTask;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.TaskAction;
import org.gradle.util.ConfigureUtil;
import org.gradle.workers.WorkerExecutor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class WsImport extends DefaultTask {

    private Configuration jaxwsToolsConfiguration;
    private boolean keep;
    private boolean extension;
    private boolean verbose;
    private boolean quiet = true;
    private boolean debug;
    private boolean xnocompile;
    private List<Wsdl> wsdls = new ArrayList<>();
    private String wsdlSourceRoot = getProject().getProjectDir().getAbsolutePath() + "/src/main/resources/wsdl/";
    private File generatedSourceRoot = new File(getProject().getBuildDir() + "/generated/src/wsdl/main");
    private File generatedClassesRoot = new File(getProject().getBuildDir() + "/classes/main");

    @Input
    public boolean isKeep() {
        return keep;
    }

    public void setKeep(boolean keep) {
        this.keep = keep;
    }


    @Input
    public boolean isExtension() {
        return extension;
    }

    public void setExtension(boolean extension) {
        this.extension = extension;
    }


    @Input
    public boolean isVerbose() {
        return verbose;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }


    @Input
    public boolean isQuiet() {
        return quiet;
    }

    public void setQuiet(boolean quiet) {
        this.quiet = quiet;
    }

    @Input
    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    @Input
    public boolean isXnocompile() {
        return xnocompile;
    }


    public void setXnocompile(boolean xnocompile) {
        this.xnocompile = xnocompile;
    }

    @Input
    public void wsdl(String file, Closure<?> closure) {
        Wsdl wsdl = new Wsdl(file);
        wsdl = ConfigureUtil.configure(closure, wsdl);
        wsdls.add(wsdl);
    }

    @InputFiles
    public Configuration getJaxwsToolsConfiguration() {
        return jaxwsToolsConfiguration;
    }

    public void setJaxwsToolsConfiguration( Configuration jaxwsToolsConfiguration ) {
        this.jaxwsToolsConfiguration = jaxwsToolsConfiguration;
    }

    @OutputDirectory
    public File getGeneratedSourceRoot() {
        return generatedSourceRoot;
    }

    public void setGeneratedSourceRoot(File generatedSourceRoot) {
        this.generatedSourceRoot = generatedSourceRoot;
    }

    @OutputDirectory
    public File getGeneratedClassesRoot() {
        return generatedClassesRoot;
    }

    public void setGeneratedClassesRoot(File generatedClassesRoot) {
        this.generatedClassesRoot = generatedClassesRoot;
    }

    @TaskAction
    public void taskAction() {
        for (Wsdl wsdl : wsdls) {
            getWorkerExecutor().submit(WsImportRunnable.class, workerConfiguration -> {
                workerConfiguration.setDisplayName("Importing WSDL");
                workerConfiguration.setParams(new WsImportConfiguration(wsdlSourceRoot, generatedSourceRoot, generatedClassesRoot, keep, extension, verbose, quiet, debug, xnocompile, wsdl));
                // TODO: Works in v4 onwards
//                workerConfiguration.setIsolationMode(IsolationMode.CLASSLOADER);
                workerConfiguration.classpath(jaxwsToolsConfiguration.getFiles());
            });
        }
    }

    @Inject
    protected WorkerExecutor getWorkerExecutor() {
        throw new UnsupportedOperationException();
    }
}
