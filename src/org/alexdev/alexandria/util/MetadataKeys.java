package org.alexdev.alexandria.util;

import org.bukkit.entity.Player;

public class MetadataKeys {
    public static String BANISH_TIME_SINCE_KEY = "BANISH_TIME_SINCE";
    public static String TELEPORT_REQUEST_TIME_SINCE = "TELEPORT_REQUEST_TIME_SINCE_[TO_USER]";

    public static String build(String key, Player target) {
        return key.replace("[TO_USER]", target.getUniqueId().toString());
    }
}
