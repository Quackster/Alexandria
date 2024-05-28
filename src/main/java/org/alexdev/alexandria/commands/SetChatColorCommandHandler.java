package org.alexdev.alexandria.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.alexdev.alexandria.Alexandria;
import org.alexdev.alexandria.managers.PlayerManager;
import org.alexdev.alexandria.managers.PluginPlayer;
import org.alexdev.alexandria.util.MetadataKeys;
import org.alexdev.alexandria.util.enums.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SetChatColorCommandHandler implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String commandName, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }


        Player player = (Player) sender;
        Color chatColor = null;

        System.out.println(String.join(",", args));

        if (args.length < 1 && player.hasMetadata(MetadataKeys.CHAT_COLOR)) {
            player.sendMessage(Component.text()
                    .append(Component.text("Chat color has been reset", Style.style(NamedTextColor.GRAY, TextDecoration.ITALIC)))
                    .build());

            player.removeMetadata(MetadataKeys.CHAT_COLOR, Alexandria.getInstance());
            return true;
        }

        if (args.length < 1 || Arrays.stream(Color.values()).noneMatch(x -> x.getName().equalsIgnoreCase(args[0]))) {
            player.sendMessage(Component.text()
                    .append(Component.text("No chat colour found", Style.style(NamedTextColor.RED)))
                    .build());
            return true;
        }

        chatColor = Color.valueOf(args[0].toUpperCase());

        player.sendMessage(Component.text()
                .append(Component.text("Chat colour set to: ", Style.style(NamedTextColor.WHITE)))
                .append(Component.text(chatColor.getName(), Style.style(TextColor.color(chatColor.getRed(), chatColor.getGreen(), chatColor.getBlue()))))
                .build());

        player.setMetadata(MetadataKeys.CHAT_COLOR, new FixedMetadataValue(Alexandria.getInstance(), chatColor.getName()));
        player.displayName(Component.text(player.getName(), Style.style(TextColor.color(chatColor.getRed(), chatColor.getGreen(), chatColor.getBlue()))));

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return Arrays.stream(Color.values()).map(Color::getName).toList();
    }
}
