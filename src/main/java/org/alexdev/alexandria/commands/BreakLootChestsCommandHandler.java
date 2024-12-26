package org.alexdev.alexandria.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import org.alexdev.alexandria.Alexandria;
import org.alexdev.alexandria.util.MetadataKeys;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.jetbrains.annotations.NotNull;

public class BreakLootChestsCommandHandler implements CommandExecutor {
    private final Alexandria plugin;

    public BreakLootChestsCommandHandler(Alexandria plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String commandName, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        var player = (Player) sender;

        if (!player.hasMetadata(MetadataKeys.ALLOW_BREAK_LOOT_CHESTS)) {
            player.setMetadata(MetadataKeys.ALLOW_BREAK_LOOT_CHESTS, new FixedMetadataValue(this.plugin, true));

            player.sendMessage(Component.text()
                    .append(Component.text("Breakable loot chests is now possible", Style.style(NamedTextColor.GRAY, TextDecoration.ITALIC)))
                    .build());

        } else {
            player.removeMetadata(MetadataKeys.ALLOW_BREAK_LOOT_CHESTS, this.plugin);

            player.sendMessage(Component.text()
                    .append(Component.text("Breakable loot chests is now disabled", Style.style(NamedTextColor.GRAY, TextDecoration.ITALIC)))
                    .build());
        }

        return true;
    }

}
