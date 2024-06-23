package org.alexdev.alexandria.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import org.alexdev.alexandria.Alexandria;
import org.alexdev.alexandria.managers.PlayerManager;
import org.alexdev.alexandria.managers.PluginPlayer;
import org.alexdev.alexandria.util.MetadataKeys;
import org.alexdev.alexandria.util.TeleportUtils;
import org.alexdev.alexandria.util.TimeUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class AfkCommandHandler implements CommandExecutor {
    private final Alexandria plugin;

    public AfkCommandHandler(Alexandria plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String commandName, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;
        PluginPlayer pluginPlayer = PlayerManager.getInstance().getPlayer(player);

        if (pluginPlayer == null) {
            return true;
        }

        if (!pluginPlayer.isAfk()) {
            pluginPlayer.goAfk();
        }
        else {
            pluginPlayer.unAfk();
        }

        return true;
    }

}
