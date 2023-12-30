package me.tim.kingdomsbeacon.listeners;

import me.tim.kingdomsbeacon.managers.Beacon;
import me.tim.kingdomsbeacon.managers.BeaconInventoryManager;
import me.tim.kingdomsbeacon.managers.BeaconManager;
import me.tim.kingdomsbeacon.managers.PlayerManager;
import me.tim.kingdomsbeacon.utils.Placeholder;
import me.tim.kingdomsbeacon.utils.TL;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerInteractEvent implements Listener {

    private final BeaconManager beaconManager;
    private final BeaconInventoryManager beaconInventoryManager;
    private final PlayerManager playerManager;

    public PlayerInteractEvent() {
        beaconManager = BeaconManager.getInstance();
        beaconInventoryManager = BeaconInventoryManager.getInstance();
        playerManager = PlayerManager.getInstance();
    }

    @EventHandler
    public void onBeaconRightClick(org.bukkit.event.player.PlayerInteractEvent event) {
        if (event.getAction().isLeftClick()) return;
        if (event.getClickedBlock() == null) return;
        if (event.getClickedBlock().getType() != Material.BEACON) return;
        event.setCancelled(true);
        Block block = event.getClickedBlock();
        Player player = event.getPlayer();
        Beacon beacon = beaconManager.getBeacon(block.getLocation());
        if (beacon == null) return;
        if (beacon.getOwner() == null) {
            TL.CLAIM_BEACON_FIRST.send(player);
            return;
        }
        if (beacon.getOwner().equals(player.getUniqueId())) {
            beaconInventoryManager.openBeaconInventory(event.getPlayer(), event.getClickedBlock().getLocation());
            return;
        }
        if (!(playerManager.getVoters(beacon.getOwner()).contains(player.getUniqueId()))) {
            TL.NOT_BEACON_OWNER.send(player);
            return;
        }
        beaconInventoryManager.openBeaconInventory(event.getPlayer(), event.getClickedBlock().getLocation());
    }

    @EventHandler
    public void onBeaconLeftClick(org.bukkit.event.player.PlayerInteractEvent event) {
        if (event.getAction().isRightClick()) return;
        if (event.getClickedBlock() == null) return;
        if (event.getClickedBlock().getType() != Material.BEACON) return;
        Block block = event.getClickedBlock();
        Player player = event.getPlayer();
        Beacon beacon = beaconManager.getBeacon(block.getLocation());
        if (beacon == null) return;
        if (beacon.getOwner().equals(player.getUniqueId())) {
            beacon.setLastClicked(System.currentTimeMillis());
            beacon.setTouched(true);
        }
        if (beacon.getOwner() != null) {
            TL.BEACON_OWNER.send(player, new Placeholder("<player>", Bukkit.getOfflinePlayer(beacon.getOwner()).getName()));
            TL.BEACON_STATUS.send(player, new Placeholder("<status>", beacon.isActive() ? "&aActive" : "&cInactive"));
            return;
        }
        if (beacon.getOwner() == null) {
            if (!playerManager.isLeader(player.getUniqueId())) {
                TL.NOT_LEADER.send(player);
                return;
            }
        }
            beacon.setOwner(player.getUniqueId());
            TL.BEACON_CLAIMED.send(player);
        }
    }
