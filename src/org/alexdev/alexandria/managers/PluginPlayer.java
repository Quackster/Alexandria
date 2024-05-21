package org.alexdev.alexandria.managers;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import org.alexdev.alexandria.util.attributes.PluginAttributable;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PluginPlayer extends PluginAttributable {
    private final Player player;
    private boolean isAfk;

    public PluginPlayer(Player player) {
        this.player = player;
        this.isAfk = false;
    }

    /**
     * Get the player for this battleplayer.
     *
     * @return the player
     */
    public Player getPlayer() {
        return player;
    }

    public boolean isAfk() {
        return isAfk;
    }

    public void setAfk(boolean afk) {
        isAfk = afk;
    }

    public void goAfk() {
        if (this.isAfk)
            return;

        this.isAfk = true;

        Bukkit.getServer().sendMessage(Component.text()
                .append(Component.text(this.player.getName(), Style.style(NamedTextColor.GRAY)))
                .append(Component.text(" is now AFK", Style.style(NamedTextColor.WHITE)))
                .build());

        this.player.displayName(Component.text()
                .append(Component.text("afk* ", Style.style(NamedTextColor.GRAY)))
                .append(Component.text(this.player.getName(), Style.style(NamedTextColor.WHITE)))
                .build());
    }

    public void unAfk() {
        if (!this.isAfk)
            return;

        this.isAfk = false;

        Bukkit.getServer().sendMessage(Component.text()
                .append(Component.text(this.player.getName(), Style.style(NamedTextColor.GRAY)))
                .append(Component.text(" is no longer AFK", Style.style(NamedTextColor.WHITE)))
                .build());

        this.player.displayName(Component.text()
                .append(Component.text(this.player.getName(), Style.style(NamedTextColor.WHITE)))
                .build());
    }
}
