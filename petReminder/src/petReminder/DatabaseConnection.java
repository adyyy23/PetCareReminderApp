package petReminder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/app_final"; // Use your database name
    private static final String DB_USER = "root";  // Default MySQL username in XAMPP
    private static final String DB_PASSWORD = "";  // Default MySQL password in XAMPP (blank)

    public static Connection connect() {
        try {
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
            return null;
        }
    }
}
