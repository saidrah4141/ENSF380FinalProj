package ca.ucalgary.ensf380;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.swing.ImageIcon;

public class AdsManager {

    private Database database;

    public AdsManager() {
        database = new Database();
    }
    public ImageIcon getAdImageIcon(int imageId) {
        byte[] imageBytes = database.getImageById(imageId);
        if (imageBytes != null) {
            ByteArrayInputStream bais = new ByteArrayInputStream(imageBytes);
			ImageIcon imageIcon = new ImageIcon(bais.readAllBytes());
			return imageIcon;
        }
        return null;
    }
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

    public void close() {
        database.close();
    }

    
}
