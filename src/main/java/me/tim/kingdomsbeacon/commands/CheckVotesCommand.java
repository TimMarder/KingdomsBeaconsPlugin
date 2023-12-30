package me.tim.kingdomsbeacon.commands;

import me.tim.kingdomsbeacon.managers.PlayerManager;
import me.tim.kingdomsbeacon.utils.Placeholder;
import me.tim.kingdomsbeacon.utils.TL;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CheckVotesCommand extends CommandExecutor {

    private final PlayerManager playerManager;

    public CheckVotesCommand() {
        this.playerManager = PlayerManager.getInstance();
        setCommand("votes");
        setUsage("/kingdom votes");
        setPlayer(true);
        setLength(1);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        int votes = playerManager.getVotes(player.getUniqueId());
        TL.TOTAL_VOTES.send(sender, new Placeholder("<votes>", votes));
    }
}
