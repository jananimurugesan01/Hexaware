package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnUtil {
    private static final String URL = "jdbc:mysql://localhost:3306/petpalsdb";
    private static final String USER = "root";
    private static final String PASSWORD = "0987@jaN."; // Use your password here

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
