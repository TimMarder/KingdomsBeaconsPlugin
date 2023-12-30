package me.tim.kingdomsbeacon.listeners;

import com.earth2me.essentials.Essentials;
import me.tim.kingdomsbeacon.managers.PlayerManager;
import me.neznamy.tab.api.TabAPI;
import me.neznamy.tab.api.event.player.PlayerLoadEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;

public class PlayerJoinEvent implements Listener {

    private final PlayerManager playerManager;

    public PlayerJoinEvent() {
        this.playerManager = PlayerManager.getInstance();
    }

    @EventHandler
    public void onPlayerJoin(org.bukkit.event.player.PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();
        Essentials essentials = (Essentials) Bukkit.getPluginManager().getPlugin("Essentials");
        essentials.getUser(playerUUID).setNickname(player.getName());
        if (!playerManager.hasPlayerData(playerUUID)) {
            playerManager.setDefaultValues(playerUUID);
            return;
        }
        if (playerManager.isLeader(playerUUID)) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 1, true, false));
        }
        TabAPI.getInstance().getEventBus().register(PlayerLoadEvent.class, e -> {
            playerManager.updateName(playerUUID);
        });

    }

}
