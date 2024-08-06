package ca.ucalgary.ensf380;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DatabaseTest {

    private Database database;
    private static final String TEST_FILE_PATH = "C:\\ProgramData\\MySQL\\MySQL Server 8.0\\Uploads\\test.jpeg";
    private static final int TEST_ID = 10;

    @BeforeEach
    public void setUp() {
        database = new Database();
    }

    @AfterEach
    public void tearDown() {
        database.close();
    }

    @Test
    public void testInsertImage() {
        try {
            database.insertImage(TEST_ID, TEST_FILE_PATH);
            byte[] retrievedImage = database.getImageById(TEST_ID);
            assertNotNull(retrievedImage, "Image should be retrieved");
            assertTrue(retrievedImage.length > 0, "Image data should not be empty");

        } catch (Exception e) {
            fail("Exception during insert or retrieve: " + e.getMessage());
        }
    }

    @Test
    public void testGetImageById() {
        try {
           
            database.insertImage(TEST_ID, TEST_FILE_PATH);
            byte[] imageData = database.getImageById(TEST_ID);           
            assertNotNull(imageData, "Image data should not be null");
            assertTrue(imageData.length > 0, "Image data should not be empty");

        } catch (Exception e) {
            fail("Exception during insert or retrieve: " + e.getMessage());
        }
    }

    
}
