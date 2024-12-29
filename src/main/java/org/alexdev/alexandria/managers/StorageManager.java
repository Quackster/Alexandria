package org.alexdev.alexandria.managers;

import com.saicone.rtag.RtagBlock;
import org.alexdev.alexandria.Alexandria;
import org.alexdev.alexandria.util.TimeManager;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class StorageManager {
    private final Alexandria plugin;
    private final File configFile;
    private final YamlConfiguration config;

    public StorageManager(Alexandria plugin) {
        this.plugin = plugin;

        this.configFile = new File(this.plugin.getDataFolder(), "vault-block.yml");

        if (!this.configFile.exists()) {
            try {
                this.configFile.createNewFile();
            } catch (IOException e) {
                this.plugin.getLogger().severe("Could not create " + this.configFile.getName());
                this.plugin.getLogger().severe(e.toString());
            }
        }

        this.config = YamlConfiguration.loadConfiguration(this.configFile);

        if (!this.config.contains("next-reset-time")) {
            this.setNextReset();
        }
    }

    private void setNextReset() {
        this.config.set("next-reset-time", TimeManager.getUnixTime() + TimeUnit.DAYS.toSeconds(7));
        this.saveConfig();
    }

    public long getNextResetTime() {
        return this.config.getLong("next-reset-time");
    }

    public void sanityCheckResetTime() {
        if (TimeManager.getUnixTime() > getNextResetTime()) {
            setNextReset();
        }
    }

    /**
     * Save the repel tower by block list.
     * Will reload the list in TowerManager.
     *
     * @param vault the vault block
     * @return true, if successful
     */
    public boolean saveVaultExpiry(Block vault) {
        var configSectionName = this.getConfigurationSectionName(vault.getLocation());
        var configSection = this.getConfigurationSection(vault.getLocation());

        if (configSection != null) {
            this.config.set(configSectionName, null);
        }

        configSection = this.config.createSection(configSectionName);
        configSection.set("vault-expiry", TimeManager.getUnixTime() + TimeUnit.DAYS.toSeconds(7));

        this.saveConfig();

        return true;
    }

    /**
     * Save the repel tower by block list.
     * Will reload the list in TowerManager.
     *
     * @param vault the vault block
     * @return true, if successful
     */
    public long getVaultExpiry(Block vault) {
        var configSectionName = this.getConfigurationSectionName(vault.getLocation());
        var configSection = this.getConfigurationSection(vault.getLocation());

        if (configSection != null) {
            this.config.set(configSectionName, null);
        }

        return configSection.getLong("vault-expiry");
    }

    public boolean hasVaultExpiry(Block vault) {
        var configSectionName = this.getConfigurationSectionName(vault.getLocation());
        var configSection = this.getConfigurationSection(vault.getLocation());

        return configSection != null;
    }

    /**
     * Get the expected config section by block location
     *
     * @param location the location of the block
     * @return the YAML config section
     */
    private ConfigurationSection getConfigurationSection(Location location) {
        return this.config.getConfigurationSection(this.getConfigurationSectionName(location));
    }

    /**
     * Gets the name of the config section
     *
     * @param location the location of the block
     * @return the YAML config section name
     */
    private String getConfigurationSectionName(Location location) {
        return "vaults." + createHash(location);
    }

    /**
     * Creates the hash of the config key by block location
     *
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

    /**
     * Gets the list of towers config instance
     *
     * @return the yaml config instance
     */
    public YamlConfiguration getConfig() {
        return config;
    }

    /**
     * Saves the YAML config.
     */
    private void saveConfig() {
        try {
            this.config.save(this.configFile);
        } catch (IOException e) {
            this.plugin.getLogger().severe("Could not save " + this.configFile.getName());
            this.plugin.getLogger().severe(e.toString());
        }
    }

    public void resetVault(Block vaultBlock) {
        this.plugin.getStorageManager().saveVaultExpiry(vaultBlock);

        //Get the NBT Data of the vault block
        RtagBlock tag = new RtagBlock(vaultBlock);

        //Remove the UUID from server_data/rewarded_players NBT Data
        var uuids = new ArrayList<int[]>();
        tag.set(uuids, "server_data", "rewarded_players");

        tag.load();
    }
}