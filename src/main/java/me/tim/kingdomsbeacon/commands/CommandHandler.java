package me.tim.kingdomsbeacon.commands;

import me.tim.kingdomsbeacon.KingdomsBeacon;
import me.tim.kingdomsbeacon.utils.TL;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class CommandHandler implements CommandExecutor {

    private final HashMap<String, me.tim.kingdomsbeacon.commands.CommandExecutor> commands = new HashMap<>();


    public CommandHandler() {
        commands.put("reload", new ReloadCommand());
        commands.put("votes", new CheckVotesCommand());
        commands.put("checkloyalty", new CheckLoyaltyCommand());
        commands.put("create", new CreateCommand());
        commands.put("settitle", new SetTitleCommand());
        commands.put("giveloyalty", new GiveLoyaltyCommand());
        commands.put("giveleader", new GiveLeaderCommand());
        commands.put("disband", new DisbandCommand());
        commands.put("givebeacon", new GiveBeaconCommand());
    }

    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {

        if (cmd.getName().equalsIgnoreCase("kingdom")) {

            if (args.length == 0) {
                sender.sendMessage(KingdomsBeacon.color("&6========= KingdomBeacons - Command list ========="));
                sender.sendMessage(KingdomsBeacon.color("&e/kingdom reload &f- Configuration & Messages reload"));
                sender.sendMessage(KingdomsBeacon.color("&e/kingdom create <name> &f- Creates a kingdom"));
                sender.sendMessage(KingdomsBeacon.color("&e/kingdom giveloyalty <name> &f- Gives your loyalty to a player"));
                sender.sendMessage(KingdomsBeacon.color("&e/kingdom checkloyalty <name> &f- Shows the player's leader"));
                sender.sendMessage(KingdomsBeacon.color("&e/kingdom votes &f- Shows your votes"));
                sender.sendMessage(KingdomsBeacon.color("&e/kingdom settitle <player> <title> &f- Sets a player's title"));
                sender.sendMessage(KingdomsBeacon.color("&e/kingdom giveleader <player> &f- Makes a player leader"));
                sender.sendMessage(KingdomsBeacon.color("&e/kingdom disband <player> &f- Disbands player's kingdom"));
                sender.sendMessage(KingdomsBeacon.color("&e/kingdom givebeacon <player> <amount> &f- Gives player a beacon"));
                sender.sendMessage(KingdomsBeacon.color("&6============== Version 1.0 =============="));
                return true;
            }

            if (args[0] != null) {
                String name = args[0].toLowerCase();
                if (commands.containsKey(name)) {
                    final me.tim.kingdomsbeacon.commands.CommandExecutor command = commands.get(name);

                    if (command.getPermission() != null && !sender.hasPermission(command.getPermission())) {
                        TL.PERMISSION_ERROR.send(sender);
                        return true;
                    }

                    if (!command.isBoth()) {
                        if (command.isConsole() && sender instanceof Player) {
                            TL.PLAYER_ERROR.send(sender);
                            return true;
                        }
                        if (command.isPlayer() && sender instanceof ConsoleCommandSender) {
                            TL.CONSOLE_ERROR.send(sender);
                            return true;
                        }
                    }

                    if (command.getLength() > args.length) {
                        sender.sendMessage(KingdomsBeacon.color("&eCorrect Usage: " + command.getUsage()));
                        return true;
                    }

                    command.execute(sender, args);
                }
            }
        }
        return false;
    }
}

