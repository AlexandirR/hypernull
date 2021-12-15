package ru.croccode.hypernull.statistic.entities;

public class MatchInfo {
    private String id;
    private int coinsSpawn;
    private String mode;
    private int kills;

    public MatchInfo(String id, int coinsSpawn, String mode, int kills) {
        this.id = id;
        this.coinsSpawn = coinsSpawn;
        this.mode = mode;
        this.kills = kills;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCoinsSpawn() {
        return coinsSpawn;
    }

    public void setCoinsSpawn(int coinsSpawn) {
        this.coinsSpawn = coinsSpawn;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }
}
