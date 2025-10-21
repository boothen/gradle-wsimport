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

import com.sun.tools.ws.ant.WsImport2;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.types.FileSet;
import org.gradle.workers.WorkAction;

import uk.co.boothen.gradle.wsimport.log.ExtendedAntLoggingAdapter;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A work action implementation that executes WsImport2 tool to generate JAX-WS portable artifacts from WSDL files.
 * This class handles the configuration and execution of the WsImport2 tool with various options like:
 * - Source and destination directories for generated code
 * - WSDL location and package configuration
 * - Binding files processing
 * - Various WsImport flags and parameters
 * The class implements Gradle's WorkAction interface for parallel execution support.
 */
public abstract class WsImportWorkAction implements WorkAction<WsImportWorkParameters> {

    @Override
    public void execute() {
        WsImport2 wsImport2 = new WsImport2();
        Wsdl wsdl = getParameters().getWsdl().get();
        String wsdlSourceRoot = getParameters().getWsdlSourceRoot().get();
        String wsdlSourceFile = Util.mergePaths(wsdlSourceRoot, wsdl.getFile());

        wsImport2.setKeep(getParameters().getKeep().get());
        wsImport2.setDestdir(getParameters().getGeneratedClassesRoot().get());
        wsImport2.setSourcedestdir(getParameters().getGeneratedSourceRoot().get());
        wsImport2.setExtension(getParameters().getExtension().get());
        wsImport2.setVerbose(getParameters().getVerbose().get());
        wsImport2.setQuiet(getParameters().getQuiet().get());
        wsImport2.setDebug(getParameters().getDebug().get());
        wsImport2.setXnocompile(getParameters().getXnocompile().get());
        wsImport2.setWsdl(wsdlSourceFile);
        wsImport2.setEncoding(getParameters().getEncoding().get());
        wsImport2.setTarget(getParameters().getTarget().get());

        wsImport2.setXadditionalHeaders(getParameters().getXadditionalHeaders().get());
        wsImport2.setxNoAddressingDatabinding(getParameters().getXNoAddressingDatabinding().get());
        wsImport2.setXdebug(getParameters().getXdebug().get());

        if (!"".equals(wsdl.getPackageName())) {
            wsImport2.setPackage(wsdl.getPackageName());
        }

        if (!"".equals(wsdl.getWsdlLocation())) {
            wsImport2.setWsdllocation(wsdl.getWsdlLocation());
        }

        if (!"".equals(wsdl.getCatalog())) {
            wsImport2.setCatalog(new File(wsdl.getCatalog()));
        }

        // TODO: Expose some of this properties
//        wsImport2.setXauthfile();
//        wsImport2.setClientjar();
//        wsImport2.setdisableAuthenticator();
//        wsImport2.setGenerateJWS();
//        wsImport2.setImplDestDir();
//        wsImport2.setImplPortName();
//        wsImport2.setImplServiceName();
//        wsImport2.setXUseBaseResourceAndURLToLoadWSDL();

        for (String wsdlXjcArg : wsdl.getXjcargs()) {
            wsImport2.createXjcarg().setValue(wsdlXjcArg);
        }

        for (String extraArg : wsdl.getExtraArgs()) {
            wsImport2.createArg().setValue(extraArg);
            // When not forking the VM we have to set extraArgs as system properties directly.
            if (extraArg.startsWith("-J-D")  && extraArg.contains("=")) {
                String[] nameAndValue = extraArg.split("=");
                System.setProperty(nameAndValue[0].substring(4), nameAndValue[1]);
            }
        }

        for (File binding : bindingFiles(wsdl, wsdlSourceRoot)) {
            wsImport2.setBinding(binding.getAbsolutePath());
        }

        FileSet fileSet = new FileSet();
        Project project = new Project();
        project.setBaseDir(getParameters().getProjectRoot().get());
        project.addBuildListener(new ExtendedAntLoggingAdapter());
        fileSet.setProject(project);
        fileSet.setDir(getParameters().getGeneratedClassesRoot().get());
        wsImport2.setProject(project);
        wsImport2.addConfiguredProduces(fileSet);
        wsImport2.setTaskName("wsImport");
        wsImport2.execute();
    }


    /**
     * Resolves the list of binding files relative to the WSDL source root.
     *
     * @param wsdl           The WSDL configuration containing binding file paths
     * @param wsdlSourceRoot The root directory or URL for WSDL and binding files
     * @return A list of resolved File objects for the binding files
     * @throws IllegalArgumentException if any binding file path is null or empty
     */
    public List<File> bindingFiles(Wsdl wsdl, String wsdlSourceRoot) {
        if (wsdl == null) {
            throw new IllegalArgumentException("WSDL configuration cannot be null");
        }
        if (wsdlSourceRoot == null || wsdlSourceRoot.trim().isEmpty()) {
            throw new IllegalArgumentException("WSDL source root cannot be null or empty");
        }

        return wsdl.getBindingFiles()
                   .stream()
                   .filter(binding -> binding != null && !binding.trim().isEmpty())
                   .map(binding -> new File(Util.mergePaths(wsdlSourceRoot, binding)))
                   .collect(Collectors.toList());
    }
}
