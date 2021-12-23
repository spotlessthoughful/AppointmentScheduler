package HelperFunctions;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.input.DataFormat;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AppointmentTimes {

    /**
     * Gets a list of appointment times from 8am-10pm in military time
     * @return ObservableList of times
     */
    public static ObservableList<String> getAppointmentTimes() {
        ObservableList<String> times = FXCollections.observableArrayList();
        for (int i = 8; i < 23; i++) {
            String time = String.format("%02d:00:00", i);
            times.add(time);
        }
        return times;
    }

    /**
     * Converts a date from UTC to local time
     * @param timeZone Timezone of user
     * @param time Time to convert
     * @return Converted time
     */
    public static String convertTime(String timeZone, String time) {

        DateFormat utcFormat = new SimpleDateFormat("HH:mm:ss");
        utcFormat.setTimeZone(java.util.TimeZone.getTimeZone("UTC"));

        return convert(timeZone, time, utcFormat);
    }

    /**
     * Converts the time between two timezones
     * @param timeZone Timezone of user
     * @param time Time to convert
     * @param tzFormat Format of time
     * @return Converted time
     */
    private static String convert(String timeZone, String time, DateFormat tzFormat) {
        Date date = null;

        try {
            date = tzFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        DateFormat localFormat = new SimpleDateFormat("HH:mm:ss");
        localFormat.setTimeZone(java.util.TimeZone.getTimeZone(timeZone));

        return localFormat.format(date);
    }

    /**
     * Converts a time from eastern to local time
     * @param timeZone Timezone of user
     * @param time Time to convert
     * @return Converted time
     */
    public static String convertTimeEasternToLocal(String timeZone, String time) {
        DateFormat easternFormat = new SimpleDateFormat("HH:mm:ss");
        easternFormat.setTimeZone(java.util.TimeZone.getTimeZone("America/New_York"));

        return convert(timeZone, time, easternFormat);
    }

    /**
     * Converts a time from eastern to local time
     * @param timeZone Timezone of user
     * @param times Times to convert
     * @return Converted times
     */
    public static ObservableList<String> convertTimesEasternToLocal(String timeZone, ObservableList<String> times) {
        ObservableList<String> localTimes = FXCollections.observableArrayList();
        for (String time : times) {
            localTimes.add(convertTimeEasternToLocal(timeZone, time));
        }
        return localTimes;
    }

    /**
     * Converts a time from local to utc time
     * @param timeZone Timezone of user
     * @param time Time to convert
     * @return Converted time
     */
    public static String convertTimeLocalToUTC(String timeZone, String time) {
        DateFormat localFormat = new SimpleDateFormat("HH:mm:ss");
        localFormat.setTimeZone(java.util.TimeZone.getTimeZone(timeZone));

        Date date = null;

        try {
            date = localFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        DateFormat utcFormat = new SimpleDateFormat("HH:mm:ss");
        utcFormat.setTimeZone(java.util.TimeZone.getTimeZone("UTC"));

        return utcFormat.format(date);
    }

    /**
     * Converts a time from local to utc time
     * @param timeZone Timezone of user
     * @param time Time to convert
     * @param format Format of time
     * @return Converted time
     */
    public static String convertTimeLocalToUTC(String timeZone, String time, String format) {
        DateFormat localFormat = new SimpleDateFormat(format);
        localFormat.setTimeZone(java.util.TimeZone.getTimeZone(timeZone));

        Date date = null;

        try {
            date = localFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        DateFormat utcFormat = new SimpleDateFormat(format);
        utcFormat.setTimeZone(java.util.TimeZone.getTimeZone("UTC"));

        return utcFormat.format(date);
    }

    /**
     * Converts date and time to users local date and time
     * @param timeZone Timezone of user
     * @param dateTime Date and time to convert
     * @return Converted date and time
     */
    public static String convertDateTimeFromUTCToLocal(String timeZone, String dateTime) {

        DateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        utcFormat.setTimeZone(java.util.TimeZone.getTimeZone("UTC"));

        return convertDate(timeZone, dateTime, utcFormat);
    }

    /**
     * Converts date and time to specified format
     * @param timeZone Timezone of user
     * @param time Time to convert
     * @param tzFormat Format of time
     * @return Converted time
     */
    private static String convertDate(String timeZone, String time, DateFormat tzFormat) {
        Date date = null;

        try {
            date = tzFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        DateFormat localFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        localFormat.setTimeZone(java.util.TimeZone.getTimeZone(timeZone));

        return localFormat.format(date);
    }

    /**
     * Converts date and time from local to utc
     * @param timeZone Timezone of user
     * @param dateTime Date and time to convert
     * @return  Converted date and time
     */
    public static String convertDateTimeFromLocalToUTC(String timeZone, String dateTime) {

        DateFormat localFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        localFormat.setTimeZone(java.util.TimeZone.getTimeZone(timeZone));

        Date date = null;

        try{
            date = localFormat.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        DateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        utcFormat.setTimeZone(java.util.TimeZone.getTimeZone("UTC"));

        return utcFormat.format(date);
    }

    /**
     * Converts date and time to users local date and time
     * @param timeZone Timezone of user
     * @param dateTime Date and time to convert
     * @param format Format of date and time
     * @return Converted date and time
     */
    public static String convertDateTimeFromUTCToLocal(String timeZone, String dateTime, String format) {

        DateFormat utcFormat = new SimpleDateFormat(format);
        utcFormat.setTimeZone(java.util.TimeZone.getTimeZone("UTC"));

        return convertDate(timeZone, dateTime, utcFormat);
    }

}
