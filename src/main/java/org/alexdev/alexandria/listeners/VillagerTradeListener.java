package org.alexdev.alexandria.listeners;

import org.alexdev.alexandria.Alexandria;
import org.alexdev.alexandria.util.BlockUtil;
import org.alexdev.alexandria.util.MetadataKeys;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.memory.MemoryKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.Random;
import java.util.UUID;

public class VillagerTradeListener implements Listener {
    private final Random random;
    private final Alexandria plugin;

    public VillagerTradeListener(Alexandria plugin) {
        this.random = new Random();
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent event) {
        Block block = event.getBlock();

        for (var villager : block.getWorld().getNearbyEntitiesByType(Villager.class, block.getLocation(), 20)) {
            var jobSiteLocation = villager.getMemory(MemoryKey.JOB_SITE);

            if (jobSiteLocation == null)
            {
                continue;
            }

            if (!jobSiteLocation.getBlock().getLocation().equals(block.getLocation()))
            {
                continue;
            }

            var jobBlock = jobSiteLocation.getBlock();

            // event.getPlayer().sendMessage("you broke a job block! " + jobBlock.getType().name());
        }
    }
}
