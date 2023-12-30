package me.tim.kingdomsbeacon.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import me.tim.kingdomsbeacon.managers.PlayerData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerDataTypeAdapter extends TypeAdapter<PlayerData> {

    @Override
    public void write(JsonWriter out, PlayerData value) throws IOException {
        out.beginObject();
        out.name("votes").value(value.getVotes());
        out.name("isLeader").value(value.isLeader());
        if (value.getLoyal() != null) {
            out.name("loyal").value(value.getLoyal().toString());
        }
        out.name("kingdom").value(value.getKingdom());
        out.name("title").value(value.getTitle());
        out.name("voters").beginArray();
        for (UUID voter : value.getVoters()) {
            out.value(voter.toString());
        }
        out.endArray();
        out.endObject();
    }

    @Override
    public PlayerData read(JsonReader in) throws IOException {
        PlayerData playerData = new PlayerData();
        in.beginObject();
        while (in.hasNext()) {
            String name = in.nextName();
            switch (name) {
                case "votes":
                    playerData.setVotes(in.nextInt());
                    break;
                case "isLeader":
                    playerData.setLeader(in.nextBoolean());
                    break;
                case "loyal":
                    playerData.setLoyal(UUID.fromString(in.nextString()));
                    break;
                case "kingdom":
                    playerData.setKingdom(in.nextString());
                    break;
                case "title":
                    playerData.setTitle(in.nextString());
                    break;
                case "voters":
                    List<UUID> voters = new ArrayList<>();
                    in.beginArray();
                    while (in.hasNext()) {
                        voters.add(UUID.fromString(in.nextString()));
                    }
                    in.endArray();
                    playerData.setVoters(voters);
                    break;
            }
        }
        in.endObject();
        return playerData;
    }
}
