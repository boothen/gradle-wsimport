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

import org.gradle.api.provider.Property;
import org.gradle.workers.WorkParameters;

import java.io.File;


/**
 * Work parameters interface for WsImport task that defines configuration options
 * for generating JAX-WS portable artifacts from WSDL files.
 */
public interface WsImportWorkParameters extends WorkParameters {

    /**
     * Whether to keep generated source files.
     * @return Property containing boolean flag indicating if generated source files should be kept
     */
    Property<Boolean> getKeep();

    /**
     * Whether to allow vendor extensions.
     * @return Property containing boolean flag indicating if vendor extensions are allowed
     */
    Property<Boolean> getExtension();

    /**
     * Whether to output messages about what the compiler is doing.
     * @return Property containing boolean flag indicating if verbose output is enabled
     */
    Property<Boolean> getVerbose();

    /**
     * Whether to suppress wsimport output.
     * @return Property containing boolean flag indicating if output should be suppressed
     */
    Property<Boolean> getQuiet();

    /**
     * Whether to run in debug mode.
     * @return Property containing boolean flag indicating if debug mode is enabled
     */
    Property<Boolean> getDebug();

    /**
     * Whether to not compile generated source files.
     * @return Property containing boolean flag indicating if compilation should be skipped
     */
    Property<Boolean> getXnocompile();

    /**
     * Whether to generate additional wrapper element for headers not bound to request or response.
     * @return Property containing boolean flag indicating if additional headers should be generated
     */
    Property<Boolean> getXadditionalHeaders();

    /**
     * Whether to disable generation of w3c.addressing middleware code.
     * @return Property containing boolean flag indicating if w3c addressing databinding should be disabled
     */
    Property<Boolean> getXNoAddressingDatabinding();

    /**
     * Whether to print debug information.
     * @return Property containing boolean flag indicating if debug information should be printed
     */
    Property<Boolean> getXdebug();

    /**
     * Target version for generated source.
     * @return Property containing target version string for generated source
     */
    Property<String> getTarget();

    /**
     * Character encoding for generated source files.
     * @return Property containing character encoding string for generated sources
     */
    Property<String> getEncoding();

    /**
     * Root directory or URL for WSDL files.
     * @return Property containing root directory path or URL string for WSDL files
     */
    Property<String> getWsdlSourceRoot();

    /**
     * Directory for generated source files.
     * @return Property containing directory File object for generated source files
     */
    Property<File> getGeneratedSourceRoot();

    /**
     * Directory for generated class files.
     * @return Property containing directory File object for generated class files
     */
    Property<File> getGeneratedClassesRoot();

    /**
     * Root directory of the project.
     * @return Property containing root directory File object of the project
     */
    Property<File> getProjectRoot();

    /**
     * WSDL configuration details.
     * @return Property containing WSDL configuration object
     */
    Property<Wsdl> getWsdl();

}
