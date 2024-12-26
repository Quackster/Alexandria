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
        recipes.add(createSlabConversion(plugin, Material.SANDSTONE_SLAB, Material.SANDSTONE));
        recipes.add(createSlabConversion(plugin, Material.STONE_SLAB, Material.STONE));
        recipes.add(createSlabConversion(plugin, Material.COBBLESTONE_SLAB, Material.COBBLESTONE));
        recipes.add(createSlabConversion(plugin, Material.DEEPSLATE_BRICK_SLAB, Material.DEEPSLATE_BRICKS));
        recipes.add(createSlabConversion(plugin, Material.DEEPSLATE_TILE_SLAB, Material.DEEPSLATE_TILES));
        recipes.add(createSlabConversion(plugin, Material.COBBLED_DEEPSLATE_SLAB, Material.COBBLED_DEEPSLATE));
        recipes.add(createSlabConversion(plugin, Material.POLISHED_DEEPSLATE_SLAB, Material.POLISHED_DEEPSLATE));

        recipes.add(createBlockConversion(plugin, Material.DEEPSLATE, Material.COBBLED_DEEPSLATE));
        recipes.forEach(Bukkit::addRecipe);
        return recipes;
    }

    private static ShapelessRecipe createBlockConversion(JavaPlugin plugin, Material input, Material output) {
        ShapelessRecipe recipe = new ShapelessRecipe(new NamespacedKey(plugin, input.name().toLowerCase() + "_block_convert"), new ItemStack(output));
        recipe.addIngredient(1, input);
        return recipe;
    }

    private static ShapelessRecipe createSlabConversion(JavaPlugin plugin, Material input, Material output) {
        ShapelessRecipe recipe = new ShapelessRecipe(new NamespacedKey(plugin, input.name().toLowerCase() + "_conversion"), new ItemStack(output));
        recipe.addIngredient(2, input);
        return recipe;
    }
}
