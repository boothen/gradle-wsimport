Gradle WSImport Plugin [![Build Status](https://secure.travis-ci.org/boothen/gradle-wsimport.png)](http://travis-ci.org/boothen/gradle-wsimport)
======================

Gradle plugin that wraps the [Ant wsimport](https://javaee.github.io/metro-jax-ws/doc/user-guide/ch04.html#tools-wsimport-ant-task) task providing a simple and clean DSL to generate source and class files from a WSDL.

## Using the plugin

The plugin is registered with [Gradle Plugins](https://plugins.gradle.org/plugin/uk.co.boothen.gradle.wsimport). The simplest way to define and use the plugin in your Gradle build file is 
```groovy
plugins {
  id "uk.co.boothen.gradle.wsimport" version "0.18"
}
```

## Simple configuration

The plugin is intend to be as configuration light as possible. Using default values for most configuration properties. Therefore, it should be possible to generate and compile Java source classes from a WSDL file with just the following configuration.

```groovy
wsimport {
    wsdl = "create/Create.wsdl"
}
```

#### WSDL Property
The WSDL property is the only property required to be set. Multiple WSDL properties can be defined.

Name | Type | Description | Default
--- | --- | --- | ---
wsdl | String | WSDL file, can include the path to file from the WSDL location. e.g. the WSDL property set to create/Create.wsdl would expect the file to be located under src/main/resources/wsdl/create/Create.wsdl | - 

Multiple WSDL files can be configured as follows

```groovy
wsimport {
    wsdl = "create/Create.wsdl"
    wsdl = "find/Find.wsdl"
    wsdl = "update/Update.wsdl"
}
```

## Detailed configuration
However, more complex configuration can be specified if required. The example below highlights how to specify those properties.
```groovy
wsimport {  
    wsdlSourceRoot = "/src/main/resources/wsdl"
    generatedSourceRoot = "/generated/src/wsdl/main"
    generatedClassesRoot = "/classes/main"
    includeDependencies = true
    
    target = "3.0"
    keep = true
    extension = true
    verbose = false
    quiet = true
    debug = false
    xnocompile = true
    xadditionalHeaders = false
    xNoAddressingDatabinding = false

    wsdl ("create/Create.wsdl") {
        bindingFile("create/bindings-create.xml")
        xjcarg("-XautoNameResolution")
        extraArg("-J-Djavax.xml.accessExternalDTD=all")
        packageName("com.different.package.name")
        wsdlLocation("schema/schema.wsdl")
        catalog("catalog/jax-ws-catalog.xml")
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

## Configuration properties

### Java Source and Compilation properties

Name | Type | Description | Default
--- | --- | --- | ---
wsdlSourceRoot | String | Directory location of WSDL files | src/main/resources/wsdl 
generatedSourceRoot | String | Directory location of the generated Java source files | build/generated/src/wsdl/main 
generatedClassesRoot | String | Directory location of the generated classes | build/classes/java/main 
includeDependencies | boolean | Include the required Jakarta XML/JWS as Gradle Implementation dependencies  | true
target | String | 	Generate code as per the given JAXWS specification version. For example, 2.0 value generates compliant code for JAXWS 2.0 spec. | 3.0 
keep | boolean | Keep generated source code files | true 
encoding | String | Set the encoding name for generated sources | UTF-8 
extension | boolean | allow vendor extensions (functionality not specified by the specification). Use of extensions may result in applications that are not portable or may not interoperate with other implementations. | false 
verbose | boolean | Output JAX-WS RI version and messages about what the compiler is doing | false 
quiet | boolean | Suppress wsimport output. | true 
debug | boolean | Print debug information. | false
xdebug | boolean | Print debug information. **Set this to true to see details around failing WSDL generation** | false
xnocompile | boolean | Do not compile generated Java files | true 
xadditionalHeaders | boolean | 	Map headers not bound to request or response message to Java method parameters. | false
xNoAddressingDatabinding | boolean | Enable binding of W3C EndpointReferenceType to Java. | false

### WSDL file additional configuration properties

Name | Type | Description
--- | --- | ---
bindingFile | String/List | Binding File |
extraArg | String/List | Additional command line arguments passed to the wsimport |
xjcarg | String/List | Arguments are directly passed to the XJC tool (JAXB Schema Compiler), which will be used for compiling the schema referenced in the wsdl 
packageName | String | 	Specifies the target package |
wsdlLocation | String | The wsdl URI passed thru this option will be used to set the value of @WebService.wsdlLocation and @WebServiceClient.wsdlLocation annotation elements on the generated SEI and Service interface. Defaults to the wsdl URL passed to wsdl attribute. |
catalog | String | Specify a catalog file to resolve external entity references. Option supports the TR9401, XCatalog and OASIS XML Catalog formats. 

## Jakarta packages vs Javax packages

From version 0.18 the plugin is defaulted to use JAXWS 3.0 specification version. This generates classes with the jakarta.* packages instead of the javax.* packages. To revert back to using javax packages the following configuration can be specified.

```groovy
dependencies {
    // Specify previous version of JAXWS tools to use
    jaxWsTools "com.sun.xml.ws:jaxws-tools:2.3.2"
    
    // Specify previous version of WS/XML/JWS classes
    implementation"javax.xml.bind:jaxb-api:2.3.1"
    implementation "javax.xml.ws:jaxws-api:2.3.1"
    implementation "javax.jws:javax.jws-api:1.1"
}

wsimport {
    
    // Exclude Jakarta WS/XML/JWS classes implementation dependencies
    includeDependencies = false
    
    // Set JAXWS specification to 2.2
    target = 2.2

    wsdl = "create/Create.wsdl""
    ...
}
```

## To Do
The following properties have not been exposed from the Ant task to the plugin. If you need any of them in your configuration submit a PR or an Issue and we'll add it in.
```
//        wsImport2.setXauthfile();
//        wsImport2.setClientjar();
//        wsImport2.setdisableAuthenticator();
//        wsImport2.setGenerateJWS();
//        wsImport2.setImplDestDir();
//        wsImport2.setImplPortName();
//        wsImport2.setImplServiceName();
//        wsImport2.setXUseBaseResourceAndURLToLoadWSDL();
```
