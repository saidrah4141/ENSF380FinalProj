package ca.ucalgary.ensf380;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Provides methods for connecting to a MySQL database and performing operations related to image storage.
 */
public class Database {

    private static final String URL = "jdbc:mysql://localhost:3306/adsdatabase";
    private static final String USER = "user"; 
    private static final String PASSWORD = "ensf380"; 

    private Connection connection;

    /**
     * Initializes a connection to the MySQL database.
     */
    public Database() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Database connection established.");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Inserts an image into the database.
     * 
     * @param id the unique identifier for the image
     * @param filePath the file path of the image to be inserted
     */
    public void insertImage(int id, String filePath) {
        String sql = "INSERT INTO image (id, img) VALUES (?, LOAD_FILE(?))";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.setString(2, filePath);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Image inserted successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves an image from the database by its unique identifier.
     * 
     * @param id the unique identifier of the image to be retrieved
     * @return a byte array containing the image data, or null if no image is found
     */
    public byte[] getImageById(int id) {
        String sql = "SELECT img FROM image WHERE id = ?";
        byte[] imageData = null;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                imageData = resultSet.getBytes("img");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return imageData;
    }

    /**
     * Closes the database connection.
     */
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
