package utils;

public class ConfigReader {

    public static String getBaseUrl() {
        return System.getProperty("base.url", "https://stellarburgers.education-services.ru");
    }

}