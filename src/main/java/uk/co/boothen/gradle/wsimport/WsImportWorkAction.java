package uk.co.boothen.gradle.wsimport;

import com.sun.tools.ws.ant.WsImport2;

import org.apache.tools.ant.types.Commandline;
import org.gradle.workers.WorkAction;

import java.io.File;

public abstract class WsImportWorkAction implements WorkAction<WsImportWorkParameters> {

    @Override
    public void execute() {
        WsImport2 wsImport2 = new WsImport2();
        WsImportConfiguration wsImportConfiguration = getParameters().getWsImportConfiguration().get();
        wsImport2.setKeep(wsImportConfiguration.isKeep());
        wsImport2.setDestdir(wsImportConfiguration.getGeneratedClassesRoot());
        wsImport2.setSourcedestdir(wsImportConfiguration.getGeneratedSourceRoot());
        wsImport2.setExtension(wsImportConfiguration.isExtension());
        wsImport2.setVerbose(wsImportConfiguration.isVerbose());
        wsImport2.setQuiet(wsImportConfiguration.isQuiet());
        wsImport2.setDebug(wsImportConfiguration.isDebug());
        wsImport2.setXnocompile(wsImportConfiguration.isXnocompile());
        wsImport2.setWsdl(wsImportConfiguration.getSourceFile());
        wsImport2.setPackage(wsImportConfiguration.getWsdl().getPackageName());
        if (!"".equals(wsImportConfiguration.getEncoding())) {
            wsImport2.setEncoding(wsImportConfiguration.getEncoding());
        }if (!"".equals(wsImportConfiguration.getTarget())) {
            wsImport2.setTarget(wsImportConfiguration.getTarget());
        }

        wsImport2.setXadditionalHeaders(wsImportConfiguration.isXadditionalHeaders());
        wsImport2.setxNoAddressingDatabinding(wsImportConfiguration.isxNoAddressingDatabinding());
        wsImport2.setXdebug(wsImportConfiguration.isXdebug());
        if (!"".equals(wsImportConfiguration.getWsdl().getWsdlLocation())) {
            wsImport2.setWsdllocation(wsImportConfiguration.getWsdl().getWsdlLocation());
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

        for (String wsdlXjcArg : wsImportConfiguration.getWsdl().getXjcargs()) {
            wsImport2.createXjcarg().setValue(wsdlXjcArg);
        }

        Commandline.Argument extraArgs = wsImport2.createArg();
        for (String extraArg : wsImportConfiguration.getWsdl().getExtraArgs()) {
            extraArgs.setValue(extraArg);
        }

        for (File binding : wsImportConfiguration.bindingFiles()) {
            wsImport2.setBinding(binding.getAbsolutePath());
        }

        wsImport2.execute();
    }
}
