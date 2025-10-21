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

/**
 * Extension for configuring the wsimport Gradle plugin.
 * Provides configuration options for wsimport tool execution including:
 * - Source and output directory configuration
 * - WSDL processing options
 * - Compilation and debugging settings
 * - Character encoding options
 */
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

    /**
     * Gets the source directory containing WSDL files.
     *
     * @return The WSDL source directory path
     */
    public String getWsdlSourceRoot() {
        return wsdlSourceRoot;
    }

    /**
     * Gets the directory where generated source files will be placed.
     *
     * @return The generated source directory path
     */
    public String getGeneratedSourceRoot() {
        return generatedSourceRoot;
    }

    /**
     * Gets the directory where compiled class files will be placed.
     *
     * @return The generated classes directory path
     */
    public String getGeneratedClassesRoot() {
        return generatedClassesRoot;
    }


    /**
     * Gets whether to keep generated source files.
     *
     * @return True if generated sources should be kept
     */
    public boolean getKeep() {
        return keep;
    }

    /**
     * Gets whether SOAP extensions are enabled.
     *
     * @return True if extensions are enabled
     */
    public boolean getExtension() {
        return extension;
    }

    /**
     * Gets whether verbose output is enabled.
     *
     * @return True if verbose output is enabled
     */
    public boolean getVerbose() {
        return verbose;
    }

    /**
     * Gets whether quiet mode is enabled (suppressing non-error messages).
     *
     * @return True if quiet mode is enabled
     */
    public boolean getQuiet() {
        return quiet;
    }

    /**
     * Gets whether debug output is enabled.
     *
     * @return True if debug output is enabled
     */
    public boolean getDebug() {
        return debug;
    }

    /**
     * Gets whether compilation of generated source files is disabled.
     *
     * @return True if compilation is disabled
     */
    public boolean getXnocompile() {
        return xnocompile;
    }

    /**
     * Gets whether mapping of additional headers is enabled.
     *
     * @return True if additional headers mapping is enabled
     */
    public boolean getXadditionalHeaders() {
        return xadditionalHeaders;
    }

    /**
     * Gets the target version for generated source files.
     *
     * @return The target version string
     */
    public String getTarget() {
        return target;
    }

    /**
     * Gets whether addressing databinding is disabled.
     *
     * @return True if addressing databinding is disabled
     */
    public boolean getXNoAddressingDatabinding() {
        return xNoAddressingDatabinding;
    }

    /**
     * Gets whether wsimport debug messages are enabled.
     *
     * @return True if wsimport debug is enabled
     */
    public boolean getXdebug() {
        return xdebug;
    }

    /**
     * Gets the character encoding used for generated source files.
     *
     * @return The character encoding name
     */
    public String getEncoding() {
        return encoding;
    }

    /**
     * Gets whether JAX-WS dependencies are automatically included.
     *
     * @return True if dependencies are included
     */
    public boolean getIncludeDependencies() {
        return includeDependencies;
    }

    /**
     * Sets the source directory containing WSDL files.
     *
     * @param wsdlSourceRoot The WSDL source directory path
     */
    public void setWsdlSourceRoot(String wsdlSourceRoot) {
        this.wsdlSourceRoot = wsdlSourceRoot;
    }

    /**
     * Sets the directory where generated source files will be placed.
     *
     * @param generatedSourceRoot The generated source directory path
     */
    public void setGeneratedSourceRoot(String generatedSourceRoot) {
        this.generatedSourceRoot = generatedSourceRoot;
    }

    /**
     * Sets the directory where compiled class files will be placed.
     *
     * @param generatedClassesRoot The generated classes directory path
     */
    public void setGeneratedClassesRoot(String generatedClassesRoot) {
        this.generatedClassesRoot = generatedClassesRoot;
    }

    /**
     * Sets whether to keep generated source files.
     *
     * @param keep True to keep generated sources
     */
    public void setKeep(boolean keep) {
        this.keep = keep;
    }

    /**
     * Sets whether to enable SOAP extensions.
     *
     * @param extension True to enable extensions
     */
    public void setExtension(boolean extension) {
        this.extension = extension;
    }

    /**
     * Sets whether to enable verbose output.
     *
     * @param verbose True to enable verbose output
     */
    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    /**
     * Sets whether to suppress non-error messages.
     *
     * @param quiet True to enable quiet mode
     */
    public void setQuiet(boolean quiet) {
        this.quiet = quiet;
    }

    /**
     * Sets whether to enable debug output.
     *
     * @param debug True to enable debug output
     */
    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    /**
     * Sets whether to disable compilation of generated source files.
     *
     * @param xnocompile True to disable compilation
     */
    public void setXnocompile(boolean xnocompile) {
        this.xnocompile = xnocompile;
    }

    /**
     * Sets whether to map headers not bound to request or response messages to Java method parameters.
     *
     * @param xadditionalHeaders True to enable additional headers
     */
    public void setXadditionalHeaders(boolean xadditionalHeaders) {
        this.xadditionalHeaders = xadditionalHeaders;
    }

    /**
     * Sets whether to disable addressing databinding.
     *
     * @param xNoAddressingDatabinding True to disable addressing databinding
     */
    public void setxNoAddressingDatabinding(boolean xNoAddressingDatabinding) {
        this.xNoAddressingDatabinding = xNoAddressingDatabinding;
    }

    /**
     * Sets whether to enable debug messages for wsimport.
     *
     * @param xdebug True to enable wsimport debug
     */
    public void setXdebug(boolean xdebug) {
        this.xdebug = xdebug;
    }

    /**
     * Sets the target version for generated source files.
     *
     * @param target The target version string
     */
    public void setTarget(String target) {
        this.target = target;
    }

    /**
     * Sets the encoding to use for generated source files.
     *
     * @param encoding The character encoding name
     */
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    /**
     * Sets whether to include JAX-WS dependencies automatically.
     *
     * @param includeDependencies True to include dependencies
     */
    public void setIncludeDependencies(boolean includeDependencies) {
        this.includeDependencies = includeDependencies;
    }

    /**
     * Adds a WSDL file to be processed.
     *
     * @param file The path to the WSDL file
     */
    public void setWsdl(String file) {
        Wsdl wsdl = new Wsdl(file);
        wsdls.add(wsdl);
    }

    /**
     * Adds a WSDL file to be processed.
     *
     * @param file The path to the WSDL file
     */
    public void wsdl(String file) {
        Wsdl wsdl = new Wsdl(file);
        wsdls.add(wsdl);
    }

    /**
     * Adds and configures a WSDL file using Groovy closure.
     *
     * @param file    The path to the WSDL file
     * @param closure The configuration closure
     */
    public void wsdl(String file, Closure<?> closure) {
        wsdl(file, report -> {
            closure.setDelegate(report);
            closure.call(report);
        });
    }

    /**
     * Adds and configures a WSDL file using an Action.
     *
     * @param file   The path to the WSDL file
     * @param action The configuration action
     */
    public void wsdl(String file, Action<Wsdl> action) {
        Wsdl wsdl = new Wsdl(file);
        action.execute(wsdl);
        wsdls.add(wsdl);
    }

    /**
     * Gets the list of configured WSDL files.
     *
     * @return List of WSDL configurations
     */
    public List<Wsdl> getWsdls() {
        return wsdls;
    }
}
