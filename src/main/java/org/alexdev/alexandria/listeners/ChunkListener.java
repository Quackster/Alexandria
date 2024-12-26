package org.alexdev.alexandria.listeners;

import org.alexdev.alexandria.Alexandria;
import org.alexdev.alexandria.util.TimeManager;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;

import java.util.concurrent.TimeUnit;

public class ChunkListener implements Listener {
    private final Alexandria plugin;

    public ChunkListener(Alexandria plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent event) {
        if (!this.plugin.getConfigurationManager().isEnabledWorld(event.getWorld().getName()))
            return;

        Chunk chunk = event.getChunk();

        for (var blockState : chunk.getTileEntities()) {
            if (blockState.getType() != Material.VAULT) {
                continue;
            }

            var vaultBlock = blockState.getBlock();

            if (!(this.plugin.getStorageManager().hasVaultDate(vaultBlock)) ||
                    ((System.currentTimeMillis() / 1000L) + TimeUnit.DAYS.toSeconds(7)) >
                            this.plugin.getStorageManager().getVaultDate(vaultBlock)) {
                this.plugin.getStorageManager().resetVault(vaultBlock);
            }
            else {
                this.plugin.getStorageManager().saveVaultData(vaultBlock);
            }
        }

    }
}