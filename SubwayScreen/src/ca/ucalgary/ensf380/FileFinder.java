package ca.ucalgary.ensf380;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A utility class for finding files with a specific extension within a directory and its subdirectories.
 * It can also retrieve the most recently modified file among the found files.
 */
public class FileFinder {

    private final String directoryPath;
    private final String fileExtension;
    private final List<File> foundFiles;

    /**
     * Constructs a FileFinder with the specified directory path and file extension.
     * 
     * @param directoryPath the path of the directory to search for files
     * @param fileExtension the file extension to filter by (e.g., ".txt")
     */
    public FileFinder(String directoryPath, String fileExtension) {
        this.directoryPath = directoryPath;
        this.fileExtension = fileExtension;
        this.foundFiles = new ArrayList<>();
    }

    /**
     * Initiates the search for files with the specified extension within the directory and its subdirectories.
     * Prints the absolute paths of the found files to the console.
     * If the specified path is not a valid directory, an error message is printed.
     */
    public void searchFiles() {
        File directory = new File(directoryPath);
        if (!directory.exists() || !directory.isDirectory()) {
            System.out.println("The specified path is not a valid directory.");
            return;
        }
        listFilesWithExtension(directory);
    }

    /**
     * Recursively lists files with the specified extension within the given directory.
     * 
     * @param directory the directory to search within
     */
    private void listFilesWithExtension(File directory) {
        File[] files = directory.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(fileExtension);
            }
        });

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    foundFiles.add(file);
                    System.out.println(file.getAbsolutePath());
                }
            }

            File[] subdirectories = directory.listFiles(File::isDirectory);
            if (subdirectories != null) {
                for (File subdir : subdirectories) {
                    listFilesWithExtension(subdir);
                }
            }
        }
    }

    /**
     * Returns the absolute path of the most recently modified file among the found files.
     * 
     * @return the absolute path of the most recently modified file
     *         or {@code null} if no files were found
     */
    public String getLatestFile() {
        if (foundFiles.isEmpty()) {
            return null;
        }
        Collections.sort(foundFiles, (file1, file2) -> Long.compare(file2.lastModified(), file1.lastModified()));
        return foundFiles.get(0).getAbsolutePath();
    }
}
