package repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertiesLoader {
    public static final Properties PROPERTIES = new Properties();
    public static void loadProperties() {
        try (BufferedReader reader = new BufferedReader(new FileReader("application.properties"))) {
            PROPERTIES.load(reader);
        } catch (IOException e) {
            System.out.println("Error loading properties: " + e.getMessage());
        }
    }
}
