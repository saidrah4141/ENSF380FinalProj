package ca.ucalgary.ensf380;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileFinder {

    private final String directoryPath;
    private final String fileExtension;
    private final List<File> foundFiles;

    public FileFinder(String directoryPath, String fileExtension) {
        this.directoryPath = directoryPath;
        this.fileExtension = fileExtension;
        this.foundFiles = new ArrayList<>();
    }

    

    
    public void searchFiles() {
        File directory = new File(directoryPath);
        if (!directory.exists() || !directory.isDirectory()) {
            System.out.println("The specified path is not a valid directory.");
            return;
        }
        listFilesWithExtension(directory);
    }

    
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

    
    public String getLatestFile() {
   
        Collections.sort(foundFiles, (file1, file2) -> Long.compare(file2.lastModified(), file1.lastModified()));
        return foundFiles.get(0).getAbsolutePath();
    }
}
