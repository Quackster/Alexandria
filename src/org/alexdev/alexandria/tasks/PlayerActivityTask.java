package org.alexdev.alexandria.tasks;

import org.alexdev.alexandria.managers.PlayerManager;
import org.alexdev.alexandria.managers.PluginPlayer;
import org.alexdev.alexandria.util.TimeUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.TimeUnit;

public class PlayerActivityTask extends BukkitRunnable {
    public static final long AFK_THRESHOLD_SECONDS = 300;

    @Override
    public void run() {
        long currentTime = System.currentTimeMillis();
        long afkThreshold = AFK_THRESHOLD_SECONDS * 1000; // 5 minutes in milliseconds

        for (Player player : Bukkit.getOnlinePlayers()) {
            PluginPlayer pluginPlayer = PlayerManager.getInstance().getPlayer(player);

            if (pluginPlayer == null)
                continue;

            long lastActivity = pluginPlayer.getLastMovement();

            if (currentTime - lastActivity > afkThreshold) {
                pluginPlayer.goAfk();
            }
        }
    }
}
