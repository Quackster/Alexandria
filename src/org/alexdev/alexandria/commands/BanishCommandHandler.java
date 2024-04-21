package org.alexdev.alexandria.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
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

import java.util.concurrent.TimeUnit;

public class BanishCommandHandler implements CommandExecutor {
    private int TELEPORT_RADIUS = 9500;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandName, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        if (!player.getWorld().getName().equalsIgnoreCase("world")) {
            player.sendMessage(Component.text()
                    .append(Component.text("You are not in the overworld!", Style.style(NamedTextColor.RED)))
                    .build());


            return true;
        }

        if (player.hasMetadata(MetadataKeys.BANISH_METADATA)) {
            long secondsSince = player.getMetadata(MetadataKeys.BANISH_METADATA).get(0).asLong();

            if (!(TimeUnit.HOURS.toSeconds(24) > TimeUtil.getUnixTime() - secondsSince)) {


            }
        }

        player.sendMessage(Component.text()
                .append(Component.text("Finding a safe location for you...", Style.style(NamedTextColor.GRAY, TextDecoration.ITALIC))
                        .toBuilder().build()));

        Location safeLocation = TeleportUtils.getSafeLocationInRadius(TELEPORT_RADIUS, Bukkit.getServer().getWorld("world"));

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
                    .append(Component.text("You will not be able to use this command again for 24 hours", Style.style(NamedTextColor.WHITE, TextDecoration.BOLD))
                            .toBuilder().build()));
        });

        return true;
    }

}
