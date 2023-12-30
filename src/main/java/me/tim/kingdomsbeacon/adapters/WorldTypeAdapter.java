package me.tim.kingdomsbeacon.adapters;

import com.google.gson.*;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.lang.reflect.Type;
import java.util.UUID;

public class WorldTypeAdapter implements JsonSerializer<World>, JsonDeserializer<World> {

    @Override
    public JsonElement serialize(World world, Type typeOfT, JsonSerializationContext context) {
        return new JsonPrimitive(world.getUID().toString());
    }

    @Override
    public World deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String uid = json.getAsString();
        return Bukkit.getWorld(UUID.fromString(uid));
    }
}
