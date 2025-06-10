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

import groovy.lang.Closure;

import org.gradle.api.Action;

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
    private boolean includeDependencies = true;

    private String target = "3.0";
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

    public boolean getIncludeDependencies() {
        return includeDependencies;
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

    public void setIncludeDependencies(boolean includeDependencies) {
        this.includeDependencies = includeDependencies;
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
        wsdl(file, report -> {
            closure.setDelegate(report);
            closure.call(report);
        });
    }

    public void wsdl(String file, Action<Wsdl> action) {
        Wsdl wsdl = new Wsdl(file);
        action.execute(wsdl);
        wsdls.add(wsdl);
    }

    public List<Wsdl> getWsdls() {
        return wsdls;
    }
}
