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

import org.gradle.api.DefaultTask;
import org.gradle.api.file.ConfigurableFileCollection;
import org.gradle.api.provider.ListProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.CacheableTask;
import org.gradle.api.tasks.Classpath;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.TaskAction;
import org.gradle.workers.WorkQueue;
import org.gradle.workers.WorkerExecutor;

import java.io.File;
import java.util.Collections;

import javax.inject.Inject;

/**
 * A Gradle task for executing wsimport operations to generate JAX-WS portable artifacts from WSDL files.
 * This task supports various wsimport options and handles the generation of Java source files and compiled classes
 * from WSDL definitions using worker execution for better performance.
 */
@CacheableTask
public abstract class WsImportTask extends DefaultTask {

    /**
     * Gets the worker executor service for parallel task execution.
     *
     * @return The worker executor instance for managing parallel work queues
     */
    @Inject
    protected abstract WorkerExecutor getWorkerExecutor();


    /**
     * Gets whether to keep generated source files.
     *
     * @return Property indicating if generated sources should be kept
     */
    @Input
    public abstract Property<Boolean> getKeep();

    /**
     * Gets whether to enable extension mode.
     *
     * @return Property indicating if extension mode is enabled
     */
    @Input
    public abstract Property<Boolean> getExtension();

    /**
     * Gets whether to enable verbose output.
     *
     * @return Property indicating if verbose output is enabled
     */
    @Input
    public abstract Property<Boolean> getVerbose();

    /**
     * Gets whether to suppress wsimport output.
     *
     * @return Property indicating if output should be suppressed
     */
    @Input
    public abstract Property<Boolean> getQuiet();

    /**
     * Gets whether to enable debugging.
     *
     * @return Property indicating if debugging is enabled
     */
    @Input
    public abstract Property<Boolean> getDebug();

    /**
     * Gets whether to disable compilation of generated source files.
     *
     * @return Property indicating if compilation should be skipped
     */
    @Input
    public abstract Property<Boolean> getXnocompile();

    /**
     * Gets whether to generate additional header files.
     *
     * @return Property indicating if additional headers should be generated
     */
    @Input
    public abstract Property<Boolean> getXadditionalHeaders();

    /**
     * Gets the target platform version.
     *
     * @return Property containing the target version
     */
    @Input
    public abstract Property<String> getTarget();

    /**
     * Gets the encoding for generated sources.
     *
     * @return Property containing the encoding value
     */
    @Input
    public abstract Property<String> getEncoding();

    /**
     * Gets whether to disable addressing databinding.
     *
     * @return Property indicating if addressing databinding is disabled
     */
    @Input
    public abstract Property<Boolean> getXNoAddressingDatabinding();

    /**
     * Gets whether to enable XML schema debugging.
     *
     * @return Property indicating if XML schema debugging is enabled
     */
    @Input
    public abstract Property<Boolean> getXdebug();

    /**
     * Gets the root directory containing WSDL files.
     *
     * @return Property containing the WSDL source root path
     */
    @Input
    public abstract Property<String> getWsdlSourceRoot();

    /**
     * Gets the list of WSDL configurations to process.
     *
     * @return ListProperty containing WSDL configurations
     */
    @Input
    public abstract ListProperty<Wsdl> getWsdls();

    /**
     * Gets the project root directory.
     *
     * @return Property containing the project directory
     */
    @Input
    public abstract Property<File> getProjectDir();

    /**
     * Gets the JAX-WS tools classpath configuration.
     *
     * @return ConfigurableFileCollection containing the tools classpath
     */
    @InputFiles
    @Classpath
    public abstract ConfigurableFileCollection getJaxwsToolsConfiguration();

    /**
     * Gets the output directory for generated source files.
     *
     * @return Property containing the generated source output directory
     */
    @OutputDirectory
    public abstract Property<File> getGeneratedSourceRoot();

    /**
     * Gets the output directory for compiled class files.
     *
     * @return Property containing the generated classes output directory
     */
    @OutputDirectory
    public abstract Property<File> getGeneratedClassesRoot();

    /**
     * Executes the wsimport task action to process WSDL files and generate Java artifacts.
     * Creates an isolated classloader for each WSDL processing operation and submits work
     * to the worker queue for execution.
     */
    @TaskAction
    public void taskAction() {

        WorkerExecutor workerExecutor = getWorkerExecutor();

        WorkQueue workQueue = workerExecutor.classLoaderIsolation(workerSpec -> {
            workerSpec.getClasspath().from(getJaxwsToolsConfiguration());
        });

        for (Wsdl wsdl : getWsdls().getOrElse(Collections.emptyList())) {
            workQueue.submit(WsImportWorkAction.class, parameters -> {
                parameters.getKeep().set(getKeep());
                parameters.getExtension().set(getExtension());
                parameters.getVerbose().set(getVerbose());
                parameters.getQuiet().set(getQuiet());
                parameters.getDebug().set(getDebug());
                parameters.getXnocompile().set(getXnocompile());
                parameters.getXadditionalHeaders().set(getXadditionalHeaders());
                parameters.getXNoAddressingDatabinding().set(getXNoAddressingDatabinding());
                parameters.getXdebug().set(getXdebug());

                parameters.getTarget().set(getTarget());
                parameters.getEncoding().set(getEncoding());

                parameters.getWsdlSourceRoot().set(getWsdlSourceRoot());
                parameters.getGeneratedSourceRoot().set(getGeneratedSourceRoot());
                parameters.getGeneratedClassesRoot().set(getGeneratedClassesRoot());
                parameters.getWsdl().set(wsdl);
                parameters.getProjectRoot().set(getProjectDir());
            });
            // The order in which the wsdl's are processed is relevant.
            workQueue.await();
        }

    }

}
