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
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class BlockBreakEvent implements Listener {

    private final PlayerManager playerManager;
    private final BeaconManager beaconManager;
    private final BeaconInventoryManager beaconInventoryManager;

    public BlockBreakEvent() {
        playerManager = PlayerManager.getInstance();
        beaconManager = BeaconManager.getInstance();
        beaconInventoryManager = BeaconInventoryManager.getInstance();
    }

    @EventHandler
    public void onBeaconBreak(org.bukkit.event.block.BlockBreakEvent event) {
        Block block = event.getBlock();
        if (block.getType() != Material.BEACON) return;
        Location location = block.getLocation();
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        if (!playerManager.isLeader(uuid)) {
            event.setCancelled(true);
            TL.NOT_LEADER.send(player);
            return;
        }
        if (beaconManager.getBeacon(location) == null) return;
        if (!beaconManager.getBeacon(location).hasOwner() || beaconManager.getBeacon(location).getOwner().equals(uuid)) {
            beaconManager.removeBeacon(location);
            for (ItemStack itemStack : beaconInventoryManager.getBreaconInventory(location).getContents()) {
                if (itemStack == null) continue;
                block.getWorld().dropItemNaturally(location, itemStack);
            }
            beaconInventoryManager.removeBeaconInventory(location);
            return;
        }
        event.setCancelled(true);
        TL.NOT_BEACON_OWNER.send(player);
    }
}
