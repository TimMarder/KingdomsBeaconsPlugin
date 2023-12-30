package me.tim.kingdomsbeacon.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class TabComplete implements TabCompleter {

    List<String> arguments = new ArrayList<String>();

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (arguments.isEmpty()) {
            arguments.add("reload");
            arguments.add("votes");
            arguments.add("checkloyalty");
            arguments.add("create");
            arguments.add("settitle");
            arguments.add("giveloyalty");
            arguments.add("giveleader");
            arguments.add("disband");
            arguments.add("givebeacon");
        }

        List<String> result = new ArrayList<String>();
        if (args[0].equals("reload") || args[0].equals("votes") || args[0].equals("create")) {
            return result;
        }
        if (args.length == 1) {
            for (String arg : arguments) {
                if (arg.toLowerCase().startsWith(args[0].toLowerCase()))
                    result.add(arg);
            }
            return result;
        }

        if (args.length > 2) {
            return result;
        }
        return null;
    }
}
