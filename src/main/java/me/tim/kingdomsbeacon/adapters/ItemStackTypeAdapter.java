package me.tim.kingdomsbeacon.adapters;

import com.google.gson.*;
import me.tim.kingdomsbeacon.utils.InventoryUtility;
import me.tim.kingdomsbeacon.utils.TL;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Type;
import java.util.logging.Level;

public class ItemStackTypeAdapter implements JsonDeserializer<ItemStack>, JsonSerializer<ItemStack> {

    @Override
    public ItemStack deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            String obj = json.getAsString();

            return InventoryUtility.toItemStack(obj);
        } catch (Exception ex) {
            TL.log(Level.WARNING, "Error encountered while deserializing a ItemStack.");
            return null;
        }
    }

    @Override
    public JsonElement serialize(ItemStack src, Type typeOfSrc, JsonSerializationContext context) {

        JsonObject obj = new JsonObject();

        try {

            obj = new Gson().fromJson(InventoryUtility.toBase64(src), JsonObject.class);

            return obj;
        } catch (Exception ex) {
            TL.log(Level.WARNING, "Error encountered while serializing a ItemStack.");
            return obj;
        }
    }
}