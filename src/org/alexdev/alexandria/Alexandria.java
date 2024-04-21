package org.alexdev.alexandria;

import org.alexdev.alexandria.commands.BanishCommandHandler;
import org.alexdev.alexandria.listeners.PlayerListener;
import org.alexdev.alexandria.configuration.ConfigurationManager;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class Alexandria extends JavaPlugin {
    private static Alexandria instance;
    private Logger logger;

    @Override
    public void onEnable() {
        instance = this;
        logger = getLogger();
        logger.info("Starting plugin");

        // Load singletons
        ConfigurationManager.getInstance();

        // Load configuration
        saveDefaultConfig();
        ConfigurationManager.getInstance().readConfig(getConfig());

        // Command handling
        CommandExecutor myCommands = new BanishCommandHandler();
        getCommand("banish").setExecutor(myCommands);

        this.registerListeners();
        logger.info("Finished");
    }

    /**
     * Register the listeners.
     */
    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
    }

    @Override
    public void onDisable() {

    }

    /**
     * Get the java plugin instance.
     *
     * @return the plugin instance
     */
    public static Alexandria getInstance() {
        return instance;
    }
}
