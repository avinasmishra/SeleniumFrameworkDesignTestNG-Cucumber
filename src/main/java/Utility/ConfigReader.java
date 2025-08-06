package Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties prop;

    public static Properties loadProperties() {
        try {
            FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\java\\Utility\\globalProperty.properties");
            prop = new Properties();
            prop.load(fis);
        } catch (IOException e) {
            System.out.println("Failed to load properties file: " + e.getMessage());
        }
        return prop;
    }

    public static String getProperty(String key) {
        if (prop == null) {
            throw new IllegalStateException("Properties file not loaded. Call loadProperties() first.");
        }
        return prop.getProperty(key);
    }

    public static long getMediumWait(String key) {
        if (prop == null) {
            throw new IllegalStateException("Properties file not loaded.");
        }
        return Long.parseLong(prop.getProperty(key));
    }

    public static int getSmallWait(String key) {
        if (prop == null) {
            throw new IllegalStateException("Properties file not loaded.");
        }
        return Integer.parseInt(prop.getProperty(key));
    }


}
