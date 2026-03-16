package com.weather.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for date and time operations.
 */
public class DateTimeUtils {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("EEEE, dd MMMM");

    /**
     * Convert Unix timestamp to local time string (HH:mm:ss)
     */
    public static String formatUnixTimestampToTime(long timestamp) {
        LocalDateTime dateTime = LocalDateTime.ofInstant(
                Instant.ofEpochSecond(timestamp),
                ZoneId.systemDefault());
        return dateTime.format(TIME_FORMATTER);
    }

    /**
     * Convert Unix timestamp to local date-time string (dd/MM/yyyy HH:mm)
     */
    public static String formatUnixTimestampToDateTime(long timestamp) {
        LocalDateTime dateTime = LocalDateTime.ofInstant(
                Instant.ofEpochSecond(timestamp),
                ZoneId.systemDefault());
        return dateTime.format(DATE_TIME_FORMATTER);
    }

    /**
     * Convert milliseconds timestamp to local date-time string
     */
    public static String formatMillisToDateTime(long millis) {
        return formatUnixTimestampToDateTime(millis / 1000);
    }

    /**
     * Convert Unix timestamp to date string (EEEE, dd MMMM)
     */
    public static String formatUnixTimestampToDate(long timestamp) {
        LocalDateTime dateTime = LocalDateTime.ofInstant(
                Instant.ofEpochSecond(timestamp),
                ZoneId.systemDefault());
        return dateTime.format(DATE_FORMATTER);
    }

    /**
     * Get time ago from timestamp (e.g., "5 minutes ago")
     */
    public static String getTimeAgo(long timestamp) {
        long currentTime = System.currentTimeMillis();
        long diffInMillis = currentTime - timestamp;
        long diffInSeconds = diffInMillis / 1000;

        if (diffInSeconds < 60) {
            return "Just now";
        } else if (diffInSeconds < 3600) {
            long minutes = diffInSeconds / 60;
            return minutes + " minute" + (minutes > 1 ? "s" : "") + " ago";
        } else if (diffInSeconds < 86400) {
            long hours = diffInSeconds / 3600;
            return hours + " hour" + (hours > 1 ? "s" : "") + " ago";
        } else {
            long days = diffInSeconds / 86400;
            return days + " day" + (days > 1 ? "s" : "") + " ago";
        }
    }
}
