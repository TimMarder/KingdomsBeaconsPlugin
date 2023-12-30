package me.tim.kingdomsbeacon.runnables;

import me.tim.kingdomsbeacon.managers.BeaconManager;
import org.bukkit.Location;

public class LastClickedTask implements Runnable {

    private final BeaconManager beaconManager;
    private static final long TIMEOUT = 20 * 60 * 60 * 48;

    public LastClickedTask() {
        this.beaconManager = BeaconManager.getInstance();
    }

    @Override
    public void run() {
        long currentTime = System.currentTimeMillis();
        for (Location beaconLocation : beaconManager.getBeacons().keySet()) {
            long lastTouchedTime = beaconManager.getBeacon(beaconLocation).getLastClicked();
            if (currentTime - lastTouchedTime >= TIMEOUT) {
                beaconManager.getBeacon(beaconLocation).setTouched(false);
                beaconManager.getBeacon(beaconLocation).setActive(false);
            }
        }
    }
}
