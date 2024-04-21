package org.alexdev.alexandria.managers;

import org.alexdev.alexandria.util.attributes.PluginAttributable;
import org.bukkit.entity.Player;

public class PluginPlayer extends PluginAttributable {
    private final Player player;


    public PluginPlayer(Player player) {
        this.player = player;
    }

    /**
     * Get the player for this battleplayer.
     *
     * @return the player
     */
    public Player getPlayer() {
        return player;
    }
}
