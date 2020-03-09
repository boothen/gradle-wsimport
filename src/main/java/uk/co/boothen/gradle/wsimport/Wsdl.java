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

public class Wsdl implements Serializable {

    private String file;
    private String packageName = "";
    private String wsdlLocation = "";
    private List<String> bindingFiles = new ArrayList<>();
    private List<String> xjcargs = new ArrayList<>();
    private List<String> extraArgs = new ArrayList<>();
    private String catalog = "";

    public Wsdl(String file) {
        this.file = file;
    }

    public String getFile() {
        return file;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    @Input
    public void packageName(String packageName) {
        this.packageName = packageName;
    }

    public String getWsdlLocation() {
        return wsdlLocation;
    }

    public void setWsdlLocation(String wsdlLocation) {
        this.wsdlLocation = wsdlLocation;
    }

    @Input
    public void wsdlLocation(String wsdlLocation) {
        this.wsdlLocation = wsdlLocation;
    }

    public List<String> getBindingFiles() {
        return bindingFiles;
    }

    public void setBindingFiles(List<String> bindingFiles) {
        this.bindingFiles = bindingFiles;
    }

    @Input
    public void bindingFile(String bindingFile) {
        bindingFiles.add(bindingFile);
    }

    public List<String> getXjcargs() {
        return xjcargs;
    }

    public void setXjcargs(List<String> xjcargs) {
        this.xjcargs = xjcargs;
    }

    @Input
    public void xjcarg(String xjcarg) {
        this.xjcargs.add(xjcarg);
    }

    public List<String> getExtraArgs() {
        return extraArgs;
    }

    public void setExtraArgs(List<String> extraArgs) {
        this.extraArgs = extraArgs;
    }

    @Input
    public void extraArg(String extraArg) {
        this.extraArgs.add(extraArg);
    }

    public String getCatalog() {
        return catalog;
    }

    @Input
    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }
}
