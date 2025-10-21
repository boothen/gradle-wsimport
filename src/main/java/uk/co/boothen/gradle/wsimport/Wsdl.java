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

import org.gradle.api.tasks.Input;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a WSDL file configuration for web service code generation.
 * This class holds settings for WSDL processing including package names,
 * binding files, and additional arguments for the wsimport tool.
 */
public class Wsdl implements Serializable {

    private String file;
    private String packageName = "";
    private String wsdlLocation = "";
    private List<String> bindingFiles = new ArrayList<>();
    private List<String> xjcargs = new ArrayList<>();
    private List<String> extraArgs = new ArrayList<>();
    private String catalog = "";

    /**
     * Creates a new WSDL configuration with the specified file path.
     *
     * @param file The path to the WSDL file
     */
    public Wsdl(String file) {
        this.file = file;
    }

    /**
     * Gets the WSDL file path.
     *
     * @return The path to the WSDL file
     */
    @Input
    public String getFile() {
        return file;
    }

    /**
     * Sets the WSDL file path.
     *
     * @param file The path to the WSDL file
     */
    public void setFile(String file) {
        this.file = file;
    }

    /**
     * Gets the target package name for generated classes.
     *
     * @return The package name for generated classes
     */
    @Input
    public String getPackageName() {
        return packageName;
    }

    /**
     * Sets the target package name for generated classes.
     *
     * @param packageName The package name for generated classes
     */
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    /**
     * Sets the target package name for generated classes.
     *
     * @param packageName The package name for generated classes
     */
    public void packageName(String packageName) {
        this.packageName = packageName;
    }

    /**
     * Gets the WSDL location URL.
     *
     * @return The WSDL location URL
     */
    @Input
    public String getWsdlLocation() {
        return wsdlLocation;
    }

    /**
     * Sets the WSDL location URL.
     *
     * @param wsdlLocation The WSDL location URL
     */
    public void setWsdlLocation(String wsdlLocation) {
        this.wsdlLocation = wsdlLocation;
    }

    /**
     * Sets the WSDL location URL.
     *
     * @param wsdlLocation The WSDL location URL
     */
    public void wsdlLocation(String wsdlLocation) {
        this.wsdlLocation = wsdlLocation;
    }

    /**
     * Gets the list of binding files.
     *
     * @return The list of binding files
     */
    @Input
    public List<String> getBindingFiles() {
        return bindingFiles;
    }

    /**
     * Sets the list of binding files.
     *
     * @param bindingFiles The list of binding files
     */
    public void setBindingFiles(List<String> bindingFiles) {
        this.bindingFiles = bindingFiles;
    }

    /**
     * Adds a single binding file to the list.
     *
     * @param bindingFile The binding file to add
     */
    public void bindingFile(String bindingFile) {
        bindingFiles.add(bindingFile);
    }

    /**
     * Gets the list of XJC arguments.
     *
     * @return The list of XJC arguments
     */
    @Input
    public List<String> getXjcargs() {
        return xjcargs;
    }

    /**
     * Sets the list of XJC arguments.
     *
     * @param xjcargs The list of XJC arguments
     */
    public void setXjcargs(List<String> xjcargs) {
        this.xjcargs = xjcargs;
    }

    /**
     * Adds a single XJC argument to the list.
     *
     * @param xjcarg The XJC argument to add
     */
    public void xjcarg(String xjcarg) {
        this.xjcargs.add(xjcarg);
    }

    /**
     * Gets the list of additional arguments.
     *
     * @return The list of additional arguments
     */
    @Input
    public List<String> getExtraArgs() {
        return extraArgs;
    }

    /**
     * Sets the list of additional arguments.
     *
     * @param extraArgs The list of additional arguments
     */
    public void setExtraArgs(List<String> extraArgs) {
        this.extraArgs = extraArgs;
    }

    /**
     * Adds a single additional argument to the list.
     *
     * @param extraArg The additional argument to add
     */
    public void extraArg(String extraArg) {
        this.extraArgs.add(extraArg);
    }

    /**
     * Gets the catalog file path.
     *
     * @return The catalog file path
     */
    @Input
    public String getCatalog() {
        return catalog;
    }

    /**
     * Sets the catalog file path.
     *
     * @param catalog The catalog file path
     */
    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }
}
