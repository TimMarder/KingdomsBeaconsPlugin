package me.tim.kingdomsbeacon.commands;

import me.tim.kingdomsbeacon.managers.PlayerManager;
import me.tim.kingdomsbeacon.utils.Placeholder;
import me.tim.kingdomsbeacon.utils.TL;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;

public class DisbandCommand extends CommandExecutor {

    private final PlayerManager playerManager;

    public DisbandCommand() {
        this.playerManager = PlayerManager.getInstance();
        setCommand("disband");
        setUsage("/kingdom disband <player>");
        setBoth();
        setLength(2);
        setPermission("kingdomsbeacon.disband");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            TL.PLAYER_NOT_FOUND.send(sender);
            return;
        }
        if (!playerManager.isLeader(target.getUniqueId())) {
            TL.NOT_LEADER.send(sender);
            return;
        }
        playerManager.setLeader(target.getUniqueId(), false);
        playerManager.setKingdom(target.getUniqueId(), null);
        playerManager.setTitle(target.getUniqueId(), null);
        playerManager.updateName(target.getUniqueId());
        target.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
        for (Player players : Bukkit.getOnlinePlayers()) {
            TL.LOST_LEADER.send(players, new Placeholder("<player>", target.getName()));
        }
        for (UUID voter : playerManager.getVoters(target.getUniqueId())) {
            playerManager.setKingdom(voter, null);
            playerManager.setTitle(voter, null);
            playerManager.setLoyal(voter, null);
            playerManager.updateName(voter);
        }
        playerManager.clearVoters(target.getUniqueId());
        playerManager.setVotes(target.getUniqueId(), 0);
    }
}
