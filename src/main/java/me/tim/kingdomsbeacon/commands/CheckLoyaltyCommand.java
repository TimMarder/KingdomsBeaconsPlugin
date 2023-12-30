package me.tim.kingdomsbeacon.commands;

import me.tim.kingdomsbeacon.managers.PlayerManager;
import me.tim.kingdomsbeacon.utils.Placeholder;
import me.tim.kingdomsbeacon.utils.TL;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class CheckLoyaltyCommand extends CommandExecutor {

    private final PlayerManager playerManager;

    public CheckLoyaltyCommand() {
        this.playerManager = PlayerManager.getInstance();
        setCommand("checkloyalty");
        setUsage("/kingdom checkloyalty <player>");
        setPlayer(true);
        setLength(2);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            TL.PLAYER_NOT_FOUND.send(sender);
            return;
        }
        if (playerManager.getLoyal(target.getUniqueId()) == null) {
            TL.NOT_LOYAL.send(sender);
            return;
        }
        UUID loyal = playerManager.getLoyal(target.getUniqueId());
        TL.LOYAL_TO.send(sender, new Placeholder("<player>", target.getName()), new Placeholder("<loyal>", Bukkit.getOfflinePlayer(loyal).getName()));
    }
}
