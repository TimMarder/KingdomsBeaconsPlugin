package me.tim.kingdomsbeacon.managers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.tim.kingdomsbeacon.KingdomsBeacon;
import me.tim.kingdomsbeacon.adapters.*;
import me.tim.kingdomsbeacon.utils.InventoryUtility;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class BeaconInventoryManager {

    private static BeaconInventoryManager instance;

    private final static HashMap<Location, Inventory> beaconInventories = new HashMap<>();

    public BeaconInventoryManager() {
    }

    public static BeaconInventoryManager getInstance() {
        if (instance == null) {
            instance = new BeaconInventoryManager();
        }
        return instance;
    }

    public void createBeaconInventory(Location location) {
        Inventory inventory = Bukkit.createInventory(null, InventoryType.CHEST, "Fuel");
        beaconInventories.put(location, inventory);
    }

    public void removeBeaconInventory(Location location) {
        beaconInventories.remove(location);
    }

    public Inventory getBreaconInventory(Location location) {
        return beaconInventories.get(location);
    }

    public HashMap<Location, Inventory> getBeaconInventories() {
        return beaconInventories;
    }

    public void openBeaconInventory(Player player, Location location) {
        Inventory inventory = beaconInventories.get(location);
        player.openInventory(inventory);
    }

    public void saveBeaconInventories() {

        try {
            Gson gson = new GsonBuilder().setPrettyPrinting()
                    .registerTypeAdapter(Location.class, new LocationTypeAdapter())
                    .create();

            Map<String, String> base64InventoryContents = new HashMap<>();
            for (Location location : beaconInventories.keySet()) {
                ItemStack[] items = beaconInventories.get(location).getContents();
                String base64InventoryContent = InventoryUtility.toBase64(items);

                String locationJson = gson.toJson(location);

                base64InventoryContents.put(locationJson, base64InventoryContent);
            }

            String json = gson.toJson(base64InventoryContents);

            FileWriter fileWriter = new FileWriter(KingdomsBeacon.getInstance().getInventoryFile());
            fileWriter.write(json);
            fileWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadBeaconInventories() {

        try {
            File file = KingdomsBeacon.getInstance().getInventoryFile();
            if (!file.exists()) {
                return;
            }

            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            bufferedReader.close();

            String json = stringBuilder.toString();

            Gson gson = new GsonBuilder().setPrettyPrinting()
                    .registerTypeAdapter(Location.class, new LocationTypeAdapter())
                    .create();

            Type type = new com.google.common.reflect.TypeToken<Map<String, String>>() {
            }.getType();
            Map<String, String> base64InventoryContents = gson.fromJson(json, type);

            for (String locationJson : base64InventoryContents.keySet()) {
                Location location = gson.fromJson(locationJson, Location.class);
                String base64InventoryContent = base64InventoryContents.get(locationJson);
                ItemStack[] items = InventoryUtility.toItemStacks(base64InventoryContent);

                Inventory inventory = Bukkit.createInventory(null, InventoryType.CHEST, "Fuel");
                inventory.setContents(items);

                beaconInventories.put(location, inventory);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}