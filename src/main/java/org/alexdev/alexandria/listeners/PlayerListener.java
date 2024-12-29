package org.alexdev.alexandria.listeners;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.alexdev.alexandria.Alexandria;
import org.alexdev.alexandria.managers.PluginPlayer;
import org.alexdev.alexandria.managers.PlayerManager;
import org.alexdev.alexandria.util.MetadataKeys;
import org.alexdev.alexandria.util.TimeManager;
import org.alexdev.alexandria.util.enums.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.concurrent.TimeUnit;

public class PlayerListener implements Listener {
    private final Alexandria plugin;

    public PlayerListener(Alexandria plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (!PlayerManager.getInstance().hasPlayer(player)) {
            PlayerManager.getInstance().addPlayer(player);
        }

        this.plugin.getStorageManager().sanityCheckResetTime();

        player.sendMessage(Component.text()
                .append(Component.text("Welcome to ", Style.style(NamedTextColor.GOLD)))
                .append(Component.text("Alexandria", Style.style(NamedTextColor.GOLD, TextDecoration.BOLD)))
                .append(Component.text("! Enjoy your stay.", Style.style(NamedTextColor.GOLD)))
                .build());

        player.sendMessage("");
        
        player.sendMessage(Component.text()
                .append(Component.text("[", Style.style(NamedTextColor.WHITE)))
                .append(Component.text("*", Style.style(NamedTextColor.GRAY)))
                .append(Component.text("] ", Style.style(NamedTextColor.WHITE)))
                .append(Component.text("You can use ", Style.style(NamedTextColor.GRAY)))
                .append(Component.text("/banish ", Style.style(NamedTextColor.BLUE)))
                .append(Component.text("to be teleported away from spawn", Style.style(NamedTextColor.GRAY)))
                .build());

        player.sendMessage(Component.text()
                .append(Component.text("[", Style.style(NamedTextColor.WHITE)))
                .append(Component.text("*", Style.style(NamedTextColor.GRAY)))
                .append(Component.text("] ", Style.style(NamedTextColor.WHITE)))
                .append(Component.text("You can use ", Style.style(NamedTextColor.GRAY)))
                .append(Component.text("/tprequest <name> ", Style.style(NamedTextColor.BLUE)))
                .append(Component.text("to teleport to a friend", Style.style(NamedTextColor.GRAY)))
                .build());

        player.sendMessage(Component.text()
                .append(Component.text("Have any issues? Please contact ", Style.style(NamedTextColor.GRAY)))
                .append(Component.text("@Cuntypatra ", Style.style(NamedTextColor.BLUE)))
                .append(Component.text("on Twitter ", Style.style(NamedTextColor.GRAY)))
                .build());

        player.sendMessage("");

        player.sendMessage(Component.text()
                .append(Component.text("We're all friends on this server, please do not grief or steal from other people.", Style.style(NamedTextColor.YELLOW, TextDecoration.ITALIC)))
                .build());

        // player.sendMessage("");

        player.sendMessage(Component.text()
                .append(Component.text("Next trial vault reset: ", Style.style(NamedTextColor.GRAY)))
                .append(Component.text(TimeManager.minutesToDaysHoursMinutes(this.plugin.getStorageManager().getNextResetTime() - TimeManager.getUnixTime()),
                        Style.style(NamedTextColor.BLUE)))
                .build());

        if (!player.hasPlayedBefore()) {
            player.getInventory().addItem(new ItemStack(Material.STONE_SWORD, 1));
            player.getInventory().addItem(new ItemStack(Material.STONE_AXE, 1));
            player.getInventory().addItem(new ItemStack(Material.STONE_SHOVEL, 1));
            player.getInventory().addItem(new ItemStack(Material.APPLE, 16));
            player.getInventory().addItem(new ItemStack(Material.OAK_PLANKS, 16));
        }

        if (player.getPersistentDataContainer().has(new NamespacedKey(this.plugin, MetadataKeys.CHAT_COLOR))) {
            String chatColorName = player.getPersistentDataContainer().get(new NamespacedKey(this.plugin, MetadataKeys.CHAT_COLOR), PersistentDataType.STRING);
            Color chatColor = null;

            if (chatColorName != null) {
                chatColor = Color.valueOf(chatColorName.toUpperCase());
            }

            if (chatColor != null) {
                player.displayName(Component.text(player.getName(), Style.style(TextColor.color(chatColor.getRed(), chatColor.getGreen(), chatColor.getBlue()))));
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (PlayerManager.getInstance().hasPlayer(event.getPlayer())) {
            // PluginPlayer battlePlayer = PlayerManager.getInstance().getPlayer(event.getPlayer());
            PlayerManager.getInstance().removePlayer(event.getPlayer());
        }
    }

    @EventHandler
    public void onPlayerBucketEmpty(PlayerBucketEmptyEvent event) {

    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        PluginPlayer pluginPlayer = PlayerManager.getInstance().getPlayer(e.getPlayer());

        if (pluginPlayer == null)
            return;

        pluginPlayer.updateLastMovement();
    }

    @EventHandler
    public void onPlayerTeleportEvent(PlayerTeleportEvent e) {
        PluginPlayer pluginPlayer = PlayerManager.getInstance().getPlayer(e.getPlayer());

        if (pluginPlayer == null)
            return;

        pluginPlayer.updateLastMovement();
    }

    @EventHandler
    public void onHungerChange(FoodLevelChangeEvent event) {

    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        PluginPlayer pluginPlayer = PlayerManager.getInstance().getPlayer(event.getPlayer());

        if (pluginPlayer == null)
            return;

        pluginPlayer.updateLastMovement();
    }

    @EventHandler
    public void onHangingBreakByEntity(HangingBreakByEntityEvent event) {

    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getPlayer();

        if (player.hasMetadata(MetadataKeys.BANISH_TIME_SINCE_KEY)) {
            long secondsSince = player.getMetadata(MetadataKeys.BANISH_TIME_SINCE_KEY).get(0).asLong();

            if (secondsSince + TimeUnit.HOURS.toSeconds(24) > TimeManager.getUnixTime()) {
                player.removeMetadata(MetadataKeys.BANISH_TIME_SINCE_KEY, this.plugin);

                player.sendMessage(Component.text()
                        .append(Component.text("The ", Style.style(NamedTextColor.GRAY)))
                        .append(Component.text("/banish ", Style.style(NamedTextColor.DARK_RED)))
                        .append(Component.text("24 hour timeout has been reset", Style.style(NamedTextColor.GRAY)))
                        .build());
            }
        }
    }


    @EventHandler
    public void onPlayerItemConsume(PlayerItemConsumeEvent e)
    {

    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        PluginPlayer pluginPlayer = PlayerManager.getInstance().getPlayer(event.getPlayer());

        if (pluginPlayer == null)
            return;

        // event.getPlayer().sendMessage("sqrt: " + event.getFrom().distanceSquared(event.getTo()));
        // event.getPlayer().sendMessage("dist: " + event.getFrom().distanceSquared(event.getTo()));
        // event.getPlayer().sendMessage("**");

        final Location afk = pluginPlayer.getAfkLocation();

        if (afk == null || !event.getTo().getWorld().equals(afk.getWorld()) || afk.distanceSquared(event.getTo()) > 9) {
            pluginPlayer.updateLastMovement();
        }
    }

    @EventHandler
    public void onAsyncPlayerChatEvent(AsyncChatEvent event) {
        PluginPlayer pluginPlayer = PlayerManager.getInstance().getPlayer(event.getPlayer());

        if (pluginPlayer == null)
            return;

        pluginPlayer.updateLastMovement();
    }

    @EventHandler
    public void onPlayerSprint(PlayerToggleSprintEvent event) {
        PluginPlayer pluginPlayer = PlayerManager.getInstance().getPlayer(event.getPlayer());

        if (pluginPlayer == null)
            return;

        pluginPlayer.updateLastMovement();
    }


    @EventHandler
    public void onPlayerSneak(PlayerToggleSneakEvent event) {
        PluginPlayer pluginPlayer = PlayerManager.getInstance().getPlayer(event.getPlayer());

        if (pluginPlayer == null)
            return;

        pluginPlayer.updateLastMovement();
    }
}
