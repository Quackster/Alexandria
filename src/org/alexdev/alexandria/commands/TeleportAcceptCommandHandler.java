package org.alexdev.alexandria.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import org.alexdev.alexandria.Alexandria;
import org.alexdev.alexandria.managers.PlayerManager;
import org.alexdev.alexandria.managers.PluginPlayer;
import org.alexdev.alexandria.util.MetadataKeys;
import org.alexdev.alexandria.util.TimeUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class TeleportAcceptCommandHandler implements CommandExecutor {
    private final int TELEPORT_RADIUS = 9500;

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

        if (!player.getWorld().getName().equalsIgnoreCase("world")) {
            player.sendMessage(Component.text()
                    .append(Component.text("You are not in the overworld!", Style.style(NamedTextColor.RED)))
                    .build());
            return true;
        }

        if (!pluginPlayer.has("TPREQUESTFROM")) {
            player.sendMessage(Component.text()
                    .append(Component.text("You have no active teleport request", Style.style(NamedTextColor.RED)))
                    .build());
            return true;
        }

        OfflinePlayer offlineTarget = Bukkit.getOfflinePlayer(UUID.fromString(pluginPlayer.get("TPREQUESTFROM")));
        pluginPlayer.remove("TPREQUESTFROM");

        if (!offlineTarget.isOnline() || offlineTarget.getPlayer() == null) {
            player.sendMessage(Component.text()
                    .append(Component.text("The " + (offlineTarget.getName() != null ? offlineTarget.getName() : "player") + " is offline", Style.style(NamedTextColor.RED)))
                    .build());
            return true;
        }

        Player target = offlineTarget.getPlayer();

        target.sendMessage(Component.text()
                .append(Component.text("Teleporting you to do " + player.getName(), Style.style(NamedTextColor.GRAY, TextDecoration.ITALIC))
                        .toBuilder().build()));

        target.teleportAsync(player.getLocation()).thenRun(() -> {
            player.sendMessage(Component.text()
                    .append(Component.text(target.getName() + " has teleported to you", Style.style(NamedTextColor.GRAY, TextDecoration.ITALIC))
                            .toBuilder().build()));

            target.sendMessage(Component.text()
                    .append(Component.text("You have been teleported to " + player.getName() + "!", Style.style(NamedTextColor.YELLOW)))
                    .build());

            target.sendMessage(Component.text()
                    .append(Component.text("You will not be able to use this command again for 24 hours", Style.style(NamedTextColor.WHITE, TextDecoration.BOLD))
                            .toBuilder().build()));

            target.setMetadata(MetadataKeys.TELEPORT_REQUEST_TIME_SINCE, new FixedMetadataValue(Alexandria.getInstance(), TimeUtil.getUnixTime()));
        });

        return true;
    }
}