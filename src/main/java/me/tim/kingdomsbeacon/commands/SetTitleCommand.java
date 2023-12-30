package me.tim.kingdomsbeacon.commands;

import me.tim.kingdomsbeacon.managers.PlayerManager;
import me.tim.kingdomsbeacon.utils.TL;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetTitleCommand extends CommandExecutor {

    private final PlayerManager playerManager;

    public SetTitleCommand() {
        this.playerManager = PlayerManager.getInstance();
        setCommand("settitle");
        setUsage("/kingdom settitle <player> <title>");
        setPlayer(true);
        setLength(3);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        if (!playerManager.isLeader(player.getUniqueId())) {
            TL.NOT_LEADER.send(player);
            return;
        }
        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            TL.PLAYER_NOT_FOUND.send(sender);
            return;
        }
        if (target == player) {
            TL.OWN_TITLE.send(player);
            return;
        }
        if (!playerManager.getVoters(player.getUniqueId()).contains(target.getUniqueId())) {
            TL.NOT_LOYAL.send(player);
            return;
        }
        playerManager.setTitle(target.getUniqueId(), args[2]);
        playerManager.updateName(target.getUniqueId());
        TL.TITLE_CHANGED.send(player);
    }
}
