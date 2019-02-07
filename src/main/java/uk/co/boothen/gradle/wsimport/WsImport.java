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
    private boolean xadditionalHeaders;
    private boolean xnoAddressingDatabinding;
    private boolean xdebug;
    private String target = "2.2";
    private String encoding = "UTF-8";
    private List<Wsdl> wsdls = new ArrayList<>();
    private String wsdlSourceRoot = Util.mergePaths(getProject().getProjectDir().getAbsolutePath(),"/src/main/resources/wsdl/");
    private File generatedSourceRoot = Util.mergeFile(getProject().getBuildDir(), "/generated/src/wsdl/main");
    private File generatedClassesRoot = Util.mergeFile(getProject().getBuildDir(), "/classes/main");

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
    public boolean isXadditionalHeaders() {
        return xadditionalHeaders;
    }

    public void setXadditionalHeaders(boolean xadditionalHeaders) {
        this.xadditionalHeaders = xadditionalHeaders;
    }

    @Input
    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    @Input
    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    @Input
    public boolean isXnoAddressingDatabinding() {
        return xnoAddressingDatabinding;
    }

    public void setXnoAddressingDatabinding(boolean xnoAddressingDatabinding) {
        this.xnoAddressingDatabinding = xnoAddressingDatabinding;
    }

    @Input
    public boolean isXdebug() {
        return xdebug;
    }

    public void setXdebug(boolean xdebug) {
        this.xdebug = xdebug;
    }

    @Input
    public void setWsdl(String file) {
        Wsdl wsdl = new Wsdl(file);
        wsdls.add(wsdl);
    }

    @Input
    public void wsdl(String file) {
        Wsdl wsdl = new Wsdl(file);
        wsdls.add(wsdl);
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

    public void setGeneratedSourceRoot(String generatedSourceRoot) {
        this.generatedSourceRoot = Util.mergeFile(getProject().getBuildDir(), generatedSourceRoot);
    }

    @OutputDirectory
    public File getGeneratedClassesRoot() {
        return generatedClassesRoot;
    }

    public void setGeneratedClassesRoot(String generatedClassesRoot) {
        this.generatedClassesRoot = Util.mergeFile(getProject().getBuildDir(), generatedClassesRoot);
    }


    @Input
    public String getWsdlSourceRoot() {
        return wsdlSourceRoot;
    }

    public void setWsdlSourceRoot(String wsdlSourceRoot) {
        this.wsdlSourceRoot = Util.mergePaths(this.getProject().getProjectDir().getAbsolutePath(), wsdlSourceRoot);
    }

    @Inject
    protected WorkerExecutor getWorkerExecutor() {
        throw new UnsupportedOperationException();
    }

    @TaskAction
    public void taskAction() {
        for (Wsdl wsdl : wsdls) {
            getWorkerExecutor().submit(WsImportRunnable.class, workerConfiguration -> {
                workerConfiguration.setDisplayName("Importing WSDL");
                workerConfiguration.setParams(new WsImportConfiguration(wsdlSourceRoot,
                                                                        generatedSourceRoot,
                                                                        generatedClassesRoot,
                                                                        keep,
                                                                        extension,
                                                                        verbose,
                                                                        quiet,
                                                                        debug,
                                                                        xnocompile,
                                                                        xadditionalHeaders,
                                                                        xnoAddressingDatabinding,
                                                                        xdebug,
                                                                        target,
                        encoding, wsdl));
                workerConfiguration.classpath(jaxwsToolsConfiguration.getFiles());
            });
        }
    }

}
