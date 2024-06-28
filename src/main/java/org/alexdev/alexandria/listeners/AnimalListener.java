package org.alexdev.alexandria.listeners;

import org.alexdev.alexandria.Alexandria;
import org.alexdev.alexandria.util.BlockUtil;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ChestedHorse;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Steerable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

import javax.print.DocFlavor;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AnimalListener implements Listener {
    private final Random random;
    private final Alexandria plugin;

    public AnimalListener(Alexandria plugin) {
        this.random = new Random();
        this.plugin = plugin;
    }


    @EventHandler
    public void onEntityClick(PlayerInteractEntityEvent event) {
        var itemInHand = event.getPlayer().getInventory().getItemInMainHand();

        if (!(itemInHand.getType() == Material.WOODEN_AXE ||
                itemInHand.getType() == Material.STONE_AXE ||
                itemInHand.getType() == Material.IRON_AXE ||
                itemInHand.getType() == Material.GOLDEN_AXE ||
                itemInHand.getType() == Material.DIAMOND_AXE ||
                itemInHand.getType() == Material.NETHERITE_AXE)) {
            return;
        }

        switch (event.getRightClicked().getType()) {
            case MULE:
            case DONKEY: {
                ChestedHorse equine = (ChestedHorse) event.getRightClicked();

                if (equine.getInventory().getContents().length > 0)
                    return;

                if (equine.isCarryingChest()) {
                    equine.setCarryingChest(false);
                    equine.getWorld().dropItemNaturally(equine.getLocation(), new ItemStack(Material.CHEST, 1));
                }

                break;
            }
            case STRIDER:
            case PIG: {
                Steerable steerable = (Steerable) event.getRightClicked();

                if (steerable.hasSaddle()) {
                    steerable.setSaddle(false); //.setCarryingChest(false);
                    steerable.setLeashHolder(null);
                    steerable.getWorld().dropItemNaturally(steerable.getLocation(), new ItemStack(Material.SADDLE, 1));
                }
                break;
            }
        }
    }
}
