package org.alexdev.alexandria.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;

import java.util.List;

public class BlockListener implements Listener {

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent event) {
        Block block = event.getBlock();

        if (!isBreakableSpawner(block)) {
            event.setCancelled(true);
            return;
        }
    }

    @EventHandler
    public void onBlockExplodeEvent(BlockExplodeEvent event) {
        List<Block> blocks = event.blockList();

        for (Block block : blocks) {
            if (!isBreakableSpawner(block)) {
                event.setCancelled(true);
                return;
            }
        }
    }

    private boolean isBreakableSpawner(Block block) {
        if (block.getType() != Material.SPAWNER) {
            return true;
        }

        if (!(block.getState() instanceof CreatureSpawner)) {
            return true;
        }

        CreatureSpawner spawner = (CreatureSpawner) block.getState();

        if (spawner.getSpawnedType() == EntityType.SILVERFISH) {
            return true;
        }

        return false;
    }
}
