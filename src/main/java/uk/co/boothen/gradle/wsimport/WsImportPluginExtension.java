package uk.co.boothen.gradle.wsimport;

import groovy.lang.Closure;

import org.gradle.util.ConfigureUtil;

import java.util.ArrayList;
import java.util.List;

public class WsImportPluginExtension {

    private String wsdlSourceRoot = "/src/main/resources/wsdl/";
    private String generatedSourceRoot = "/generated/src/wsdl/main";
    private String generatedClassesRoot = "/classes/main";

    private boolean keep = true;
    private boolean extension;
    private boolean verbose;
    private boolean quiet = true;
    private boolean debug;
    private boolean xnocompile = true;
    private boolean xadditionalHeaders;
    private boolean xNoAddressingDatabinding;
    private boolean xdebug;

    private String target = "2.2";
    private String encoding  = "UTF-8";
    private List<Wsdl> wsdls = new ArrayList<>();

    public String getWsdlSourceRoot() {
        return wsdlSourceRoot;
    }

    public String getGeneratedSourceRoot() {
        return generatedSourceRoot;
    }

    public String getGeneratedClassesRoot() {
        return generatedClassesRoot;
    }

    public boolean getKeep() {
        return keep;
    }

    public boolean getExtension() {
        return extension;
    }

    public boolean getVerbose() {
        return verbose;
    }

    public boolean getQuiet() {
        return quiet;
    }

    public boolean getDebug() {
        return debug;
    }

    public boolean getXnocompile() {
        return xnocompile;
    }

    public boolean getXadditionalHeaders() {
        return xadditionalHeaders;
    }

    public String getTarget() {
        return target;
    }

    public boolean getXNoAddressingDatabinding() {
        return xNoAddressingDatabinding;
    }

    public boolean getXdebug() {
        return xdebug;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setWsdlSourceRoot(String wsdlSourceRoot) {
        this.wsdlSourceRoot = wsdlSourceRoot;
    }

    public void setGeneratedSourceRoot(String generatedSourceRoot) {
        this.generatedSourceRoot = generatedSourceRoot;
    }

    public void setGeneratedClassesRoot(String generatedClassesRoot) {
        this.generatedClassesRoot = generatedClassesRoot;
    }

    public void setKeep(boolean keep) {
        this.keep = keep;
    }

    public void setExtension(boolean extension) {
        this.extension = extension;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    public void setQuiet(boolean quiet) {
        this.quiet = quiet;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public void setXnocompile(boolean xnocompile) {
        this.xnocompile = xnocompile;
    }

    public void setXadditionalHeaders(boolean xadditionalHeaders) {
        this.xadditionalHeaders = xadditionalHeaders;
    }

    public void setxNoAddressingDatabinding(boolean xNoAddressingDatabinding) {
        this.xNoAddressingDatabinding = xNoAddressingDatabinding;
    }

    public void setXdebug(boolean xdebug) {
        this.xdebug = xdebug;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public void setWsdl(String file) {
        Wsdl wsdl = new Wsdl(file);
        wsdls.add(wsdl);
    }

    public void wsdl(String file) {
        Wsdl wsdl = new Wsdl(file);
        wsdls.add(wsdl);
    }

    public void wsdl(String file, Closure<?> closure) {
        Wsdl wsdl = new Wsdl(file);
        wsdl = ConfigureUtil.configure(closure, wsdl);
        wsdls.add(wsdl);
    }

    public List<Wsdl> getWsdls() {
        return wsdls;
    }
}
