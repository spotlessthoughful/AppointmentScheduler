package HelperFunctions;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SystemTimestamp {

    /**
     * Function that gets time now
     * @return String
     */
    public static String getTimestamp() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dateFormatter.format(now);
    }

    /**
     * Function that gets time now plus 15 minutes
     * @return String
     */
    public static String getTimestampPlus15() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        now = now.plusMinutes(15);
        return dateFormatter.format(now);
    }

}
