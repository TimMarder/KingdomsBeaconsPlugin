package me.tim.kingdomsbeacon.runnables;

import me.tim.kingdomsbeacon.managers.BeaconInventoryManager;
import me.tim.kingdomsbeacon.managers.BeaconManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class FuelTask implements Runnable {

    private final BeaconManager beaconManager;
    private final BeaconInventoryManager beaconInventoryManager;

    public FuelTask() {
        this.beaconManager = BeaconManager.getInstance();
        this.beaconInventoryManager = BeaconInventoryManager.getInstance();
    }

    @Override
    public void run() {
        for (Location beaconInventoryLocation : beaconInventoryManager.getBeaconInventories().keySet()) {
            if (!beaconManager.getBeacon(beaconInventoryLocation).isTouched()) continue;
            Inventory inventory = beaconInventoryManager.getBreaconInventory(beaconInventoryLocation);
            if (inventory == null) continue;
            if (inventory.contains(Material.WATER_BUCKET)) {
                ItemStack[] contents = inventory.getContents();
                for (ItemStack item : contents) {
                    if (item != null && item.getType() == Material.WATER_BUCKET) {
                        item.setAmount(item.getAmount() - 1);
                        beaconManager.getBeacon(beaconInventoryLocation).setActive(true);
                        break;
                    }
                }
                return;
            }
            beaconManager.getBeacon(beaconInventoryLocation).setActive(false);
        }
    }
}
