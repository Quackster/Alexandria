package org.alexdev.alexandria.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.entity.minecart.StorageMinecart;

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

    /*
    public static boolean isBreakableBlockOrChest(Player player, Block block) {
        if (block.getType() != Material.CHEST && block.getType() != Material.CHEST_MINECART) {
            return true;
        }

        if (!(block.getBlockData() instanceof Chest chest)) {
            return true;
        }

        if (chest.hasLootTable()) {
            if (player != null) {
                if (player.hasMetadata(MetadataKeys.ALLOW_BREAK_LOOT_CHESTS)) {
                    return true;
                } else {
                    player.sendMessage(Component.text()
                            .append(Component.text("Are you sure you want to break loot chests? They eventually replenish. Enter ", Style.style(NamedTextColor.RED)))
                            .append(Component.text("/breaklootchests ", Style.style(NamedTextColor.YELLOW)))
                            .append(Component.text("to allow you to break loot chests.", Style.style(NamedTextColor.RED)))
                            .build());

                    return false;
                }
            }

            return false;
        }

        return false;
    }

    public static boolean isBreakableBlockOrMinecartChest(Player player, Entity entity) {
        if (entity.getType() != EntityType.CHEST_MINECART) {
            return true;
        }

        StorageMinecart chest = (StorageMinecart) entity;

        if (chest.hasLootTable()) {
            if (player != null) {
                if (player.hasMetadata(MetadataKeys.ALLOW_BREAK_LOOT_CHESTS)) {
                    return true;
                } else {
                    player.sendMessage(Component.text()
                            .append(Component.text("Are you sure you want to break loot chests? They eventually replenish. Enter ", Style.style(NamedTextColor.RED)))
                            .append(Component.text("/breaklootchests ", Style.style(NamedTextColor.YELLOW)))
                            .append(Component.text("to allow you to break loot chests.", Style.style(NamedTextColor.RED)))
                            .build());

                    return false;
                }
            }

            return false;
        }

        return false;
    }
    */
}
