package org.alexdev.alexandria.util;

import org.bukkit.entity.Player;

public class MetadataKeys {
    public static String ALLOW_BREAK_LOOT_CHESTS = "ALLOW_BREAK_LOOT_CHESTS";
    public static String BANISH_TIME_SINCE_KEY = "BANISH_TIME_SINCE";
    public static String TELEPORT_REQUEST_TIME_SINCE = "TELEPORT_REQUEST_TIME_SINCE_[TO_USER]";
    public static String CHAT_COLOR = "CHAT_COLOR";
    public static String VILLAGER_INVENTORY_EDIT = "VILLAGER_INVENTORY_EDIT";

    public static String build(String key, Player target) {
        return key.replace("[TO_USER]", target.getUniqueId().toString());
    }
}
