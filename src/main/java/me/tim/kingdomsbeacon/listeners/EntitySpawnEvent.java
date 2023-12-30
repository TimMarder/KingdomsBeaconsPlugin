package me.tim.kingdomsbeacon.listeners;

import me.tim.kingdomsbeacon.cache.KingdomsBeaconConfig;
import me.tim.kingdomsbeacon.managers.BeaconManager;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class EntitySpawnEvent implements Listener {

    private final BeaconManager beaconManager;

    public EntitySpawnEvent() {
        this.beaconManager = BeaconManager.getInstance();
    }

    @EventHandler
    public void onEntitySpawn(org.bukkit.event.entity.EntitySpawnEvent event) {
        Entity entity = event.getEntity();
        if (!(entity instanceof LivingEntity)) return;
        if (!(entity instanceof Monster)) return;
        Location mobLocation = entity.getLocation();
        for (Location beaconLocation : beaconManager.getBeacons().keySet()) {
            if (!beaconManager.getBeacon(beaconLocation).isActive()) continue;
            if (mobLocation.distance(beaconLocation) <= KingdomsBeaconConfig.radius) {
            event.setCancelled(true);
        }
        }
    }
}
