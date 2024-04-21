package org.alexdev.alexandria.util;

import org.alexdev.alexandria.Alexandria;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

public class TeleportUtils {
    public static Location getSafeLocationInRadius(int radius, World world) {
        int attempts = 0;
        while (attempts < 100) { // Try up to 100 times to find a safe location
            double x = (Math.random() * (2 * radius)) - radius; // Random x coordinate
            double z = (Math.random() * (2 * radius)) - radius; // Random z coordinate

            // Location location = new Location(world, x, 0, z);
            Block block = world.getHighestBlockAt((int) x, (int) z);
            // Block block = location.getBlock(); // Get the block at this location

            if (isSafeLocation(block)) {
                return block.getRelative(BlockFace.UP).getLocation(); // Return the location if it's safe
            }
            attempts++;
        }
        // Alexandria.getInstance().getLogger().warning("Failed to find a safe location within " + radius + " blocks.");
        return null; // Return null if no safe location is found
    }

    private static boolean isSafeLocation(Block block) {
        return block.getType().isSolid() && block.getRelative(BlockFace.UP).getType() == Material.AIR && block.getRelative(BlockFace.UP).getRelative(BlockFace.UP).getType() == Material.AIR; // Check if both the block and the block above it are not solid
    }

    // Example usage:
    public void teleportPlayerToSafeLocation(Player player) {
        if (!player.getWorld().getName().equalsIgnoreCase("world")) {
            return;
        }

        World world = Bukkit.getWorld("world"); // Get the world
        if (world != null) {
            Location safeLocation = getSafeLocationInRadius(9000, world); // Get a safe location within 9000 blocks from (0,0)
            if (safeLocation != null) {
                player.teleport(safeLocation); // Teleport the player to the safe location
            }
        }
    }
}