package me.tim.kingdomsbeacon.commands;

import me.tim.kingdomsbeacon.cache.KingdomsBeaconConfig;
import me.tim.kingdomsbeacon.managers.PlayerManager;
import me.tim.kingdomsbeacon.utils.Placeholder;
import me.tim.kingdomsbeacon.utils.TL;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;

public class GiveLoyaltyCommand extends CommandExecutor {

    private final PlayerManager playerManager;

    public GiveLoyaltyCommand() {
        this.playerManager = PlayerManager.getInstance();
        setCommand("giveloyalty");
        setUsage("/kingdom giveloyalty <name>");
        setPlayer(true);
        setLength(2);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        Player target = Bukkit.getPlayer(args[1]);

        if (target == null) {
            TL.PLAYER_NOT_FOUND.send(sender);
            return;
        }
        if (target == player) {
            TL.OWN_VOTE.send(player);
            return;
        }
        if (playerManager.getLoyal(player.getUniqueId()) == null) {
            playerManager.addVote(player.getUniqueId(), target.getUniqueId());
            playerManager.setLoyal(player.getUniqueId(), target.getUniqueId());
            if (playerManager.hasEnoughVotes(target.getUniqueId(), KingdomsBeaconConfig.votes)) {
                playerManager.setLeader(target.getUniqueId(), true);
                playerManager.updateName(target.getUniqueId());
                target.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 1, true, false));
                for (Player players : Bukkit.getOnlinePlayers()) {
                    TL.GOT_LEADER.send(players, new Placeholder("<player>", target.getName()));
                }
            }
            return;
        }
        if (playerManager.getLoyal(player.getUniqueId()).equals(target.getUniqueId())) {
            TL.ALREADY_LOYAL.send(player);
            return;
        }
        playerManager.removeVote(playerManager.getLoyal(player.getUniqueId()), player.getUniqueId());
        playerManager.addVote(player.getUniqueId(), target.getUniqueId());
        if (KingdomsBeaconConfig.disband) {
            if (playerManager.hasEnoughVotes(playerManager.getLoyal(player.getUniqueId()), KingdomsBeaconConfig.votes)) {
                playerManager.setLeader(playerManager.getLoyal(player.getUniqueId()), false);
                playerManager.setKingdom(playerManager.getLoyal(player.getUniqueId()), null);
                playerManager.setTitle(playerManager.getLoyal(player.getUniqueId()), null);
                Bukkit.getPlayer(playerManager.getLoyal(player.getUniqueId())).removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
                playerManager.updateName(playerManager.getLoyal(player.getUniqueId()));
                for (Player players : Bukkit.getOnlinePlayers()) {
                    TL.LOST_LEADER.send(players, new Placeholder("<player>", target.getName()));
                }
                for (UUID voter : playerManager.getVoters(playerManager.getLoyal(player.getUniqueId()))) {
                    playerManager.setKingdom(voter, null);
                    playerManager.setTitle(voter, null);
                    playerManager.updateName(voter);
                }
            }
        }
        playerManager.setLoyal(player.getUniqueId(), target.getUniqueId());
    }
}
