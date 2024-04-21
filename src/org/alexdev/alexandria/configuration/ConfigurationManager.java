package org.alexdev.alexandria.configuration;

import java.io.File;
import java.util.logging.Logger;

import org.alexdev.alexandria.Alexandria;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigurationManager {
    private static ConfigurationManager instance;
    private Logger logger;// = BattleCTF.P.getLogger();

    public ConfigurationManager() {
        this.logger = Alexandria.getInstance().getLogger();
    }

    /**
     * Set configuration from values in a file configuration.
     *
     * @param savedConfig the existing file configuration
     */
    public void readConfig(FileConfiguration savedConfig) {
        Alexandria.getInstance().saveDefaultConfig();
        YamlConfiguration.loadConfiguration(new File(Alexandria.getInstance().getDataFolder().getAbsolutePath(), "file.test"));
        //this.variable = savedConfig.getBoolean("variable",false);
    }

    /**
     * Get configuration singleton.
     *
     * @return the configuration instance
     */
    public static ConfigurationManager getInstance() {
        if (instance == null) {
            instance = new ConfigurationManager();
        }

        return instance;
    }
}
