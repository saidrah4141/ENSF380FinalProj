package ca.ucalgary.ensf380;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.swing.ImageIcon;

/**
 * Manages advertisement images, including retrieving and displaying them.
 */
public class AdsManager {

    private Database database;

    /**
     * Constructs an AdsManager instance and initializes the database connection.
     */
    public AdsManager() {
        database = new Database();
    }

    /**
     * Retrieves an advertisement image as an ImageIcon based on the given image ID.
     * 
     * @param imageId the ID of the advertisement image to retrieve
     * @return an ImageIcon representing the advertisement image, or null if no image was found
     */
    public ImageIcon getAdImageIcon(int imageId) {
        byte[] imageBytes = database.getImageById(imageId);
        if (imageBytes != null) {
            try (ByteArrayInputStream bais = new ByteArrayInputStream(imageBytes)) {
                return new ImageIcon(bais.readAllBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Saves the advertisement image to a file based on the given image ID.
     * 
     * @param imageId the ID of the advertisement image to save
     */
    public void displayAd(int imageId) {
        byte[] imageBytes = database.getImageById(imageId);

        if (imageBytes != null) {
            try {
                File file = new File("ad_" + imageId + ".jpg"); 
                try (OutputStream os = new FileOutputStream(file)) {
                    os.write(imageBytes);
                    System.out.println("Advertisement image saved as: " + file.getAbsolutePath());
                }
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No advertisement found with ID: " + imageId);
        }
    }

    /**
     * Closes the database connection.
     */
    public void close() {
        database.close();
    }
}
