package org.alexdev.alexandria;

import com.loohp.lotterysix.objects.Scheduler;
import org.alexdev.alexandria.commands.*;
import org.alexdev.alexandria.listeners.*;
import org.alexdev.alexandria.managers.ConfigurationManager;
import org.alexdev.alexandria.managers.PlayerManager;
import org.alexdev.alexandria.managers.StorageManager;
import org.alexdev.alexandria.tasks.PlayerActivityTask;
import org.alexdev.alexandria.util.RecipeGenerator;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class Alexandria extends JavaPlugin {
    public static final boolean ENABLE_AFK_CHECK = true;
    public static final boolean ALERT_AFK_MESSAGE = true;

    private static Alexandria instance;

    private ConfigurationManager configurationManager;
    private StorageManager storageManager;

    private Logger logger;
    private PlayerActivityTask playerActivityTask;

    @Override
    public void onEnable() {
        instance = this;
        logger = getLogger();
        logger.info("Starting plugin");

        // Load singletons
        this.configurationManager = new ConfigurationManager(this);
        this.configurationManager.readConfig();

        this.storageManager = new StorageManager(this);

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

        RecipeGenerator.createSlabConversionList(this);

        logger.info("Finished");
    }

    /**
     * Register the listeners.
     */
    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
        getServer().getPluginManager().registerEvents(new BlockListener(this), this);
        getServer().getPluginManager().registerEvents(new EntityListener(this), this);
        getServer().getPluginManager().registerEvents(new AnimalListener(this), this);
        getServer().getPluginManager().registerEvents(new VillagerInventoryListener(this), this);
        getServer().getPluginManager().registerEvents(new VillagerTradeListener(this), this);
        getServer().getPluginManager().registerEvents(new ChunkListener(this), this);
    }

    @Override
    public void onDisable() {
        PlayerManager.getInstance().getPlayers().clear();
    }

    public ConfigurationManager getConfigurationManager() {
        return configurationManager;
    }

    public StorageManager getStorageManager() {
        return storageManager;
    }
}
