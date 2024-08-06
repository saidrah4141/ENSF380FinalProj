package ca.ucalgary.ensf380;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileFinderTest {

    private FileFinder fileFinder;
    private File testDir;
    private File subDir;

    @BeforeEach
    public void setUp() throws IOException {
        
        testDir = new File("testdata");
        subDir = new File(testDir, "subdir");

        
        if (!testDir.exists()) {
            assertTrue(testDir.mkdir(), "Failed to create test directory");
        }
        if (!subDir.exists()) {
            assertTrue(subDir.mkdir(), "Failed to create subdirectory");
        }

        
        createTestFile(testDir, "file1.txt", System.currentTimeMillis() - 10000);
        createTestFile(subDir, "file2.txt", System.currentTimeMillis() - 5000);
        createTestFile(testDir, "file3.txt", System.currentTimeMillis());

        fileFinder = new FileFinder(testDir.getAbsolutePath(), ".txt");
    }

    private void createTestFile(File dir, String filename, long lastModified) throws IOException {
        File file = new File(dir, filename);
        try (FileWriter writer = new FileWriter(file)) {
            writer.write("This is a test file.");
        }
        file.setLastModified(lastModified);
    }

    @AfterEach
    public void tearDown() {
       
        deleteRecursively(testDir);
    }

    private void deleteRecursively(File file) {
        if (file.isDirectory()) {
            for (File child : file.listFiles()) {
                deleteRecursively(child);
            }
        }
        file.delete();
    }

    @Test
    public void testSearchFiles() {
        fileFinder.searchFiles();
        String latestFilePath = fileFinder.getLatestFile();

        assertNotNull(latestFilePath, "Latest file path should not be null");
        assertTrue(latestFilePath.endsWith("file3.txt"), "The latest file should be 'file3.txt'");
    }
}
