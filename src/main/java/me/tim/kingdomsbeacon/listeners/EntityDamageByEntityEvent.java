package me.tim.kingdomsbeacon.listeners;

import me.tim.kingdomsbeacon.cache.KingdomsBeaconConfig;
import me.tim.kingdomsbeacon.managers.BeaconManager;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class EntityDamageByEntityEvent implements Listener {

    private final BeaconManager beaconManager;

    public EntityDamageByEntityEvent() {
        this.beaconManager = BeaconManager.getInstance();
    }

    @EventHandler
    public void onEntityDamage(org.bukkit.event.entity.EntityDamageByEntityEvent event) {
        Entity entity = event.getDamager();
        if (!(entity instanceof LivingEntity)) return;
        if (!(event.getEntity() instanceof Player)) return;
        Location mobLocation = entity.getLocation();
        for (Location beaconLocation : beaconManager.getBeacons().keySet()) {
            if (!beaconManager.getBeacon(beaconLocation).isActive()) continue;
            if (mobLocation.distance(beaconLocation) >= KingdomsBeaconConfig.radius) {
                event.setDamage(event.getDamage() * KingdomsBeaconConfig.damage);
            }
        }
    }
}
