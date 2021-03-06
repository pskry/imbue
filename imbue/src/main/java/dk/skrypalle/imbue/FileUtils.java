package dk.skrypalle.imbue;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

final class FileUtils {

    static List<File> listFiles(File dir, boolean recurse) {
        return listFiles(dir, pathname -> true, recurse);
    }

    static List<File> listFiles(File dir, FileFilter filter, boolean recurse) {
        return listFiles(dir, filter, recurse, null);
    }

    private static List<File> listFiles(
            File dir,
            FileFilter filter,
            boolean recurse,
            List<File> out) {
        if (dir == null || !dir.isDirectory()) {
            throw new IllegalArgumentException("'dir' must point to a directory.");
        }
        if (filter == null) {
            throw new IllegalArgumentException("'filter' must not be null");
        }

        var entries = dir.listFiles();
        if (entries == null || entries.length == 0) {
            return List.of();
        }

        var files = out == null
                ? new ArrayList<File>()
                : out;

        for (var entry : entries) {
            if (filter.accept(entry) && !entry.isDirectory()) {
                files.add(entry);
            }

            if (recurse && entry.isDirectory()) {
                listFiles(entry, filter, true, files);
            }
        }

        return files;
    }

    private FileUtils() { /* static utility */ }

}
