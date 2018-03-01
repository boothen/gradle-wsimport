package uk.co.boothen.gradle.wsimport;

import java.io.File;
import java.io.Serializable;

public class WsImportConfiguration implements Serializable {

    private final String sourceRoot;
    private final File generatedSourceRoot;
    private final File generatedClassesRoot;

    private final boolean keep;
    private final boolean extension;
    private final boolean verbose;
    private final boolean quiet;
    private final boolean debug;
    private final boolean xnocompile;
    private final Wsdl wsdl;


    public WsImportConfiguration(String sourceRoot,
                                 File generatedSourceRoot,
                                 File generatedClassesRoot,
                                 boolean keep,
                                 boolean extension,
                                 boolean verbose,
                                 boolean quiet,
                                 boolean debug,
                                 boolean xnocompile,
                                 Wsdl wsdl) {
        this.sourceRoot = sourceRoot;
        this.generatedSourceRoot = generatedSourceRoot;
        this.generatedClassesRoot = generatedClassesRoot;
        this.keep = keep;
        this.extension = extension;
        this.verbose = verbose;
        this.quiet = quiet;
        this.debug = debug;
        this.xnocompile = xnocompile;
        this.wsdl = wsdl;
    }

    public String getSourceRoot() {
        return sourceRoot;
    }

    public File getGeneratedSourceRoot() {
        return generatedSourceRoot;
    }

    public File getGeneratedClassesRoot() {
        return generatedClassesRoot;
    }

    public boolean isKeep() {
        return keep;
    }

    public boolean isExtension() {
        return extension;
    }

    public boolean isVerbose() {
        return verbose;
    }

    public boolean isQuiet() {
        return quiet;
    }

    public boolean isDebug() {
        return debug;
    }

    public boolean isXnocompile() {
        return xnocompile;
    }

    public Wsdl getWsdl() {
        return wsdl;
    }
}
