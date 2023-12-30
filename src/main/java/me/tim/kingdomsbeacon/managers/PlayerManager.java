package me.tim.kingdomsbeacon.managers;

import com.earth2me.essentials.Essentials;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import me.tim.kingdomsbeacon.KingdomsBeacon;
import me.tim.kingdomsbeacon.adapters.PlayerDataTypeAdapter;
import me.neznamy.tab.api.TabAPI;
import me.neznamy.tab.api.TabPlayer;
import org.bukkit.Bukkit;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PlayerManager {

    private static PlayerManager instance;

    private HashMap<UUID, PlayerData> players;

    public PlayerManager() {
    }

    public static PlayerManager getInstance() {
        if (instance == null) {
            instance = new PlayerManager();
        }
        return instance;
    }

    public boolean hasPlayerData(UUID player) {
        return this.players.containsKey(player);
    }

    public void setDefaultValues(UUID player) {
        PlayerData playerData = new PlayerData();
        playerData.setVotes(0);
        playerData.setLeader(false);
        playerData.setKingdom(null);
        playerData.setTitle(null);
        playerData.setLoyal(null);
        playerData.setVoters(new ArrayList<>());
        this.players.put(player, playerData);
    }

    public void addVote(UUID voter, UUID candidate) {
        PlayerData playerData = this.players.getOrDefault(candidate, new PlayerData());
        playerData.addVote(voter);
    }

    public void removeVote(UUID voter, UUID candidate) {
        PlayerData playerData = this.players.getOrDefault(candidate, new PlayerData());
            playerData.removeVote(voter);
    }

    public int getVotes(UUID uuid) {
        PlayerData playerData = this.players.get(uuid);
        return playerData.getVotes();
    }

    public void setVotes(UUID uuid, int votes) {
        PlayerData playerData = this.players.get(uuid);
        playerData.setVotes(votes);
    }

    public UUID getLoyal(UUID uuid) {
        PlayerData playerData = this.players.get(uuid);
        return playerData.getLoyal();
    }

    public void setLoyal(UUID uuid, UUID target) {
        PlayerData playerData = this.players.get(uuid);
        playerData.setLoyal(target);
    }

    public String getKingdom(UUID uuid) {
        PlayerData playerData = this.players.get(uuid);
        return playerData.getKingdom();
    }

    public void setKingdom(UUID uuid, String kingdom) {
        PlayerData playerData = this.players.get(uuid);
        playerData.setKingdom(kingdom);
    }

    public String getTitle(UUID uuid) {
        PlayerData playerData = this.players.get(uuid);
        return playerData.getTitle();
    }

    public void setTitle(UUID uuid, String title) {
        PlayerData playerData = this.players.get(uuid);
        playerData.setTitle(title);
    }

    public boolean hasEnoughVotes(UUID candidate, int requiredVotes) {
        PlayerData playerData = this.players.get(candidate);
        return playerData != null && playerData.getVotes() >= requiredVotes;
    }

    public void setLeader(UUID player, boolean isLeader) {
        PlayerData playerData = this.players.getOrDefault(player, new PlayerData());
        playerData.setLeader(isLeader);
    }

    public boolean isLeader(UUID player) {
        PlayerData playerData = this.players.get(player);
        return playerData != null && playerData.isLeader();
    }

    public List<UUID> getVoters(UUID candidate) {
        PlayerData playerData = this.players.get(candidate);
        if (playerData != null) {
            return playerData.getVoters();
        } else {
            return new ArrayList<>();
        }
    }

    public void clearVoters(UUID candidate) {
        PlayerData playerData = this.players.getOrDefault(candidate, new PlayerData());
        playerData.getVoters().clear();
    }

    public void updateName(UUID player) {
        PlayerData playerData = this.players.get(player);
        TabPlayer tabPlayer = TabAPI.getInstance().getPlayer(player);
        Essentials essentials = (Essentials) Bukkit.getPluginManager().getPlugin("Essentials");
        String kingdom = playerData.getKingdom();
        if (playerData.getKingdom() == null) {
            TabAPI.getInstance().getNameTagManager().setPrefix(tabPlayer, null);
            TabAPI.getInstance().getTabListFormatManager().setPrefix(tabPlayer, null);
            essentials.getUser(Bukkit.getPlayer(player)).setNickname(Bukkit.getPlayer(player).getName());
            return;
        }
            TabAPI.getInstance().getNameTagManager().setPrefix(tabPlayer, "&7[" + kingdom + "&7] &f");
            TabAPI.getInstance().getTabListFormatManager().setPrefix(tabPlayer, "&7[" + kingdom + "&7] &f");
            essentials.getUser(player).setNickname(KingdomsBeacon.color("&7[" + kingdom + "&7] &f" + Bukkit.getPlayer(player).getName()));
        if (playerData.getTitle() == null) return;
        String title = playerData.getTitle();
        TabAPI.getInstance().getNameTagManager().setPrefix(tabPlayer, "&7[" + kingdom + "&7] &7[" + title + "&7] &f");
        TabAPI.getInstance().getTabListFormatManager().setPrefix(tabPlayer, "&7[" + kingdom + "&7] &f");
        essentials.getUser(player).setNickname(KingdomsBeacon.color("&7[" + kingdom + "&7] &7[" + title + "&7] &f" + Bukkit.getPlayer(player).getName()));
    }

    public void saveData() {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting()
                    .registerTypeAdapter(PlayerData.class, new PlayerDataTypeAdapter())
                    .create();
            String json = gson.toJson(this.players);
            FileWriter fileWriter = new FileWriter(KingdomsBeacon.getInstance().getPlayerFile());
            fileWriter.write(json);
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadData() {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting()
                    .registerTypeAdapter(PlayerData.class, new PlayerDataTypeAdapter())
                    .create();
            FileReader fileReader = new FileReader(KingdomsBeacon.getInstance().getPlayerFile());
            this.players = gson.fromJson(fileReader, new TypeToken<HashMap<UUID, PlayerData>>(){}.getType());
            fileReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
