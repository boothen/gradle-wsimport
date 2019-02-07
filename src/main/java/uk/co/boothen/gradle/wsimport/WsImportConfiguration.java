package uk.co.boothen.gradle.wsimport;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

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
    private final boolean xadditionalHeaders;
    private final boolean xNoAddressingDatabinding;
    private final boolean xdebug;

    private final String target;
    private final String encoding;
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
                                 boolean xadditionalHeaders,
                                 boolean xNoAddressingDatabinding,
                                 boolean xdebug,
                                 String target,
                                 String encoding, Wsdl wsdl) {
        this.sourceRoot = sourceRoot;
        this.generatedSourceRoot = generatedSourceRoot;
        this.generatedClassesRoot = generatedClassesRoot;
        this.keep = keep;
        this.extension = extension;
        this.verbose = verbose;
        this.quiet = quiet;
        this.debug = debug;
        this.xnocompile = xnocompile;
        this.xadditionalHeaders = xadditionalHeaders;
        this.xNoAddressingDatabinding = xNoAddressingDatabinding;
        this.xdebug = xdebug;
        this.target = target;
        this.encoding = encoding;
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

    public boolean isXadditionalHeaders() {
        return xadditionalHeaders;
    }

    public String getTarget() {
        return target;
    }

    public boolean isxNoAddressingDatabinding() {
        return xNoAddressingDatabinding;
    }

    public boolean isXdebug() {
        return xdebug;
    }

    public Wsdl getWsdl() {
        return wsdl;
    }

    public List<File> bindingFiles() {
        return wsdl.getBindingFiles()
            .stream()
            .map(binding -> new File(Util.mergePaths(sourceRoot, binding)))
            .peek(System.out::println)
            .collect(Collectors.toList());
    }

    public String getSourceFile() {
        return Util.mergePaths(sourceRoot, wsdl.getFile());
    }

    public String getEncoding() {
        return encoding;
    }
}
