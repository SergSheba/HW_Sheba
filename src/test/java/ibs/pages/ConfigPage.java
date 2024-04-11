package ibs.pages;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * Утилитный класс для управления свойствами конфигурации,
 * обеспечивает извлечение URL тестового стенда из конфигурационного файла.
 */

public class ConfigPage {
    public static FileInputStream fileInputStream;
    protected static Properties PROPERTIES;

    static {
        try {
            fileInputStream = new FileInputStream("src/main/resources/conf.properties");
            PROPERTIES = new Properties();
            PROPERTIES.load(fileInputStream);
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null)
                try {
                    fileInputStream.close();
                } catch (Throwable e) {
                    e.printStackTrace();
                }
        }
    }

    public static String getProperty(String key) {
        return PROPERTIES.getProperty(key);
    }
}
