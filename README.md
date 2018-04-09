Gradle WsImport Plugin
======================


[![Build Status](https://secure.travis-ci.org/boothen/gradle-wsimport.png)](http://travis-ci.org/boothen/gradle-wsimport)


## Using the plugin

```groovy
plugins {
  id "uk.co.boothen.gradle.wsimport" version "0.1"
}
```



## Example

```groovy
plugins {
  id "uk.co.boothen.gradle.wsimport" version "0.1"
}


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