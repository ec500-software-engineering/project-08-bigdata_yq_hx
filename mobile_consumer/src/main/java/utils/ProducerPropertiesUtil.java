package utils;

import java.io.IOException;
        import java.io.InputStream;
        import java.util.Properties;

public class ProducerPropertiesUtil {
    public static Properties properties = null;

    static {
        InputStream is = ClassLoader.getSystemResourceAsStream("kafka_producer.properties");
        properties = new Properties();
        try {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}