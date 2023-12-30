package me.tim.kingdomsbeacon.commands;

import me.tim.kingdomsbeacon.managers.PlayerManager;
import me.tim.kingdomsbeacon.utils.TL;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class CreateCommand extends CommandExecutor {

    private final PlayerManager playerManager;

    public CreateCommand() {
        this.playerManager = PlayerManager.getInstance();
        setCommand("create");
        setUsage("/kingdom create <name>");
        setPlayer(true);
        setLength(2);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        if (!playerManager.isLeader(player.getUniqueId())) {
            TL.NOT_LEADER.send(player);
            return;
        }

        if (playerManager.getKingdom(player.getUniqueId()) != null) {
            TL.ALREADY_HAS_KINGDOM.send(player);
            return;
        }
        String kingdom = args[1];
        playerManager.setKingdom(player.getUniqueId(), kingdom);
        playerManager.setTitle(player.getUniqueId(), "&cLeader");
        playerManager.updateName(player.getUniqueId());
        TL.KINGDOM_CREATED.send(player);
        for (UUID voter : playerManager.getVoters(player.getUniqueId())) {
            playerManager.setKingdom(voter, kingdom);
            playerManager.updateName(voter);
        }
    }
}
