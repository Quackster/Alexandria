package org.alexdev.alexandria;

import com.loohp.lotterysix.objects.Scheduler;
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
    public static final boolean ALERT_AFK_MESSAGE = true;

    private static Alexandria instance;

    private ConfigurationManager configurationManager;

    private Logger logger;
    private PlayerActivityTask playerActivityTask;

    @Override
    public void onEnable() {
        instance = this;
        logger = getLogger();
        logger.info("Starting plugin");

        // Load singletons
        this.configurationManager = new ConfigurationManager(this);

        // Load configuration
        saveDefaultConfig();

        this.configurationManager.readConfig(getConfig());

        // Reload players
        PlayerManager.getInstance().reloadPlayers();
        this.logger.info("There are " + PlayerManager.getInstance().getPlayers().size() + " players stored");

        // Command handling
        getCommand("banish").setExecutor(new BanishCommandHandler(this));
        getCommand("tprequest").setExecutor(new TeleportRequestCommandHandler(this));
        getCommand("tpdecline").setExecutor(new TeleportDeclineCommandHandler(this));
        getCommand("tpaccept").setExecutor(new TeleportAcceptCommandHandler(this));
        getCommand("afk").setExecutor(new AfkCommandHandler(this));
        getCommand("chunkage").setExecutor(new ChunkAgeCommandHandler(this));

        // Command + tab handling
        getCommand("setchatcolour").setExecutor(new SetChatColorCommandHandler(this));
        getCommand("setchatcolour").setTabCompleter(new SetChatColorCommandHandler(this));

        this.registerListeners();

        if (ENABLE_AFK_CHECK) {
            Scheduler.runTaskTimer(this, new PlayerActivityTask(), 20L, 20L);
        }

        logger.info("Finished");
    }

    /**
     * Register the listeners.
     */
    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
        getServer().getPluginManager().registerEvents(new BlockListener(this), this);
        getServer().getPluginManager().registerEvents(new EntityListener(this), this);
    }

    @Override
    public void onDisable() {
        PlayerManager.getInstance().getPlayers().clear();
    }

    public ConfigurationManager getConfigurationManager() {
        return configurationManager;
    }
}
