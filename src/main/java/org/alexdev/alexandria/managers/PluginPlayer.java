package org.alexdev.alexandria.managers;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import org.alexdev.alexandria.Alexandria;
import org.alexdev.alexandria.tasks.PlayerActivityTask;
import org.alexdev.alexandria.util.attributes.PluginAttributable;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class PluginPlayer extends PluginAttributable {
    private final Player player;
    private boolean isAfk;
    private long lastMovement;
    private Location afkLocation;

    public PluginPlayer(Player player) {
        this.player = player;
        this.isAfk = false;
        this.updateLastMovement();
    }

    public void updateLastMovement() {
        this.lastMovement = System.currentTimeMillis();

        if (this.isAfk) {
            this.unAfk();
        }
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

    public void goAfk() {
        if (!Alexandria.ENABLE_AFK_CHECK) {
            return;
        }

        if (this.isAfk)
            return;

        this.afkLocation = this.player.getLocation().clone();
        this.isAfk = true;

        Bukkit.getServer().sendMessage(Component.text()
                .append(Component.text(this.player.getName(), Style.style(NamedTextColor.GRAY)))
                .append(Component.text(" is now AFK", Style.style(NamedTextColor.WHITE)))
                .build());

        this.player.playerListName(Component.text()
                .append(Component.text("afk* ", Style.style(NamedTextColor.GRAY)))
                .append(Component.text(this.player.getName(), Style.style(NamedTextColor.WHITE)))
                .build());
    }

    public void unAfk() {
        if (!Alexandria.ENABLE_AFK_CHECK) {
            return;
        }

        if (!this.isAfk)
            return;

        this.isAfk = false;

        Bukkit.getServer().sendMessage(Component.text()
                .append(Component.text(this.player.getName(), Style.style(NamedTextColor.GRAY)))
                .append(Component.text(" is no longer AFK", Style.style(NamedTextColor.WHITE)))
                .build());

        this.player.playerListName(Component.text()
                .append(Component.text(this.player.getName(), Style.style(NamedTextColor.WHITE)))
                .build());
    }

    public long getLastMovement() {
        return lastMovement;
    }

    public Location getAfkLocation() {
        return afkLocation;
    }
}
