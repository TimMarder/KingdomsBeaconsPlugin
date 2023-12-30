package me.tim.kingdomsbeacon.listeners;

import me.tim.kingdomsbeacon.managers.BeaconInventoryManager;
import me.tim.kingdomsbeacon.managers.BeaconManager;
import me.tim.kingdomsbeacon.managers.PlayerManager;
import me.tim.kingdomsbeacon.utils.TL;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.UUID;

public class BlockPlaceEvent implements Listener {

    private final BeaconManager beaconManager;
    private final PlayerManager playerManager;
    private final BeaconInventoryManager beaconInventoryManager;

    public BlockPlaceEvent() {
        beaconManager = BeaconManager.getInstance();
        playerManager = PlayerManager.getInstance();
        beaconInventoryManager = BeaconInventoryManager.getInstance();
    }

    @EventHandler
    public void onBeaconPlace(org.bukkit.event.block.BlockPlaceEvent event) {
        Block block = event.getBlockPlaced();
        if (block.getType() != Material.BEACON) return;
        Player player = event.getPlayer();
        if (!(playerManager.isLeader(player.getUniqueId()))) {
            event.setCancelled(true);
            TL.NOT_LEADER.send(player);
            return;
        }
        Location location = block.getLocation();
        UUID uuid = event.getPlayer().getUniqueId();
        long currentTime = System.currentTimeMillis();
        beaconManager.addBeacon(location, uuid, currentTime, true);
        beaconInventoryManager.createBeaconInventory(location);
    }
}
