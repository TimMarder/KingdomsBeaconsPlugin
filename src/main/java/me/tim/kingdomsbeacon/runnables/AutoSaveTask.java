package me.tim.kingdomsbeacon.runnables;

import me.tim.kingdomsbeacon.managers.BeaconInventoryManager;
import me.tim.kingdomsbeacon.managers.BeaconManager;
import me.tim.kingdomsbeacon.managers.PlayerManager;

public class AutoSaveTask implements Runnable {

    private final BeaconManager beaconManager;
    private final PlayerManager playerManager;
    private final BeaconInventoryManager beaconInventoryManager;

    public AutoSaveTask() {
        beaconManager = BeaconManager.getInstance();
        playerManager = PlayerManager.getInstance();
        beaconInventoryManager = BeaconInventoryManager.getInstance();
    }

    @Override
    public void run() {
        beaconManager.saveBeacons();
        playerManager.saveData();
        beaconInventoryManager.saveBeaconInventories();
    }
}
