package org.alexdev.alexandria.util;

import org.alexdev.alexandria.util.time.Time;
import org.bukkit.Location;

import java.time.Duration;

public class TimeManager {
    public static long getUnixTime() {
        long unixTime = System.currentTimeMillis() / 1000L;
        return unixTime;
    }

    public static String describeLocation(Location location) {
        return location.getBlockX() + ", " + location.getBlockY() + ", " + location.getBlockZ() + " in " + location.getWorld().getName();
    }

    public static Time humanReadableTicks(long minecraftTicks) {
        long seconds = minecraftTicks / 20L;

        int anHourInSeconds = 3600;
        int aMinuteInSeconds = 60;

        return new Time((seconds / anHourInSeconds), ((seconds % anHourInSeconds) / aMinuteInSeconds), (seconds % aMinuteInSeconds));
    }

    public static String minutesToDaysHoursMinutes(long seconds) {
        Duration d = Duration.ofSeconds(seconds);
        long days = d.toDaysPart();
        long hours = d.toHoursPart();
        long minutes = d.toMinutesPart();
        return String.format("%d day(s), %d hour(s), %d minute(s)", days, hours, minutes);
    }
}
