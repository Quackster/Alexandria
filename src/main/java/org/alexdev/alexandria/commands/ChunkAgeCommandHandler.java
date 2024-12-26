package org.alexdev.alexandria.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import org.alexdev.alexandria.Alexandria;
import org.alexdev.alexandria.util.TimeManager;
import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ChunkAgeCommandHandler implements CommandExecutor {
    private final Alexandria plugin;

    public ChunkAgeCommandHandler(Alexandria plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String commandName, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        var player = (Player) sender;
        Chunk chunk = player.getChunk();

        player.sendMessage(Component.text()
                .append(Component.text("The chunk at ", Style.style(NamedTextColor.RED)))
                .append(Component.text(chunk.getX(), Style.style(NamedTextColor.WHITE)))
                .append(Component.text(", ", Style.style(NamedTextColor.RED)))
                .append(Component.text(chunk.getZ(), Style.style(NamedTextColor.WHITE)))
                .append(Component.text(" is ", Style.style(NamedTextColor.RED)))
                .append(Component.text(chunk.getInhabitedTime(), Style.style(NamedTextColor.WHITE)))
                .append(Component.text(" ticks old, or ", Style.style(NamedTextColor.RED)))
                .append(Component.text(TimeManager.humanReadableTicks(chunk.getInhabitedTime()).getHours(), Style.style(NamedTextColor.WHITE)))
                .append(Component.text(" hours, ", Style.style(NamedTextColor.RED)))
                .append(Component.text(TimeManager.humanReadableTicks(chunk.getInhabitedTime()).getMinutes(), Style.style(NamedTextColor.WHITE)))
                .append(Component.text(" minutes, ", Style.style(NamedTextColor.RED)))
                .append(Component.text(TimeManager.humanReadableTicks(chunk.getInhabitedTime()).getHours(), Style.style(NamedTextColor.WHITE)))
                .append(Component.text(" seconds old", Style.style(NamedTextColor.RED)))
                .build());

        return true;
    }

}
