package me.tim.kingdomsbeacon.utils;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author Elapsed
 * @version 1.0
 * @apiNote This has all the Inventory Utilities
 */

public class InventoryUtility {

    /**
     * Encoding a ItemStack to a string
     *
     * @param itemStack The ItemStack we are encoding
     * @return This returns a base 64 Encoded Itemstack
     */
    public static String toBase64(ItemStack itemStack) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

            dataOutput.writeInt(0);
            dataOutput.writeObject(itemStack);

            // Serialize that array
            dataOutput.close();

            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception e) {
            throw new IllegalStateException("Unable to parse itemstack.", e);
        }
    }

    /**
     * Base64 String converted to a ItemStack
     * @param data The Base64 Encoded
     * @return This returns an ItemStack
     * @throws IOException
     */
    public static ItemStack toItemStack(String data) {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);

            dataInput.close();
            return (ItemStack) dataInput.readObject();
        } catch (Exception e) {
            throw new IllegalStateException("Unable to decode class type.");
        }
    }

    /**
     * Converts the inventory contents to sting
     *
     * @param items The Array of items
     * @return This returns the base 64 code
     * @throws IllegalStateException
     */
    public static String toBase64(ItemStack[] items) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

            // Write the size of the inventory
            dataOutput.writeInt(items.length);

            // Save every element in the list
            for (int i = 0; i < items.length; i++) {
                dataOutput.writeObject(items[i]);
            }

            // Serialize that array
            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception e) {
            throw new IllegalStateException("Unable to save item stacks.", e);
        }
    }

    /**
     * Base64 String converted the Array of ItemStacks
     * @param data The Base64 Encoded
     * @return This returns the Array of ItemStacks
     * @throws IOException
     */
    public static ItemStack[] toItemStacks(String data) {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            ItemStack[] items = new ItemStack[dataInput.readInt()];

            // Read the serialized inventory
            for (int i = 0; i < items.length; i++) {
                items[i] = (ItemStack) dataInput.readObject();
            }

            dataInput.close();
            return items;
        } catch (Exception e) {
            throw new IllegalStateException("Unable to decode class type.", e);
        }
    }

    /**
     * Checks if there is a free space in the inventory
     * @param inventory The inventory we are going to check
     * @return
     */
    public boolean freeSpace(Inventory inventory) {

        for (ItemStack itemStack : inventory.getContents()) {
            if (itemStack == null) return true;
        }

        return false;
    }

    /**
     * Checking if we can fit a ItemStack in the inventory
     * @param inventory
     * @param itemStack
     * @return
     */
    public boolean canFitItemStack(Inventory inventory, ItemStack itemStack) {

        if (freeSpace(inventory)) return true;

        for (ItemStack inventoryItem : inventory.getContents()) {
            if (inventoryItem.isSimilar(itemStack) && inventoryItem.getAmount() < inventoryItem.getMaxStackSize()) return true;
        }

        return false;
    }

}