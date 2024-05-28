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

public class TeleportRequestCommandHandler implements CommandExecutor {
    private final int TELEPORT_RADIUS = 9500;
    private final Alexandria plugin;

    public TeleportRequestCommandHandler(Alexandria plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String commandName, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        if (args.length < 1) {
            return true;
        }

        Player player = (Player) sender;
        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            player.sendMessage(Component.text()
                    .append(Component.text("Player does not exist", Style.style(NamedTextColor.RED)))
                    .build());

            return true;
        }

        if (!target.isOnline()) {
            player.sendMessage(Component.text()
                    .append(Component.text("Player is not online", Style.style(NamedTextColor.RED)))
                    .build());

            return true;
        }

        String metadataKey = MetadataKeys.TELEPORT_REQUEST_TIME_SINCE;

        if (player.isOp()) {
            player.removeMetadata(metadataKey, this.plugin);
        }

        if (!player.getWorld().getName().equalsIgnoreCase("world")) {
            player.sendMessage(Component.text()
                    .append(Component.text("You are not in the overworld!", Style.style(NamedTextColor.RED)))
                    .build());
            return true;
        }

        if (player.hasMetadata(metadataKey)) {
            long secondsSince = player.getMetadata(metadataKey).get(0).asLong();

            if (secondsSince + TimeUnit.HOURS.toSeconds(24) > TimeUtil.getUnixTime()) {
                player.sendMessage(Component.text()
                        .append(Component.text("Please wait 24 hours before using ", Style.style(NamedTextColor.RED)))
                        .append(Component.text("/tprequest " + target.getName() + " ", Style.style(NamedTextColor.GRAY, TextDecoration.ITALIC)))
                        .append(Component.text("again", Style.style(NamedTextColor.RED)))
                        .build());
                return true;
            }
        }

        PluginPlayer targetPluginPlayer = PlayerManager.getInstance().getPlayer(target);

        if (targetPluginPlayer == null) {
            return true;
        }

        if (targetPluginPlayer.has("TPREQUESTFROM")) {
            player.sendMessage(Component.text()
                    .append(Component.text(target.getName() + " already has an active teleport request, and will need to use either ", Style.style(NamedTextColor.RED)))
                    .append(Component.text("/tpdecline ", Style.style(NamedTextColor.GRAY, TextDecoration.ITALIC)))
                    .append(Component.text("or ", Style.style(NamedTextColor.RED)))
                    .append(Component.text("/tpaccept ", Style.style(NamedTextColor.GRAY, TextDecoration.ITALIC)))
                    .append(Component.text("to clear it", Style.style(NamedTextColor.RED)))
                    .build());

            return true;
        }

        targetPluginPlayer.set("TPREQUESTFROM", player.getUniqueId().toString());

        player.sendMessage(Component.text()
                .append(Component.text("Teleport request sent to " + target.getName(), Style.style(NamedTextColor.RED)))
                .build());

        target.sendMessage(Component.text()
                .append(Component.text(player.getName() + " has requested to teleport to you, either enter ", Style.style(NamedTextColor.RED)))
                .append(Component.text("/tpdecline ", Style.style(NamedTextColor.GRAY, TextDecoration.ITALIC)))
                .append(Component.text("or ", Style.style(NamedTextColor.RED)))
                .append(Component.text("/tpaccept", Style.style(NamedTextColor.GRAY, TextDecoration.ITALIC)))
                .build());

        return true;
    }
}
