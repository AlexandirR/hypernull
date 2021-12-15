package ru.croccode.hypernull.statistic.entities;

public class MatchTable {
    private String idMatch;
    private int idBot;
    private int coins;
    private int kills;
    private int deaths;
    private int fieldsOpen;

    public MatchTable(String idMatch, int idBot, int coins, int kills, int deaths, int fieldsOpen) {
        this.idMatch = idMatch;
        this.idBot = idBot;
        this.coins = coins;
        this.kills = kills;
        this.deaths = deaths;
        this.fieldsOpen = fieldsOpen;
    }

    public String getIdMatch() {
        return idMatch;
    }

    public void setIdMatch(String idMatch) {
        this.idMatch = idMatch;
    }

    public int getIdBot() {
        return idBot;
    }

    public void setIdBot(int idBot) {
        this.idBot = idBot;
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

    public int getFieldsOpen() {
        return fieldsOpen;
    }

    public void setFieldsOpen(int fieldsOpen) {
        this.fieldsOpen = fieldsOpen;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }
}
