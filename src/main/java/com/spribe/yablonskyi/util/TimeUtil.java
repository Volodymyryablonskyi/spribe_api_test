package com.spribe.yablonskyi.util;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class TimeUtil {

    public static LocalTime getCurrentTime() { return LocalTime.now(); }

    public static long getDuration(LocalTime start, LocalTime end) {
        return start.until(end, ChronoUnit.SECONDS);
    }

    public static String makeTimeReadable(long seconds) {
        if (seconds < 0) {
            long SECONDS_IN_DAY = 86400;
            seconds = SECONDS_IN_DAY - Math.abs(seconds);
        }
        return String.format("%02d:%02d:%02d", seconds / 3600, (seconds % 3600) / 60, (seconds % 60));
    }

}