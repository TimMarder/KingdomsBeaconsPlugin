package me.tim.kingdomsbeacon.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;

public class EntityExplodeEvent implements Listener {

    @EventHandler
    public void onEntityExplode(org.bukkit.event.entity.EntityExplodeEvent event) {
        List<Block> destroyedBlocks = event.blockList();
        destroyedBlocks.removeIf(nextBlock -> nextBlock.getType() == Material.BEACON);
    }
}
