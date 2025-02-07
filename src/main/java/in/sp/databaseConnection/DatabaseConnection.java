package in.sp.databaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    public static Connection getConnection() {
        Connection conn = null;
        try {
            // Register the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Correct the connection URL format
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/todos", "root", "root"); // Updated URL

        } catch (Exception e) {
            e.printStackTrace();  // Print stack trace for debugging
        }
        return conn;
    }
}
