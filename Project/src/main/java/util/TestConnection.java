
package util;
import java.sql.Connection;
import java.sql.SQLException;



public class TestConnection {
    public static void main(String[] args) throws SQLException {
        Connection conn = DBConnUtil.getConnection();
        if (conn != null) {
            System.out.println("Connection successful!");
        } else {
            System.out.println("Connection failed.");
        }
    }
}