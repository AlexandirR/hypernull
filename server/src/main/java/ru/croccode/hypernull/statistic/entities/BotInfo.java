package ru.croccode.hypernull.statistic.entities;

public class BotInfo {
    private int id;
    private String name;
    private int coins;
    private int kills;
    private int deaths;

    public BotInfo(int id, String name, int coins, int kills, int death) {
        this.id = id;
        this.name = name;
        this.coins = coins;
        this.kills = kills;
        this.deaths = death;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getDeath() {
        return deaths;
    }

    public void setDeath(int death) {
        this.deaths = death;
    }
}
