package org.alexdev.alexandria.util;

import com.loohp.lotterysix.objects.Scheduler;
import org.alexdev.alexandria.Alexandria;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class TeleportUtils {
    public static Location getSafeLocationInRadius(JavaPlugin plugin, int radius, World world) {
        int attempts = 0;
        while (attempts < 100) { // Try up to 100 times to find a safe location
            double x = ((Math.random() * (2 * radius)) - radius) + 500; // Random x coordinate
            double z = ((Math.random() * (2 * radius)) - radius) + 500; // Random z coordinate

            Location location = new Location(world, x, 0, z);

            Location safeLocation =
                    Scheduler.executeRegionCall(plugin, () ->  {
                        Block block = world.getHighestBlockAt((int) x, (int) z);

                        // System.out.println("HighestBlockAt: " + block.getType().name());

                        if (isSafeLocation(block.getLocation())) {
                            return block.getRelative(BlockFace.UP).getLocation(); // Return the location if it's safe
                        }

                        return null;

                    }, location);


            if (safeLocation != null) {

                System.out.println("Safe loc 1: " + safeLocation.getX() + " | " + safeLocation.getY() + " | " + safeLocation.getZ());

                return safeLocation;
            }
            else {

                System.out.println("Safe loc 2: " + location.getX()  + " | " + location.getY() + " | " + location.getZ());

            }
            attempts++;
        }
        // Alexandria.getInstance().getLogger().warning("Failed to find a safe location within " + radius + " blocks.");
        return null; // Return null if no safe location is found
    }

    @SuppressWarnings("deprecation")
    public static boolean isSafeLocation(Location location) {
        try {
            Block feet = location.getBlock();
            if (!feet.getType().isTransparent() && !feet.getLocation().add(0, 1, 0).getBlock().getType().isTransparent()) {
                return false; // not transparent (will suffocate)
            }
            Block head = feet.getRelative(BlockFace.UP);
            if (!head.getType().isTransparent()) {
                return false; // not transparent (will suffocate)
            }
            Block ground = feet.getRelative(BlockFace.DOWN);
            // returns if the ground is solid or not.
            return ground.getType().isSolid();
        } catch (Exception err) {
            err.printStackTrace();
        }
        return false;
    }

    /*
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
    }*/
}