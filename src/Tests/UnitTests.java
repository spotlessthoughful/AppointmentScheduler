package Tests;

import HelperFunctions.AppointmentTimes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class UnitTests {

    String timeZone = "America/Detroit";
    String utcTime = "22:00:00";
    String appointmentTimeConverted = AppointmentTimes.convertTime(timeZone, utcTime);
    String pacificTime = "America/Los_Angeles";
    ObservableList<String> pacificTimesList = FXCollections.observableArrayList("08:00:00", "10:00:00");
    ObservableList<String> pacificToEastern = AppointmentTimes.convertTimesEasternToLocal(pacificTime, pacificTimesList);

    @Test
    public void testAppointmentTimes() {
        // Tests that the appointment time is converted to the correct time zone
        assertEquals("17:00:00", appointmentTimeConverted);
    }

    @Test
    public void testEasternLocal() {
        // Tests that the eastern time is converted to the correct time zone
        assertEquals("05:00:00", pacificToEastern.get(0));
    }

    @Test
    public void utcToLocal() {
        // Tests that the utc time is converted to the correct time zone
        assertEquals("01:00:00", AppointmentTimes.convertTime(pacificTime, "09:00:00"));
    }

    @Test
    public void localToUTC() {
        // Tests that the current time is converted to the utc time zone
        assertEquals("09:00:00", AppointmentTimes.convertTimeLocalToUTC(pacificTime, "01:00:00"));
    }
}
