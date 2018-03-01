package uk.co.boothen.gradle.wsimport;

import com.sun.tools.ws.ant.WsImport2;

import org.apache.tools.ant.types.Commandline;

import javax.inject.Inject;

public class WsImportRunnable implements Runnable {

    private final WsImportConfiguration wsImportConfiguration;

    @Inject
    public WsImportRunnable(WsImportConfiguration wsImportConfiguration) {
        this.wsImportConfiguration = wsImportConfiguration;
    }

    @Override
    public void run() {
        WsImport2 wsImport2 = new WsImport2();

        wsImport2.setKeep(wsImportConfiguration.isKeep());
        wsImport2.setDestdir(wsImportConfiguration.getGeneratedClassesRoot());
        wsImport2.setSourcedestdir(wsImportConfiguration.getGeneratedSourceRoot());
        wsImport2.setExtension(wsImportConfiguration.isExtension());
        wsImport2.setVerbose(wsImportConfiguration.isVerbose());
        wsImport2.setQuiet(wsImportConfiguration.isQuiet());
        wsImport2.setDebug(wsImportConfiguration.isDebug());
        wsImport2.setXnocompile(wsImportConfiguration.isXnocompile());
        wsImport2.setWsdl(wsImportConfiguration.getSourceRoot() + wsImportConfiguration.getWsdl().getFile());
        wsImport2.setPackage(wsImportConfiguration.getWsdl().getPackageName());
        Commandline.Argument xjcarg = wsImport2.createXjcarg();
        for (String wsdlXlcArg : wsImportConfiguration.getWsdl().getXjcargs()) {
            xjcarg.setValue(wsdlXlcArg);
        }
        for (String binding : wsImportConfiguration.getWsdl().getBindingFiles()) {
            wsImport2.setBinding(binding);
        }
        wsImport2.execute();
    }
}
