package org.alexdev.alexandria.listeners;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import org.alexdev.alexandria.managers.PluginPlayer;
import org.alexdev.alexandria.managers.PlayerManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;

public class PlayerListener implements Listener {
    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (!PlayerManager.getInstance().hasPlayer(player)) {
            PlayerManager.getInstance().addPlayer(player);
        }

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
                .append(Component.text("@AIexandriaQueen ", Style.style(NamedTextColor.BLUE)))
                .append(Component.text("on Twitter ", Style.style(NamedTextColor.GRAY)))
                .build());

        player.sendMessage("");

        player.sendMessage(Component.text()
                .append(Component.text("We're all friends on this server, please do not grief or steal from other people.", Style.style(NamedTextColor.YELLOW, TextDecoration.ITALIC)))
                .build());

        player.sendMessage("");

        if (!player.hasPlayedBefore()) {
            player.getInventory().addItem(new ItemStack(Material.STONE_SWORD, 1));
            player.getInventory().addItem(new ItemStack(Material.STONE_AXE, 1));
            player.getInventory().addItem(new ItemStack(Material.STONE_SHOVEL, 1));
            player.getInventory().addItem(new ItemStack(Material.APPLE, 16));
            player.getInventory().addItem(new ItemStack(Material.OAK_PLANKS, 16));
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

    }

    @EventHandler
    public void onPlayerTeleportEvent(PlayerTeleportEvent e) {

    }

    @EventHandler
    public void onHungerChange(FoodLevelChangeEvent event) {

    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent e) {

    }

    @EventHandler
    public void onHangingBreakByEntity(HangingBreakByEntityEvent event) {

    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {

    }


    @EventHandler
    public void onPlayerItemConsume(PlayerItemConsumeEvent e)
    {

    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {


    }

    @EventHandler
    public void onPlayerSprint(PlayerToggleSprintEvent e) {

    }


    @EventHandler
    public void onPlayerSneak(PlayerToggleSneakEvent e) {

    }
}
