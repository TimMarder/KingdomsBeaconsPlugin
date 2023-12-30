package me.tim.kingdomsbeacon;

import me.tim.kingdomsbeacon.cache.KingdomsBeaconConfig;
import me.tim.kingdomsbeacon.commands.CommandHandler;
import me.tim.kingdomsbeacon.commands.TabComplete;
import me.tim.kingdomsbeacon.listeners.*;
import me.tim.kingdomsbeacon.managers.BeaconInventoryManager;
import me.tim.kingdomsbeacon.managers.BeaconManager;
import me.tim.kingdomsbeacon.managers.PlayerManager;
import me.tim.kingdomsbeacon.recipes.RecipeManager;
import me.tim.kingdomsbeacon.runnables.AutoSaveTask;
import me.tim.kingdomsbeacon.runnables.FuelTask;
import me.tim.kingdomsbeacon.runnables.LastClickedTask;
import me.tim.kingdomsbeacon.utils.TL;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public final class KingdomsBeacon extends JavaPlugin {

    private static KingdomsBeacon instance;
    private final BeaconManager beaconManager;
    private final PlayerManager playerManager;
    private final BeaconInventoryManager beaconInventoryManager;

    public KingdomsBeacon() {
        beaconManager = BeaconManager.getInstance();
        playerManager = PlayerManager.getInstance();
        beaconInventoryManager = BeaconInventoryManager.getInstance();
    }

    public static KingdomsBeacon getInstance() {
        return instance;
    }


    public static String color(final String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    @Override
    public void onLoad() {
        instance = this;
        File config = new File(getDataFolder(), "config.yml");
        if (!config.exists()) saveDefaultConfig();
        File messages = new File(getDataFolder(), "messages.yml");
        if (!messages.exists()) {
            try {
                messages.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        File dataFolder = new File(getDataFolder(), "Data");
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        File beacons = new File(dataFolder, "beacons.json");
        if (!beacons.exists()) {
            try {
                beacons.createNewFile();
                FileWriter fileWriter = new FileWriter(beacons);
                fileWriter.write("[]");
                fileWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        File players = new File(dataFolder, "players.json");
        if (!players.exists()) {
            try {
                players.createNewFile();
                FileWriter fileWriter = new FileWriter(players);
                fileWriter.write("[]");
                fileWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        File inventories = new File(dataFolder, "inventories.json");
        if (!inventories.exists()) {
            try {
                inventories.createNewFile();
                FileWriter fileWriter = new FileWriter(inventories);
                fileWriter.write("[]");
                fileWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onEnable() {
        Bukkit.getScheduler().runTaskAsynchronously(this, () -> {
            beaconManager.loadBeacons();
            beaconInventoryManager.loadBeaconInventories();
            playerManager.loadData();
        });
        KingdomsBeaconConfig.register();
        RecipeManager.init();
        registerEvents();
        registerMessages();
        registerCommands();
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, new LastClickedTask(), 20, 20 * 60 * 30);
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, new FuelTask(), 20, 20 * 60 * 60);
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, new AutoSaveTask(), 20, 20 * 60 * 5);
    }

    @Override
    public void onDisable() {
    Bukkit.getScheduler().cancelTasks(this);
    beaconManager.saveBeacons();
    beaconInventoryManager.saveBeaconInventories();
    playerManager.saveData();
    }

    public File getBeaconFile() {
        return new File(getDataFolder(), "Data/beacons.json");
    }

    public File getPlayerFile() {
        return new File(getDataFolder(), "Data/players.json");
    }

    public File getInventoryFile() {
        return new File(getDataFolder(), "Data/inventories.json");
    }

    private void registerEvents() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new EntityExplodeEvent(), this);
        pluginManager.registerEvents(new BlockPlaceEvent(), this);
        pluginManager.registerEvents(new BlockBreakEvent(), this);
        pluginManager.registerEvents(new PlayerInteractEvent(), this);
        pluginManager.registerEvents(new PlayerJoinEvent(), this);
        pluginManager.registerEvents(new EntitySpawnEvent(), this);
        pluginManager.registerEvents(new EntityDamageByEntityEvent(), this);
        pluginManager.registerEvents(new InventoryClickEvent(), this);
        pluginManager.registerEvents(new PlayerDeathEvent(), this);
        pluginManager.registerEvents(new PrepareItemCraftEvent(), this);
    }

    private void registerCommands() {
        getCommand("kingdom").setExecutor(new CommandHandler());
        getCommand("kingdom").setTabCompleter(new TabComplete());
    }

    public void registerMessages() {

        File file = new File(getDataFolder() + File.separator + "messages.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        for (TL message : TL.values()) {
            if (config.getString("messages." + message.getPath()) != null) {
                message.setMessage(config.getString("messages." + message.getPath()));
            } else {
                config.set("messages." + message.getPath(), message.getMessage());
            }
        }
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
