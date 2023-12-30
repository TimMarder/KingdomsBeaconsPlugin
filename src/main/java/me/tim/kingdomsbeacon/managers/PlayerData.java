package me.tim.kingdomsbeacon.managers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerData {

    private int votes;
    private boolean isLeader;
    private UUID loyal;
    private String kingdom;
    private String title;
    private List<UUID> voters;

    public PlayerData() {
        this.votes = 0;
        this.isLeader = false;
        this.loyal = null;
        this.kingdom = null;
        this.title = null;
        this.voters = new ArrayList<>();
    }

    public void addVote(UUID voter) {
        this.votes++;
        this.voters.add(voter);
    }

    public void removeVote(UUID voter) {
        this.votes--;
        this.voters.remove(voter);
    }

    public int getVotes() {
        return this.votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public UUID getLoyal() {
        return this.loyal;
    }

    public void setLoyal(UUID uuid) {
        this.loyal = uuid;
    }

    public String getKingdom() {
        return kingdom;
    }

    public void setKingdom(String kingdom) {
        this.kingdom = kingdom;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isLeader() {
        return this.isLeader;
    }

    public void setLeader(boolean isLeader) {
        this.isLeader = isLeader;
    }

    public List<UUID> getVoters() {
        return this.voters;
    }

    public void setVoters(List<UUID> voters) {
        this.voters = voters;
    }
}
