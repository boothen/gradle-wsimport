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
import org.apache.tools.ant.types.Commandline;
import org.apache.tools.ant.types.FileSet;
import org.gradle.workers.WorkAction;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

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

        // TODO: Expose some of this properties
//        wsImport2.setXauthfile();
//        wsImport2.setCatalog();
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

        Commandline.Argument extraArgs = wsImport2.createArg();
        for (String extraArg : wsdl.getExtraArgs()) {
            extraArgs.setValue(extraArg);
        }

        for (File binding : bindingFiles(wsdl, wsdlSourceRoot)) {
            wsImport2.setBinding(binding.getAbsolutePath());
        }

        FileSet fileSet = new FileSet();
        Project project = new Project();
        project.setBaseDir(getParameters().getProjectRoot().get());
        fileSet.setProject(project);
        fileSet.setDir(getParameters().getGeneratedClassesRoot().get());
        wsImport2.setProject(project);
        wsImport2.addConfiguredProduces(fileSet);
        wsImport2.execute();
    }

    public List<File> bindingFiles(Wsdl wsdl, String wsdlSourceRoot) {
        return wsdl.getBindingFiles()
                   .stream()
                   .map(binding -> new File(Util.mergePaths(wsdlSourceRoot, binding)))
                   .collect(Collectors.toList());
    }
}
