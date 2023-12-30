package me.tim.kingdomsbeacon.listeners;

import me.tim.kingdomsbeacon.utils.TL;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

public class InventoryClickEvent implements Listener {

    @EventHandler
    public void onBeaconInventoryClick(org.bukkit.event.inventory.InventoryClickEvent event) {
        if (event.getClickedInventory() == null) return;
        if (!event.getView().getTitle().equalsIgnoreCase("Fuel")) return;
        Player player = (Player) event.getWhoClicked();
        Inventory inventory = player.getInventory();
        if (!event.getClickedInventory().equals(inventory)) return;
        if (event.getCurrentItem() == null) return;
        if (event.getCurrentItem().getType() == Material.WATER_BUCKET) return;
        event.setCancelled(true);
        TL.ONLY_ACCEPTS_FUEL.send(player);
    }
}
