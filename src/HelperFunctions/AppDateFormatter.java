package HelperFunctions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AppDateFormatter {

    /**
     * Convert string to date and time
     * @param dateString date and time string
     * @return LocalDateTime
     */
    public static LocalDateTime convertStringToDate(String dateString) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return LocalDateTime.parse(dateString, formatter);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return null;
    }
}
