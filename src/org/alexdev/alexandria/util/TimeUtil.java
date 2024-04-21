package org.alexdev.alexandria.util;

import org.bukkit.Location;

public class TimeUtil {
    public static long getUnixTime() {
        long unixTime = System.currentTimeMillis() / 1000L;
        return unixTime;
    }

    public static String describeLocation(Location location) {
        return location.getBlockX() + ", " + location.getBlockY() + ", " + location.getBlockZ() + " in " + location.getWorld().getName();
    }
}
