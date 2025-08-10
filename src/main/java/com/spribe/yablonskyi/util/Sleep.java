package com.spribe.yablonskyi.util;

public class Sleep {

    public static void sleep(int sleepMs) {
        try {
            Thread.sleep(sleepMs);
        } catch (Exception ignored) {
        }
    }

}
