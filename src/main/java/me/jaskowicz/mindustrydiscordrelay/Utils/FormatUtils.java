package me.jaskowicz.mindustrydiscordrelay.Utils;

public class FormatUtils {

    public static String formatTime(long duration) {
        if(duration == Long.MAX_VALUE)
            return "MAX_VALUE";
        long seconds = Math.round(duration/1000.0);
        long hours = seconds/(60*60);
        seconds %= 60*60;
        long minutes = seconds/60;
        seconds %= 60;
        return (hours>0 ? hours+" hours " : "") + (minutes<10 ? "0"+minutes : minutes) + " minutes and " + (seconds<10 ? "0"+seconds : seconds) + " seconds";
    }

    public static long getHoursFromTime(long duration) {
        long seconds = Math.round(duration/1000.0);
        return seconds/(60*60);
    }
}
