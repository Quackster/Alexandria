package org.alexdev.alexandria.listeners;

import org.alexdev.alexandria.Alexandria;
import org.alexdev.alexandria.util.BlockUtil;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;

import java.util.ArrayList;
import java.util.List;

public class BlockListener implements Listener {
    private final Alexandria plugin;

    public BlockListener(Alexandria plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent event) {
        if (event.getPlayer().getGameMode() == GameMode.CREATIVE)
            return;

        Block block = event.getBlock();

        if (!BlockUtil.isBreakableBlockOrSpawner(block)) {
            event.setCancelled(true);
            return;
        }
    }

    @EventHandler
    public void onBlockExplodeEvent(BlockExplodeEvent event) {
        List<Block> blocks = event.blockList();

        for (Block block : new ArrayList<>(blocks)) {
            if (!BlockUtil.isBreakableBlockOrSpawner(block)) {
                blocks.remove(block);
            }
        }
    }
}
