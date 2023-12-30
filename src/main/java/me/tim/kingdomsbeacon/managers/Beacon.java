package me.tim.kingdomsbeacon.managers;

import org.bukkit.Location;

import java.util.UUID;

public class Beacon {

    private UUID playerUUID;
    private final Location location;
    private boolean active;
    private long lastClicked;
    private boolean touched;

    public Beacon(UUID playerUUID, Location location, boolean active, long lastClicked, boolean touched) {
        this.location = location;
        this.playerUUID = playerUUID;
        this.active = active;
        this.lastClicked = lastClicked;
        this.touched = touched;
    }

    public UUID getOwner() {
        return playerUUID;
    }

    public boolean hasOwner() {
        return playerUUID != null;
    }

    public void setOwner(UUID playerUUID) {
        this.playerUUID = playerUUID;
    }

    public Location getLocation() {
        return location;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public long getLastClicked() {
        return lastClicked;
    }

    public void setLastClicked(Long lastClicked) {
        this.lastClicked = lastClicked;
    }

    public boolean isTouched() {
        return touched;
    }

    public void setTouched(boolean touched) {
        this.touched = touched;
    }
}
