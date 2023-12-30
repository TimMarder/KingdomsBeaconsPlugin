package me.tim.kingdomsbeacon.adapters;

import com.google.gson.*;
import me.tim.kingdomsbeacon.KingdomsBeacon;
import org.bukkit.Location;

import java.lang.reflect.Type;

public class LocationTypeAdapter implements JsonSerializer<Location>, JsonDeserializer<Location> {
    @Override
    public JsonElement serialize(Location location, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("world", location.getWorld().getName());
        jsonObject.addProperty("x", location.getX());
        jsonObject.addProperty("y", location.getY());
        jsonObject.addProperty("z", location.getZ());
        jsonObject.addProperty("pitch", location.getPitch());
        jsonObject.addProperty("yaw", location.getYaw());
        return jsonObject;
    }

    @Override
    public Location deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        if (jsonElement.isJsonObject()) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            String worldName = jsonObject.get("world").getAsString();
            double x = jsonObject.get("x").getAsDouble();
            double y = jsonObject.get("y").getAsDouble();
            double z = jsonObject.get("z").getAsDouble();
            float pitch = jsonObject.get("pitch").getAsFloat();
            float yaw = jsonObject.get("yaw").getAsFloat();
            return new Location(KingdomsBeacon.getInstance().getServer().getWorld(worldName), x, y, z, yaw, pitch);
        } else {
            throw new JsonParseException("Location should be a JSON object.");
        }
    }
}