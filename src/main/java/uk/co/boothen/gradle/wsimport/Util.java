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

import java.io.File;

/**
 * Utility class providing path and file merging functionality.
 * Contains static helper methods for handling file paths.
 */
public final class Util {

    private Util() {

    }

    /**
     * Merges two path strings together, ensuring proper path separator between them.
     *
     * @param root The root path string
     * @param file The file/directory path to append
     * @return The merged path with proper separator
     */
    public static String mergePaths(String root, String file) {
        if (!root.endsWith("/") && !file.startsWith("/")) {
            return root + "/" + file;
        }
        return root + file;
    }

    /**
     * Creates a new File by merging a root directory with a file path.
     *
     * @param buildDir The root directory as a File object
     * @param file     The file path to append
     * @return A new File object representing the merged path
     */
    public static File mergeFile(File buildDir, String file) {
        String mergePaths = mergePaths(buildDir.toString(), file);
        return new File(mergePaths);
    }
}
