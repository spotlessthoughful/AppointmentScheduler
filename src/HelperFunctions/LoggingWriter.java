package HelperFunctions;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class LoggingWriter {

    /**
     * Append a message to the log file login_activity.txt
     * @param message The message to be appended to the log file
     */
    public static void appendToLog(String message) {
        try {
            // Open the file
            BufferedWriter output = new BufferedWriter(new FileWriter("login_activity.txt", true));
            output.append(message);
            output.newLine();
            output.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
