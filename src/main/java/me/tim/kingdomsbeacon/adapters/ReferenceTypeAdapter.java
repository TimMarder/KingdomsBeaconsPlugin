package me.tim.kingdomsbeacon.adapters;

import com.google.gson.*;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.lang.reflect.Type;

public class ReferenceTypeAdapter implements JsonSerializer<Reference<?>>, JsonDeserializer<Reference<?>> {

    @Override
    public JsonElement serialize(Reference<?> reference, Type typeOfT, JsonSerializationContext context) {
        return new JsonPrimitive(reference.get().toString());
    }

    @Override
    public Reference<?> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return new WeakReference<>(context.deserialize(json, typeOfT));
    }
}
