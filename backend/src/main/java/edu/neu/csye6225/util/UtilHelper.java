package edu.neu.csye6225.util;

import java.text.SimpleDateFormat;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.TimeZone;

public class UtilHelper {

    public static String convertToDatetimeString(ZonedDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss:SSS'Z'"));
    }
}
