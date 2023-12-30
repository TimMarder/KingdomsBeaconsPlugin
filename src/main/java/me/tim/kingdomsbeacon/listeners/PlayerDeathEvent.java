package me.tim.kingdomsbeacon.listeners;

import me.tim.kingdomsbeacon.managers.BeaconManager;
import me.tim.kingdomsbeacon.utils.Placeholder;
import me.tim.kingdomsbeacon.utils.TL;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.UUID;

public class PlayerDeathEvent implements Listener {

    private final BeaconManager beaconManager;

    public PlayerDeathEvent() {
        beaconManager = BeaconManager.getInstance();
    }

    @EventHandler
    public void onPlayerDeath(org.bukkit.event.entity.PlayerDeathEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        for (Location beaconLocation : beaconManager.getBeacons().keySet()) {
            if (beaconManager.getBeacon(beaconLocation).getOwner() == null) continue;
            if (!beaconManager.getBeacon(beaconLocation).getOwner().equals(uuid)) continue;
            beaconManager.getBeacon(beaconLocation).setOwner(null);
            beaconManager.getBeacon(beaconLocation).setActive(false);
        }
        for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
            TL.BEACONS_DEACTIVATED.send(onlinePlayers, new Placeholder("<player>", player.getName()));
        }
    }
}
