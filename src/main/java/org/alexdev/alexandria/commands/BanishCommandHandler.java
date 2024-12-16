package org.alexdev.alexandria.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import org.alexdev.alexandria.Alexandria;
import org.alexdev.alexandria.util.MetadataKeys;
import org.alexdev.alexandria.util.TeleportUtils;
import org.alexdev.alexandria.util.TimeUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class BanishCommandHandler implements CommandExecutor {
    private final int TELEPORT_RADIUS = 9000;
    private final int NETHER_TELEPORT_RADIUS = 4500;
    private final Alexandria plugin;

    public BanishCommandHandler(Alexandria plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String commandName, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        if (player.isOp()) {
            player.removeMetadata(MetadataKeys.BANISH_TIME_SINCE_KEY, this.plugin);
        }

        /*
        if (!player.getWorld().getName().equalsIgnoreCase("world")) {
            player.sendMessage(Component.text()
                    .append(Component.text("You are not in the overworld!", Style.style(NamedTextColor.RED)))
                    .build());
            return true;
        }
        */

        if (player.getWorld().getEnvironment() != World.Environment.NORMAL &&
                player.getWorld().getEnvironment() != World.Environment.NETHER) {
            player.sendMessage(Component.text()
                    .append(Component.text("You are not in the overworld or Nether!", Style.style(NamedTextColor.RED)))
                    .build());
            return true;
        }

        if (player.hasMetadata(MetadataKeys.BANISH_TIME_SINCE_KEY)) {
            long secondsSince = player.getMetadata(MetadataKeys.BANISH_TIME_SINCE_KEY).get(0).asLong();

            if (secondsSince + TimeUnit.HOURS.toSeconds(24) > TimeUtil.getUnixTime()) {
                player.sendMessage(Component.text()
                        .append(Component.text("Please wait 24 hours before using /banish again", Style.style(NamedTextColor.RED)))
                        .build());
                return true;
            }
        }

        player.sendMessage(Component.text()
                .append(Component.text("Finding a safe location for you...", Style.style(NamedTextColor.GRAY, TextDecoration.ITALIC)))
                .build());

        Location safeLocation = TeleportUtils.getSafeLocationInRadius(TELEPORT_RADIUS, player.getWorld());

        if (safeLocation == null) {
            player.sendMessage(Component.text()
                    .append(Component.text("Could not find a safe location, please try again, oops!"))
                    .build());
            return true;
        }

        player.teleportAsync(safeLocation).thenRun(() -> {
            player.sendMessage(Component.text()
                    .append(Component.text("You have been teleported!", Style.style(NamedTextColor.YELLOW)))
                    .build());

            player.sendMessage(Component.text()
                    .append(Component.text("You will not be able to use this command again for 24 hours", Style.style(NamedTextColor.WHITE, TextDecoration.BOLD)))
                            .build());

            player.setMetadata(MetadataKeys.BANISH_TIME_SINCE_KEY, new FixedMetadataValue(this.plugin, TimeUtil.getUnixTime()));
        });

        return true;
    }

}
