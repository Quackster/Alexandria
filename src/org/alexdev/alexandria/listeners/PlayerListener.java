package org.alexdev.alexandria.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.player.*;

public class PlayerListener implements Listener {
    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event) {

    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {

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
