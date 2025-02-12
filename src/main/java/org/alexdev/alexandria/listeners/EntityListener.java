package org.alexdev.alexandria.listeners;

import org.alexdev.alexandria.Alexandria;
import org.alexdev.alexandria.util.BlockUtil;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.vehicle.VehicleDestroyEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EntityListener implements Listener {
    private final Random random;
    private final Alexandria plugin;

    public EntityListener(Alexandria plugin) {
        this.random = new Random();
        this.plugin = plugin;
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

                if (handItem.containsEnchantment(Enchantment.LOOTING)) {
                    int lootingLevel = handItem.getEnchantmentLevel(Enchantment.LOOTING);

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

                int membraneDropCount = this.random.nextInt(maxMembraneChance); // Randomly decide the drop count
                event.getDrops().add(new ItemStack(Material.PHANTOM_MEMBRANE, membraneDropCount));
            }
        }
    }

    @EventHandler
    public void onSkeletonSpawnEvent(EntitySpawnEvent event) {
        if (event.getEntityType() == EntityType.SKELETON) {
            if (event.getEntity().getEntitySpawnReason() == CreatureSpawnEvent.SpawnReason.SPAWNER ||
                event.getEntity().getEntitySpawnReason() == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG) {
                var monster = (Skeleton) event.getEntity();
                var chance = 0.085F;

                monster.getEquipment().setHelmetDropChance(0.0F);
                monster.getEquipment().setChestplateDropChance(0.0F);
                monster.getEquipment().setLeggingsDropChance(0.0F);
                monster.getEquipment().setBootsDropChance(0.0F);
                monster.getEquipment().setItemInMainHandDropChance(0.0F);

                monster.getEquipment().setHelmetDropChance(chance);
                monster.getEquipment().setChestplateDropChance(chance);
                monster.getEquipment().setLeggingsDropChance(chance);
                monster.getEquipment().setBootsDropChance(chance);
                monster.getEquipment().setItemInMainHandDropChance(chance);
            }
        }
    }

    @EventHandler
    public void onHuskDeathEvent(EntityDeathEvent event) {
        if (event.getEntityType() == EntityType.HUSK) {
            int maxSandDropChance = 2;

            if (event.getEntity().getKiller() != null) {
                Player killer = event.getEntity().getKiller();
                ItemStack handItem = killer.getInventory().getItemInMainHand();

                if (handItem.containsEnchantment(Enchantment.LOOTING)) {
                    int lootingLevel = handItem.getEnchantmentLevel(Enchantment.LOOTING);

                    switch (lootingLevel) {
                        case 1:
                            maxSandDropChance += 1;
                            break;
                        case 2:
                            maxSandDropChance += 2;
                            break;
                        case 3:
                            maxSandDropChance += 3;
                            break;
                    }
                }
            }

            int sandDropCount = this.random.nextInt(maxSandDropChance); // Randomly decide the drop count
            event.getDrops().add(new ItemStack(Material.SAND, sandDropCount));
        }
    }

    @EventHandler
    public void onEnderDragonDeathEvent(EntityDeathEvent event) {
        var world = event.getEntity().getWorld();

        if (event.getEntityType() == EntityType.ENDER_DRAGON) {
            if (event.getEntity().getKiller() != null) {
                for (var player : world.getNearbyPlayers(world.getSpawnLocation(), 120)) {
                    world.dropItem(player.getLocation(), new ItemStack(Material.ELYTRA, 1));
                    world.dropItem(player.getLocation(), new ItemStack(Material.SHULKER_SHELL, 6));
                    //event.getDrops().add(new ItemStack(Material.ELYTRA, 1));
                    //event.getDrops().add(new ItemStack(Material.SHULKER_SHELL, this.random.nextInt(5, 10)));
                }
            }
        }
    }

    @EventHandler
    public void onDrownedDropTurtleEggsDeathEvent(EntityDeathEvent event) {
        if (Math.random() > 0.05) { // 5% chance of turtle eggs
            return;
        }

        if (event.getEntityType() == EntityType.DROWNED) {
            if (event.getEntity().getKiller() == null) {
                return;
            }

            if (event.getEntity().getLocation().getBlock().getType() != Material.WATER) {
                return;
            }

            ItemStack handItem = event.getEntity().getKiller().getInventory().getItemInMainHand();
            int maxEggChance = 1;

            if (handItem.containsEnchantment(Enchantment.LOOTING)) {
                int lootingLevel = handItem.getEnchantmentLevel(Enchantment.LOOTING);

                switch (lootingLevel) {
                    case 1:
                        maxEggChance = 2;
                        break;
                    case 2:
                        maxEggChance = 3;
                        break;
                    case 3:
                        maxEggChance = 4;
                        break;
                }
            }

            int eggDropCount = random.nextInt(maxEggChance) + 1; // Randomly decide the drop count
            event.getDrops().add(new ItemStack(Material.TURTLE_EGG, eggDropCount));
        }
    }

    @EventHandler
    public void onEntityExplodeEvent(EntityExplodeEvent event) {

        List<Block> blocks = event.blockList();

        for (Block block : new ArrayList<>(blocks)) {
            if (!BlockUtil.isBreakableBlockOrSpawner(block)) {
                blocks.remove(block);
            }

            //if (!BlockUtil.isBreakableBlockOrChest(null, block)) {
            //    blocks.remove(block);
            //}
        }
    }

    @EventHandler
    public void onEntityChangeBlockEvent(EntityChangeBlockEvent event) {
        if (event.getEntity().getType() != EntityType.ENDERMAN) {
            return;
        }

        // Fuck off Endermen
        event.setCancelled(true);
    }

    @EventHandler
    public void onGhastEntityExplode(EntityExplodeEvent event) {
        if (event.getEntityType() == EntityType.FIREBALL) {
            event.blockList().clear();
        }
    }

    /*
    @EventHandler
    public void onGhastExplosionPrime(ExplosionPrimeEvent event) {
        if (event.getEntityType() == EntityType.FIREBALL) {
            event.().clear();
        }
    }*/
}
