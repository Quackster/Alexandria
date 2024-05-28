package org.alexdev.alexandria.configuration;

import java.io.File;
import java.util.logging.Logger;

import org.alexdev.alexandria.Alexandria;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigurationManager {
    private static ConfigurationManager instance;
    private final Alexandria plugin;
    private Logger logger;// = BattleCTF.P.getLogger();

    public ConfigurationManager(Alexandria plugin) {
        this.plugin = plugin;
        this.logger = this.plugin.getLogger();
    }

    /**
     * Set configuration from values in a file configuration.
     *
     * @param savedConfig the existing file configuration
     */
    public void readConfig(FileConfiguration savedConfig) {
        this.plugin.saveDefaultConfig();
        // YamlConfiguration.loadConfiguration(new File(this.plugin.getDataFolder().getAbsolutePath(), "file.test"));
        //this.variable = savedConfig.getBoolean("variable",false);
    }
}
