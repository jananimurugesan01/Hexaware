package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DBPropertyUtil {

    public static String getConnectionString(String propertyFileName) {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(propertyFileName)) {
            props.load(fis);
            String url = props.getProperty("jdbc:mysql://localhost:3306/transportdb");
            String user = props.getProperty("root");
            String password = props.getProperty("0987@jaN.");
            return url + "?user=" + user + "&password=" + password;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
