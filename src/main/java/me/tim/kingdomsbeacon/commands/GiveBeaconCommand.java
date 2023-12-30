package me.tim.kingdomsbeacon.commands;

import me.tim.kingdomsbeacon.KingdomsBeacon;
import me.tim.kingdomsbeacon.utils.Placeholder;
import me.tim.kingdomsbeacon.utils.TL;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GiveBeaconCommand extends CommandExecutor {

    public GiveBeaconCommand() {
        setCommand("givebeacon");
        setUsage("/kingdom givebeacon <name> <amount>");
        setPlayer(true);
        setLength(3);
        setPermission("kingdomsbeacon.givebeacon");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        Player target = Bukkit.getPlayer(args[1]);

        if (target == null) {
            TL.PLAYER_NOT_FOUND.send(sender);
            return;
        }
        if (target.getInventory().firstEmpty() == -1) {
            TL.INVENTORY_FULL.send(player);
            return;
        }
        try {
            int amount = Integer.parseInt(args[2]);
        ItemStack beacon = new ItemStack(Material.BEACON, amount);
        target.getInventory().addItem(beacon);
        TL.BEACON_GIVEN.send(player, new Placeholder("<player>", target.getName()), new Placeholder("<amount>", amount));
        TL.BEACON_RECEIVED.send(target, new Placeholder("<amount>", amount));
        } catch (NumberFormatException e) {
            sender.sendMessage(KingdomsBeacon.color("&eCorrect Usage: " + getUsage()));
        }
    }
}
