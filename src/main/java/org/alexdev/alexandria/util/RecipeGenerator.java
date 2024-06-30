package org.alexdev.alexandria.util;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class RecipeGenerator {
    public  static List<ShapelessRecipe> createSlabConversionList(JavaPlugin plugin) {
        var recipes = new ArrayList<ShapelessRecipe>();

        recipes.add(createSlabConversion(plugin, Material.OAK_SLAB, Material.OAK_PLANKS));
        recipes.add(createSlabConversion(plugin, Material.SPRUCE_SLAB, Material.SPRUCE_PLANKS));
        recipes.add(createSlabConversion(plugin, Material.BIRCH_SLAB, Material.BIRCH_PLANKS));
        recipes.add(createSlabConversion(plugin, Material.JUNGLE_SLAB, Material.JUNGLE_PLANKS));
        recipes.add(createSlabConversion(plugin, Material.ACACIA_SLAB, Material.ACACIA_PLANKS));
        recipes.add(createSlabConversion(plugin, Material.DARK_OAK_SLAB, Material.DARK_OAK_PLANKS));
        recipes.add(createSlabConversion(plugin, Material.BAMBOO_SLAB, Material.BAMBOO_PLANKS));
        recipes.add(createSlabConversion(plugin, Material.CHERRY_SLAB, Material.CHERRY_PLANKS));
        recipes.add(createSlabConversion(plugin, Material.MANGROVE_SLAB, Material.MANGROVE_PLANKS));
        recipes.forEach(Bukkit::addRecipe);
        return recipes;
    }
    private static ShapelessRecipe createSlabConversion(JavaPlugin plugin, Material input, Material output) {
        ShapelessRecipe recipe = new ShapelessRecipe(new NamespacedKey(plugin, input.name().toLowerCase() + "_conversion"), new ItemStack(output));
        recipe.addIngredient(2, input);
        return recipe;
    }
}
