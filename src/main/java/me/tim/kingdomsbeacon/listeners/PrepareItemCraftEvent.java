package me.tim.kingdomsbeacon.listeners;

import me.tim.kingdomsbeacon.managers.PlayerManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class PrepareItemCraftEvent implements Listener {

    private final PlayerManager playerManager;

    public PrepareItemCraftEvent() {
        this.playerManager = PlayerManager.getInstance();
    }

    @EventHandler
    public void onBeaconCraft(org.bukkit.event.inventory.PrepareItemCraftEvent event) {
        if (event.getRecipe() == null) return;
        if (event.getRecipe().getResult().getType() != Material.BEACON) return;
        Player player = (Player) event.getInventory().getHolder();
        if (player == null) return;
        if (playerManager.isLeader(player.getUniqueId())) return;
        ItemStack itemStack = new ItemStack(Material.AIR);
        event.getInventory().setResult(itemStack);
    }
}
