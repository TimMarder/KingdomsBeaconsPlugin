package me.tim.kingdomsbeacon.managers;

import com.google.gson.*;
import me.tim.kingdomsbeacon.KingdomsBeacon;
import me.tim.kingdomsbeacon.adapters.LocationTypeAdapter;
import me.tim.kingdomsbeacon.adapters.ReferenceTypeAdapter;
import me.tim.kingdomsbeacon.adapters.WorldTypeAdapter;
import org.bukkit.Location;
import org.bukkit.World;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

import java.io.FileWriter;
import java.util.Map;
import java.lang.ref.Reference;

public class BeaconManager {

    private static BeaconManager instance;

    private static final HashMap<Location, Beacon> beacons = new HashMap<>();

    public BeaconManager() {
    }

    public static BeaconManager getInstance() {
        if (instance == null) {
            instance = new BeaconManager();
        }
        return instance;
    }

    public void addBeacon(Location location, UUID uuid, long lastClicked, boolean touched) {
        Beacon beacon = new Beacon(uuid, location, false, lastClicked, true);
        beacons.put(location, beacon);
    }

    public void removeBeacon(Location location) {

        for (Iterator<Map.Entry<Location, Beacon>> iterator = beacons.entrySet().iterator(); iterator.hasNext();) {
            Map.Entry<Location, Beacon> entry = iterator.next();
            Location beaconLocation = entry.getKey();

            if (beaconLocation.equals(location)) {
                iterator.remove();
            }
        }

    }

    public Beacon getBeacon(Location location) {
        return beacons.get(location);
    }

    public HashMap<Location, Beacon> getBeacons() {
        return beacons;
    }


    public void saveBeacons() {

        try {
            Gson gson = new GsonBuilder().setPrettyPrinting()
                    .registerTypeAdapter(Reference.class, new ReferenceTypeAdapter())
                    .registerTypeAdapter(Location.class, new LocationTypeAdapter())
                    .registerTypeAdapter(World.class, new WorldTypeAdapter())
                    .create();
            String json = gson.toJson(beacons.values());
            FileWriter fileWriter = new FileWriter(KingdomsBeacon.getInstance().getBeaconFile());
            fileWriter.write(json);
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadBeacons() {

        try {
            File file = KingdomsBeacon.getInstance().getBeaconFile();
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
                    .registerTypeAdapter(Reference.class, new ReferenceTypeAdapter())
                    .registerTypeAdapter(Location.class, new LocationTypeAdapter())
                    .registerTypeAdapter(World.class, new WorldTypeAdapter())
                    .create();

            Type type = new com.google.common.reflect.TypeToken<List<Beacon>>(){}.getType();
            List<Beacon> beaconList = gson.fromJson(json, type);
            for (Beacon beacon : beaconList) {
                beacons.put(beacon.getLocation(), beacon);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
