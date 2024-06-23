package org.alexdev.alexandria.util;

import org.alexdev.alexandria.util.time.Time;
import org.bukkit.Location;

public class TimeUtil {
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
}
