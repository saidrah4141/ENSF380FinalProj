package ca.ucalgary.ensf380;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private static final String URL = "jdbc:mysql://localhost:3306/adsdatabase";
    private static final String USER = "user"; 
    private static final String PASSWORD = "ensf380"; 

    private Connection connection;

    public Database() {
        try {
           
            Class.forName("com.mysql.cj.jdbc.Driver");
       
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Database connection established.");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    
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

    public static void main(String[] args) {
        Database db = new Database();

     
        byte[] image = db.getImageById(1);
        if (image != null) {
            System.out.println("Retrieved image size: " + image.length + " bytes");
        }

        

        db.close();
    }
}
