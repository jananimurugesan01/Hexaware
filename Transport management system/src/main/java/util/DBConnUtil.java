package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnUtil {

    private static Connection connection;

    public static Connection getConnection(String propertyFileName) {
        if (connection == null) {
            try {
                String connectionString = DBPropertyUtil.getConnectionString(propertyFileName);
                if (connectionString != null) {
                    connection = DriverManager.getConnection(connectionString);
                    System.out.println("Database connection established.");
                } else {
                    System.out.println("Failed to load connection string.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
}
