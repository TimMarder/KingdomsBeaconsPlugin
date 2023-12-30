package me.tim.kingdomsbeacon.commands;

import me.tim.kingdomsbeacon.managers.PlayerManager;
import me.tim.kingdomsbeacon.utils.Placeholder;
import me.tim.kingdomsbeacon.utils.TL;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GiveLeaderCommand extends CommandExecutor {

    private final PlayerManager playerManager;

    public GiveLeaderCommand() {
        this.playerManager = PlayerManager.getInstance();
        setCommand("giveleader");
        setUsage("/kingdom giveleader <player>");
        setBoth();
        setLength(2);
        setPermission("kingdomsbeacon.giveleader");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            TL.PLAYER_NOT_FOUND.send(sender);
            return;
        }
        playerManager.setLeader(target.getUniqueId(), true);
        TL.GOT_LEADER.send(sender, new Placeholder("<player>", target.getName()));
    }
}
