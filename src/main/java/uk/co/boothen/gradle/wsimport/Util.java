package uk.co.boothen.gradle.wsimport;

import java.io.File;

public final class Util {

    private Util() {

    }

    public static String mergePaths(String root, String file) {
        if (!root.endsWith("/") && !file.startsWith("/")) {
            return root + "/" + file;
        }
        return root + file;
    }

    public static File mergeFile(File buildDir, String file) {
        String mergePaths = mergePaths(buildDir.toString(), file);
        return new File(mergePaths);
    }
}
