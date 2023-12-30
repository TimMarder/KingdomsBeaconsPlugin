package me.tim.kingdomsbeacon.cache;

import me.tim.kingdomsbeacon.KingdomsBeacon;
import org.bukkit.configuration.file.FileConfiguration;

public class KingdomsBeaconConfig {

    public static KingdomsBeaconConfig plugin;
    public static FileConfiguration config;
    public static Integer radius;
    public static Integer damage;
    public static Integer votes;
    public static Boolean disband;

    public KingdomsBeaconConfig() {
        plugin = this;
        register();
    }

    public static void register() {
        config = KingdomsBeacon.getInstance().getConfig();
        radius = config.getInt(KingdomsBeacon.color("kingdoms-beacon.beacon-radius"));
        damage = config.getInt(KingdomsBeacon.color("kingdoms-beacon.damage-multiplier"));
        votes = config.getInt(KingdomsBeacon.color("kingdoms-beacon.required-votes"));
        disband = config.getBoolean(KingdomsBeacon.color("kingdoms-beacon.disband-kingdoms"));
    }
}
