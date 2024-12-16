package org.alexdev.alexandria.util;

import com.loohp.lotterysix.objects.Scheduler;
import org.alexdev.alexandria.Alexandria;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.Random;
import java.util.Set;

//public class TeleportUtils {
    /*
    // See https://github.com/EssentialsX/Essentials/blob/2.x/Essentials/src/main/java/com/earth2me/essentials/utils/LocationUtil.java#L24-L31
    private static final Set<Material> BAD_BLOCKS = EnumSet.of(
            Material.LAVA, Material.WATER, Material.CACTUS, Material.CAMPFIRE, Material.FIRE, Material.MAGMA_BLOCK,
            Material.SOUL_CAMPFIRE, Material.SOUL_FIRE, Material.SWEET_BERRY_BUSH, Material.WITHER_ROSE,
            Material.END_PORTAL, Material.NETHER_PORTAL
    );

    public static Location getSafeLocationInRadius(JavaPlugin plugin, int radius, World world) {
        Location safeLocation = null;

        int attempts = 0;

        while (attempts < 100 && safeLocation == null) {
            int x = (int) ((Math.random() * (2 * radius)) - radius) + 500;
            int z = (int) ((Math.random() * (2 * radius)) - radius) + 500;

            final Location location = new Location(world, x, 0, z);

            safeLocation = generateSafeLocation(x, z, location.getWorld());// Scheduler.executeRegionCall(plugin, () -> { return generateSafeLocation(x, z, location.getWorld()); }, location);

            attempts++;
        }

        return safeLocation;
    }

    public static @Nullable Location generateSafeLocation(int x, int z, @NotNull World world) {
        var chunk = world.getChunkAtAsync(x >> 4, z >> 4).thenApply(c -> c.getChunkSnapshot(true, false, false)).join();

        int y = createSafeY(
                chunk,
                world.getEnvironment() == World.Environment.NETHER,
                x & 0xF,
                z & 0xF,
                world.getMinHeight(), world.getMaxHeight()
        );

        return y != Integer.MIN_VALUE ? new Location(world, x, y, z) : null;
    }

    private static int createSafeY(@NotNull ChunkSnapshot chunk, boolean isNether, int blockX, int blockZ, int minY, int maxY) {
        return isNether ?
                getSafeYInNether(chunk, blockX, blockZ) :
                getSafeHighestY(chunk, blockX, blockZ, minY, maxY);
    }

    private static int getSafeHighestY(ChunkSnapshot chunk, int blockX, int blockZ, int minY, int maxY) {
        int y = chunk.getHighestBlockYAt(blockX, blockZ) + 1;
        return y != minY && y + 1 != maxY && isLocationSafe(chunk, blockX, y, blockZ) ? y : Integer.MIN_VALUE;
    }

    private static int getSafeYInNether(ChunkSnapshot chunk, int blockX, int blockZ) {
        for (int y = 32; y < 100; y++) { // y < 32 can be lava, and 100 < y is basically a ceiling
            if (isLocationSafe(chunk, blockX, y, blockZ)) {
                return y;
            }
        }

        return Integer.MIN_VALUE;
    }

    private static boolean isLocationSafe(@NotNull ChunkSnapshot chunk, int blockX, int y, int blockZ) {
        var floor = chunk.getBlockType(blockX, y - 1, blockZ);

        if (!floor.isSolid() || BAD_BLOCKS.contains(floor)) {
            return false;
        }

        return chunk.getBlockType(blockX, y, blockZ).isAir() && chunk.getBlockType(blockX, y + 1, blockZ).isAir();
    }

     */
    public class TeleportUtils {
        public static Location getSafeLocationInRadius(int radius, World world) {
            int attempts = 0;
            while (attempts < 100) { // Try up to 100 times to find a safe location
                double x = ((Math.random() * (2 * radius)) - radius) + 500; // Random x coordinate
                double z = ((Math.random() * (2 * radius)) - radius) + 500; // Random z coordinate

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
//}