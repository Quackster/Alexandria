package org.alexdev.alexandria.listeners;

import org.alexdev.alexandria.util.BlockUtil;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EntityListener implements Listener {
    private final Random random;

    public EntityListener() {
        this.random = new Random();
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        if (event.getEntityType() != EntityType.PLAYER) {
            return;
        }

        Player player = (Player) event.getEntity();

        if (event.getCause() == EntityDamageEvent.DamageCause.STARVATION) {
            if (player.getHealth() > 1) {
                return;
            }

            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBatDeathEvent(EntityDeathEvent event) {
        if (event.getEntityType() == EntityType.BAT) {
            if (event.getEntity().getKiller() != null) {
                Player killer = event.getEntity().getKiller();
                ItemStack handItem = killer.getInventory().getItemInMainHand();

                int maxMembraneChance = 1;

                if (handItem.containsEnchantment(Enchantment.LOOT_BONUS_MOBS)) {
                    int lootingLevel = handItem.getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS);

                    switch (lootingLevel) {
                        case 1:
                            maxMembraneChance = 2;
                            break;
                        case 2:
                            maxMembraneChance = 3;
                            break;
                        case 3:
                            maxMembraneChance = 4;
                            break;
                    }
                }

                int membraneDropCount = random.nextInt(maxMembraneChance) + 1; // Randomly decide the drop count
                event.getDrops().add(new ItemStack(Material.PHANTOM_MEMBRANE, membraneDropCount));
            }
        }
    }

    @EventHandler
    public void onEnderDragonDeathEvent(EntityDeathEvent event) {
        if (event.getEntityType() == EntityType.ENDER_DRAGON) {
            if (event.getEntity().getKiller() != null) {
                event.getDrops().add(new ItemStack(Material.ELYTRA, 1));
            }
        }
    }

    @EventHandler
    public void onEntityExplodeEvent(EntityExplodeEvent event) {
        List<Block> blocks = event.blockList();

        for (Block block : new ArrayList<>(blocks)) {
            if (!BlockUtil.isBreakableBlockOrSpawner(block)) {
                blocks.remove(block);
            }
        }
    }

}
