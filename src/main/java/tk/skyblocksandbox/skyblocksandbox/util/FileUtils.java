package tk.skyblocksandbox.skyblocksandbox.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

public class FileUtils {

    public static void deleteDirectory(final File directory) throws IOException {
        if(!directory.exists()){
            return;
        }

        if(!isSymlink(directory)){
            cleanDirectory(directory);
        }

        if(!directory.delete()){
            final String message = "Unable to delete directory " + directory;
            throw new IOException(message);
        }
    }

    private static void cleanDirectory(final File directory) throws IOException {
        final File[] files = verifiedListFiles(directory);

        IOException exception = null;
        for(final File file : files){
            try {
                forceDelete(file);
            } catch (final IOException ioe){
                exception = ioe;
            }
        }

        if(null != exception){
            throw exception;
        }
    }

    public static void forceDelete(final File file) throws IOException {
        if (file.isDirectory()) {
            deleteDirectory(file);
        } else { final boolean filePresent = file.exists();
            if (!file.delete()) {
                if (!filePresent) {
                    throw new FileNotFoundException("File does not exist: " + file);
                }
                final String message = "Unable to delete file: " + file;
                throw new IOException(message);
            }
        }
    }

    private static File[] verifiedListFiles(File directory) throws IOException{
        if(!directory.exists()){
            final String message = directory + " does not exist";
            throw new IllegalArgumentException(message);
        }

        if(!directory.isDirectory()){
            final String message = directory + " is not a directory";
            throw new IllegalArgumentException(message);
        }

        final File[] files = directory.listFiles();
        if (files == null) {  // null if security restricted
            throw new IOException("Failed to list contents of " + directory);
        }
        return files;
    }

    private static boolean isSymlink(final File file) throws IOException{
        if(file == null){
            throw new NullPointerException("File must no be null");
        }
        return Files.isSymbolicLink(file.toPath());
    }
}