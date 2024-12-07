package org.alexdev.alexandria.listeners;

import org.alexdev.alexandria.util.MetadataKeys;
import org.alexdev.alexandria.util.TimeUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.alexdev.alexandria.Alexandria;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.Random;
import java.util.UUID;

public class VillagerListener implements Listener {
    private final Random random;
    private final Alexandria plugin;

    public VillagerListener(Alexandria plugin) {
        this.random = new Random();
        this.plugin = plugin;
    }

    /**
     * Disallows barrier removal since villagers only have 8 slots
     * @param event
     */
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals("Villager's Inventory")) {
            if (event.getSlot() == 8) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (event.getPlayer().hasMetadata(MetadataKeys.VILLAGER_INVENTORY_EDIT)) {
            var uniqueId = (UUID) event.getPlayer().getMetadata(MetadataKeys.VILLAGER_INVENTORY_EDIT).get(0).value();
            var target = event.getPlayer().getWorld().getEntity(uniqueId);

            this.plugin.getLogger().info("UNIQUE ID " + uniqueId);


            if (target instanceof Villager villager) {
                // Villager target = interactions.get(event.getPlayer().getName());

                // if we're just closed our inventory
                if (event.getView().getTitle().equals("Villager's Inventory")) {


                    this.plugin.getLogger().info("UNIQUE ID  2 " + uniqueId);


                    // copy the edited inventory back into the villagers inventory
                    for (int i = 0; i < 8; i++) { // careful to only select the first 8 slots
                        villager.getInventory().setItem(i, event.getInventory().getItem(i));
                    }

                    if (event.getPlayer().hasMetadata(MetadataKeys.VILLAGER_INVENTORY_EDIT)) {
                        event.getPlayer().removeMetadata(MetadataKeys.VILLAGER_INVENTORY_EDIT, this.plugin);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onRightClick(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();

        if (player.isSneaking()) {
            if (event.getRightClicked() instanceof Villager villager) {
                event.setCancelled(true);

                if (player.hasMetadata(MetadataKeys.VILLAGER_INVENTORY_EDIT)) {
                    player.removeMetadata(MetadataKeys.VILLAGER_INVENTORY_EDIT, this.plugin);
                }

                player.setMetadata(MetadataKeys.VILLAGER_INVENTORY_EDIT, new FixedMetadataValue(this.plugin, villager.getUniqueId()));

                Inventory inventory = Bukkit.createInventory(null, 9, "Villager's Inventory");

                for (int i = 0; i < 8; i++) {
                    inventory.setItem(i, villager.getInventory().getItem(i));
                }

                inventory.setItem(8, new ItemStack(Material.BARRIER));

                player.openInventory(inventory);
            }
        }
    }
}
