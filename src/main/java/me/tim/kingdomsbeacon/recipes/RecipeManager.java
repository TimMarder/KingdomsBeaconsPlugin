package me.tim.kingdomsbeacon.recipes;

import me.tim.kingdomsbeacon.KingdomsBeacon;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class RecipeManager {

    public static void init() {
    createBeaconRecipe();
    }

    private static void createBeaconRecipe() {
        NamespacedKey key = new NamespacedKey(KingdomsBeacon.getInstance(), "beacon");
        ShapedRecipe recipe = new ShapedRecipe(key, new ItemStack(Material.BEACON));
        recipe.shape("GAG", "GAG", "DPD");
        recipe.setIngredient('G', Material.GOLD_BLOCK);
        recipe.setIngredient('D', Material.DIAMOND_BLOCK);
        recipe.setIngredient('A', Material.GOLDEN_APPLE);
        recipe.setIngredient('P', Material.ENDER_PEARL);
        Bukkit.getServer().addRecipe(recipe);
        Bukkit.getServer().removeRecipe(Material.BEACON.getKey());
    }
}
