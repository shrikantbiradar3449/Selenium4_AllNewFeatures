package utils;

public class ConfigReader {
    public static String getAppUrl() {
        return System.getProperty("app.url", "https://practicesoftwaretesting.com/");
    }
}
