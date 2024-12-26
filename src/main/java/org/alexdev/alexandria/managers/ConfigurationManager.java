package org.alexdev.alexandria.managers;

import org.alexdev.alexandria.Alexandria;
import org.bukkit.Location;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Logger;

public class ConfigurationManager {
    private final Alexandria plugin;
    private Logger logger;

    private List<String> enabledWorlds;

    public ConfigurationManager(Alexandria plugin) {
        this.plugin = plugin;
        this.plugin.saveDefaultConfig();
    }

    /**
     * Read the configuration
     */
    public void readConfig() {
        this.plugin.reloadConfig();
        this.enabledWorlds = this.plugin.getConfig().getStringList("enabled-worlds");
    }

    /**
     * Get if the world is enabled to be searched for not.
     * @param worldName the name of the world
     * @return whether the world is enabled or not
     */
    public boolean isEnabledWorld(String worldName) {
        return this.enabledWorlds.stream().anyMatch(x -> x.equalsIgnoreCase(worldName));
    }

    /**
     * Gets the name of the config section
     * @param location the location of the block
     * @return the YAML config section name
     */
    private String getConfigurationSectionName(Location location) {
        return "vaults." + createHash(location);
    }

    /**
     * Creates the hash of the config key by block location
     * @param location the location of the block
     * @return the MD5 hashed config key
     */
    private String createHash(Location location) {
        String toHash = location.getWorld().getName() + ";" + location.getBlockX() + ";" + location.getBlockY() + ";" + location.getBlockZ();

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(toHash.getBytes());
            byte[] digest = md.digest();

            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }

            return sb.toString().toLowerCase();
        } catch (NoSuchAlgorithmException e) {
            this.plugin.getLogger().severe("Could not hash the following: " + toHash);
            this.plugin.getLogger().severe(e.toString());
            return null;
        }
    }
}