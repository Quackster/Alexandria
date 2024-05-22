package org.alexdev.alexandria.util;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;

public class BlockUtil {
    public static boolean isBreakableBlockOrSpawner(Block block) {
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
