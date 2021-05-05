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
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.CacheableTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.PathSensitive;
import org.gradle.api.tasks.PathSensitivity;
import org.gradle.api.tasks.TaskAction;
import org.gradle.workers.WorkQueue;
import org.gradle.workers.WorkerExecutor;

import java.io.File;

import javax.inject.Inject;

@CacheableTask
public class WsImportTask extends DefaultTask {

    private final WorkerExecutor workerExecutor;

    private Property<Configuration> jaxwsToolsConfiguration;
    private Property<Boolean> keep;
    private Property<Boolean> extension;
    private Property<Boolean> verbose;
    private Property<Boolean> quiet;
    private Property<Boolean> debug;
    private Property<Boolean> xnocompile;
    private Property<Boolean> xadditionalHeaders;
    private Property<Boolean> xNoAddressingDatabinding;
    private Property<Boolean> xdebug;
    private Property<String> target;
    private Property<String> encoding;
    private Property<Wsdl> wsdl;
    private Property<String> wsdlSourceRoot;
    private Property<File> generatedSourceRoot;
    private Property<File> generatedClassesRoot;
    private Property<File> projectDir;

    @Inject
    public WsImportTask(WorkerExecutor workerExecutor) {
        this.workerExecutor = workerExecutor;
        jaxwsToolsConfiguration = getProject().getObjects().property(Configuration.class);
        keep = getProject().getObjects().property(Boolean.class);
        extension = getProject().getObjects().property(Boolean.class);
        verbose = getProject().getObjects().property(Boolean.class);
        quiet = getProject().getObjects().property(Boolean.class);
        debug = getProject().getObjects().property(Boolean.class);
        xnocompile = getProject().getObjects().property(Boolean.class);
        xadditionalHeaders = getProject().getObjects().property(Boolean.class);
        xNoAddressingDatabinding = getProject().getObjects().property(Boolean.class);
        xdebug = getProject().getObjects().property(Boolean.class);
        target = getProject().getObjects().property(String.class);
        encoding = getProject().getObjects().property(String.class);
        wsdl = getProject().getObjects().property(Wsdl.class);
        wsdlSourceRoot = getProject().getObjects().property(String.class);
        generatedSourceRoot = getProject().getObjects().property(File.class);
        generatedClassesRoot = getProject().getObjects().property(File.class);
        projectDir = getProject().getObjects().property(File.class);
    }

    @Input
    public Property<Boolean> getKeep() {
        return keep;
    }

    @Input
    public Property<Boolean> getExtension() {
        return extension;
    }

    @Input
    public Property<Boolean> getVerbose() {
        return verbose;
    }

    @Input
    public Property<Boolean> getQuiet() {
        return quiet;
    }

    @Input
    public Property<Boolean> getDebug() {
        return debug;
    }

    @Input
    public Property<Boolean> getXnocompile() {
        return xnocompile;
    }

    @Input
    public Property<Boolean> getXadditionalHeaders() {
        return xadditionalHeaders;
    }

    @Input
    public Property<String> getTarget() {
        return target;
    }

    @Input
    public Property<String> getEncoding() {
        return encoding;
    }

    @Input
    public Property<Boolean> getXNoAddressingDatabinding() {
        return xNoAddressingDatabinding;
    }

    @Input
    public Property<Boolean> getXdebug() {
        return xdebug;
    }

    @Input
    public Property<String> getWsdlSourceRoot() {
        return wsdlSourceRoot;
    }

    @Input
    public Property<Wsdl> getWsdl() {
        return wsdl;
    }

    @Input
    public Property<File> getProjectDir() {
        return projectDir;
    }

    @InputFiles
    @PathSensitive(PathSensitivity.NAME_ONLY)
    public Property<Configuration> getJaxwsToolsConfiguration() {
        return jaxwsToolsConfiguration;
    }

    @OutputDirectory
    public Property<File> getGeneratedSourceRoot() {
        return generatedSourceRoot;
    }

    @OutputDirectory
    public Property<File> getGeneratedClassesRoot() {
        return generatedClassesRoot;
    }

    @TaskAction
    public void taskAction() {
        WorkQueue workQueue = workerExecutor.classLoaderIsolation(workerSpec -> {
            workerSpec.getClasspath().from(jaxwsToolsConfiguration);
        });

        workQueue.submit(WsImportWorkAction.class, parameters -> {
            parameters.getKeep().set(keep);
            parameters.getExtension().set(extension);
            parameters.getVerbose().set(verbose);
            parameters.getQuiet().set(quiet);
            parameters.getDebug().set(debug);
            parameters.getXnocompile().set(xnocompile);
            parameters.getXadditionalHeaders().set(xadditionalHeaders);
            parameters.getXNoAddressingDatabinding().set(xNoAddressingDatabinding);
            parameters.getXdebug().set(xdebug);

            parameters.getTarget().set(target);
            parameters.getEncoding().set(encoding);

            parameters.getWsdlSourceRoot().set(wsdlSourceRoot);
            parameters.getGeneratedSourceRoot().set(generatedSourceRoot);
            parameters.getGeneratedClassesRoot().set(generatedClassesRoot);
            parameters.getWsdl().set(wsdl);
            parameters.getProjectRoot().set(projectDir);
        });

    }

}
