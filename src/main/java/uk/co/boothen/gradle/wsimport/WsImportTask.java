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

@CacheableTask
public abstract class WsImportTask extends DefaultTask {

    @Inject
    protected abstract WorkerExecutor getWorkerExecutor();

    @Input
    public abstract Property<Boolean> getKeep();

    @Input
    public abstract Property<Boolean> getExtension();

    @Input
    public abstract Property<Boolean> getVerbose();

    @Input
    public abstract Property<Boolean> getQuiet();

    @Input
    public abstract Property<Boolean> getDebug();

    @Input
    public abstract Property<Boolean> getXnocompile();

    @Input
    public abstract Property<Boolean> getXadditionalHeaders();

    @Input
    public abstract Property<String> getTarget();

    @Input
    public abstract Property<String> getEncoding();

    @Input
    public abstract Property<Boolean> getXNoAddressingDatabinding();

    @Input
    public abstract Property<Boolean> getXdebug();

    @Input
    public abstract Property<String> getWsdlSourceRoot();

    @Input
    public abstract ListProperty<Wsdl> getWsdls();

    @Input
    public abstract Property<File> getProjectDir();

    @InputFiles
    @Classpath
    public abstract ConfigurableFileCollection getJaxwsToolsConfiguration();

    @OutputDirectory
    public abstract Property<File> getGeneratedSourceRoot();

    @OutputDirectory
    public abstract Property<File> getGeneratedClassesRoot();

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
