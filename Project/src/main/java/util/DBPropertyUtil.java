// File: util/DBPropertyUtil.java
package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DBPropertyUtil {
    public static Properties getDBProperties(String filename) throws IOException {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(filename)) {
            props.load(fis);
        }
        return props;
    }
}
