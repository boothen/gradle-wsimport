Gradle WsImport Plugin
======================


[![Build Status](https://secure.travis-ci.org/boothen/gradle-wsimport.png)](http://travis-ci.org/boothen/gradle-wsimport)


Gradle plugin that wraps the Ant WSImport allowing for a simple and clean DSL in Gradle.
## Using the plugin

```groovy
plugins {
  id "uk.co.boothen.gradle.wsimport" version "0.1"
}
```

## Simplest Example
```groovy
apply plugin: 'uk.co.boothen.gradle.wsimport'

task wsimport(type: uk.co.boothen.gradle.wsimport.WsImport) {
    wsdl = "create/Create.wsdl"
}
```

## Example

```groovy
apply plugin: 'uk.co.boothen.gradle.wsimport'

task wsimport(type: uk.co.boothen.gradle.wsimport.WsImport) {
    keep = true
    extension = true
    verbose = true
    quiet = false
    debug = true
    xnocompile = true

    wsdl("create/Create.wsdl") {
        bindingFile("create/bindings-create.xml")
        xjcarg("-XautoNameResolution")
    }

    wsdl ("find/Find.wsdl") {
        bindingFile ("find/bindings-find.xml")
        xjcarg ("-XautoNameResolution")
    }

    wsdl ("update/Update.wsdl") {
        bindingFile ("update/bindings-update.xml")
        xjcarg ("-XautoNameResolution")
    }
}
```

## To Do
```
        // TODO: Expose some of this properties
//        wsImport2.setXauthfile();
//        wsImport2.setCatalog();
//        wsImport2.setClientjar();
//        wsImport2.setdisableAuthenticator();
//        wsImport2.setGenerateJWS();
//        wsImport2.setImplDestDir();
//        wsImport2.setImplPortName();
//        wsImport2.setImplServiceName();
//        wsImport2.setWsdllocation();
//        wsImport2.setXUseBaseResourceAndURLToLoadWSDL();
```