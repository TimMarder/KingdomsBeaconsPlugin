package me.tim.kingdomsbeacon.utils;

import me.tim.kingdomsbeacon.KingdomsBeacon;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.logging.Level;

public enum TL {

    NOT_LEADER("not-leader", "&cYou need to be a leader in order to perform this action."),
    GOT_LEADER("got-leader", "&a<player> got enough votes and has become a leader"),
    LOST_LEADER("lost-leader", "&c<player> doesn't have enough votes anymore and lost their leadership status"),
    NOT_BEACON_OWNER("not-beacon-owner", "&cThis beacon isn't owned by you."),
    ONLY_ACCEPTS_FUEL("only-accepts-fuel", "&cThis storage only accepts fuel."),
    CLAIM_BEACON_FIRST("claim-beacon-first", "&eLeft Click to claim beacon first!"),
    BEACONS_DEACTIVATED("beacons-deactivated", "&c<player>'s beacons have all been deactivated!"),
    BEACON_CLAIMED("beacon-claimed", "&aBeacon claimed!"),
    BEACON_OWNER("beacon-owner" , "&eBeacon Owner: &a<player>."),
    BEACON_STATUS("beacon-status", "&eBeacon Status: <status>"),
    PERMISSION_ERROR("permission-error", "&cYou do not have enough permissions to use this command!"),
    CONSOLE_ERROR("console-error", "Console is not allowed to use this command!"),
    PLAYER_ERROR("player-error", "&cPlayers are not allowed to use this command!"),
    CONFIG_RELOAD("config-reload", "&aConfig reloaded!"),
    PLAYER_NOT_FOUND("player-not-found", "&cPlayer not found!"),
    TOTAL_VOTES("total-votes", "&eYou currently have &A<votes> &evotes."),
    LOYAL_TO("loyal-to", "&e<player> is loyal to &a<loyal>&e."),
    ALREADY_HAS_KINGDOM("already-has-kingdom", "&cAlready a part of kingdom."),
    KINGDOM_CREATED("kingdom-created", "&aKingdom created!"),
    NOT_LOYAL("not-loyal", "&cThis player isn't loyal to anyone."),
    TITLE_CHANGED("title-changed", "&aThis player's title has been changed."),
    OWN_TITLE("own-title", "&cYou can't change your own title"),
    OWN_VOTE("own-vote", "&cYou can't vote for yourself."),
    ALREADY_LOYAL("already-loyal", "&cYou are already loyal to this person."),
    BEACON_GIVEN("beacon-given", "&a<amount> beacons given to <player>."),
    BEACON_RECEIVED("beacon-received", "&aYou received <amount> beacons!"),
    INVENTORY_FULL("inventory-full", "&cInventory full!");

    private String path, message;

    TL(String path, String message) {
        this.path = path;
        this.message = message;
    }

    public void send(CommandSender sender, Placeholder... placeHolders) {

        if (this.message.contains("\\n")) {
            for (String string : this.message.split("\n")) {
                sender.sendMessage(sender instanceof Player ? TextUtility.colorize(string, placeHolders) : TextUtility.strip(string, placeHolders));
            }
            return;
        }

        sender.sendMessage(sender instanceof Player ? TextUtility.colorize(this.message, placeHolders) : TextUtility.strip(this.message, placeHolders));
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static void log(Level lvl, String message) {
        Bukkit.getLogger().log(lvl, "[" + KingdomsBeacon.getInstance().getName() + "] " + message);
    }
}
