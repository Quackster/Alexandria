package org.alexdev.alexandria;

import org.alexdev.alexandria.commands.*;
import org.alexdev.alexandria.listeners.BlockListener;
import org.alexdev.alexandria.listeners.EntityListener;
import org.alexdev.alexandria.listeners.PlayerListener;
import org.alexdev.alexandria.configuration.ConfigurationManager;
import org.alexdev.alexandria.managers.PlayerManager;
import org.alexdev.alexandria.tasks.PlayerActivityTask;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class Alexandria extends JavaPlugin {
    public static final boolean ENABLE_AFK_CHECK = true;

    private static Alexandria instance;
    private Logger logger;
    private PlayerActivityTask playerActivityTask;

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

        // Reload players
        PlayerManager.getInstance().reloadPlayers();
        this.logger.info("There are " + PlayerManager.getInstance().getPlayers().size() + " players stored");

        // Command handling
        getCommand("banish").setExecutor(new BanishCommandHandler());
        getCommand("tprequest").setExecutor(new TeleportRequestCommandHandler());
        getCommand("tpdecline").setExecutor(new TeleportDeclineCommandHandler());
        getCommand("tpaccept").setExecutor(new TeleportAcceptCommandHandler());
        getCommand("afk").setExecutor(new AfkCommandHandler());

        // Command + tab handling
        getCommand("setchatcolour").setExecutor(new SetChatColorCommandHandler());
        getCommand("setchatcolour").setTabCompleter(new SetChatColorCommandHandler());

        this.registerListeners();

        if (ENABLE_AFK_CHECK) {
            this.playerActivityTask = new PlayerActivityTask();
            this.getServer().getGlobalRegionScheduler().runAtFixedRate(this, stask -> this.playerActivityTask.run(), 20L, 20L);
        }

        logger.info("Finished");
    }

    /**
     * Register the listeners.
     */
    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        getServer().getPluginManager().registerEvents(new BlockListener(), this);
        getServer().getPluginManager().registerEvents(new EntityListener(), this);
    }

    @Override
    public void onDisable() {
        PlayerManager.getInstance().getPlayers().clear();
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
