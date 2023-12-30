package me.tim.kingdomsbeacon.commands;

import me.tim.kingdomsbeacon.KingdomsBeacon;
import me.tim.kingdomsbeacon.cache.KingdomsBeaconConfig;
import me.tim.kingdomsbeacon.utils.TL;
import org.bukkit.command.CommandSender;

public class ReloadCommand extends CommandExecutor {
    public ReloadCommand() {
        setCommand("reload");
        setPermission("kingdomsbeacon.reload");
        setUsage("/kingdom reload");
        setBoth();
        setLength(1);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        KingdomsBeacon.getInstance().reloadConfig();
        KingdomsBeacon.getInstance().registerMessages();
        KingdomsBeaconConfig.register();
        TL.CONFIG_RELOAD.send(sender);
    }
}