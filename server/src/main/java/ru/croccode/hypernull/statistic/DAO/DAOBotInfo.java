package ru.croccode.hypernull.statistic.DAO;

import ru.croccode.hypernull.statistic.entities.BotInfo;

import java.lang.reflect.Field;
import java.sql.*;

public class DAOBotInfo {
    private Connection connection;

    public DAOBotInfo(Connection connection) {
        this.connection = connection;
    }

    public BotInfo findBotInfo(String name) throws SQLException {
        String SQL = "SELECT * FROM " + BotInfo.class.getSimpleName() +
                " WHERE name = ?;";
        try (PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setString(1,  name);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    int idBot = result.getInt("id");
                    String nameBot = result.getString("name");
                    int coins = result.getInt("coins");
                    int kills = result.getInt("kills");
                    int deaths = result.getInt("deaths");
                    return new BotInfo(idBot, nameBot, coins, kills, deaths);
                }
                else {
                    return null;
                }
            }
        }
    }

    public BotInfo findBotInfo(int id) throws SQLException {
        String SQL = "SELECT * FROM " + BotInfo.class.getSimpleName() +
                " WHERE id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setInt(1,  id);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    int idBot = result.getInt("id");
                    String nameBot = result.getString("name");
                    int coins = result.getInt("coins");
                    int kills = result.getInt("kills");
                    int deaths = result.getInt("deaths");
                    return new BotInfo(idBot, nameBot, coins, kills, deaths);
                }
                else {
                    return null;
                }
            }
        }
    }

    public BotInfo createBotInfo(BotInfo botInfo) throws SQLException {
        if(this.findBotInfo(botInfo.getName()) != null)
            return null;
        Field[] fields = BotInfo.class.getDeclaredFields();
        String SQL = "INSERT INTO " + BotInfo.class.getSimpleName() +"(";
        for(int i = 0; i < fields.length - 1; ++i) {
            SQL += fields[i].getName() + ",";
        }
        SQL += fields[fields.length - 1].getName() + ") VALUES(";
        for(int i = 0; i < fields.length - 1; ++i) {
            SQL += "?,";
        }
        SQL += "?);";
        try (PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setInt(1, botInfo.getId());
            statement.setString(2, botInfo.getName());
            statement.setInt(3, botInfo.getCoins());
            statement.setInt(4, botInfo.getKills());
            statement.setInt(5, botInfo.getDeath());
            statement.execute();
        }
        return botInfo;
    }

    public BotInfo updateBotInfo(BotInfo botInfo) throws SQLException {
        if(this.findBotInfo(botInfo.getName()) == null)
            return null;
        Field[] fields = BotInfo.class.getDeclaredFields();
        String SQL = "UPDATE " + BotInfo.class.getSimpleName() +" SET ";
        for(int i = 0; i < fields.length - 1; ++i) {
            SQL += "" + fields[i].getName() + " = ?, ";
        }
        SQL += "" + fields[fields.length - 1].getName() + " = ? WHERE id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setInt(1, botInfo.getId());
            statement.setString(2, botInfo.getName());
            statement.setInt(3, botInfo.getCoins());
            statement.setInt(4, botInfo.getKills());
            statement.setInt(5, botInfo.getDeath());
            statement.setInt(6, botInfo.getId());
            statement.execute();
        }
        return botInfo;
    }

    public int newId() throws SQLException {
        String SQL_MAXN = "SELECT MAX(id)" +
                "FROM " + BotInfo.class.getSimpleName();
        int number = 0;
        try (Statement statement = connection.createStatement()) {
            try (ResultSet result = statement.executeQuery(SQL_MAXN)) {
                if (result.next()) {
                    number = result.getInt("MAX(id)") + 1;
                } else {
                    number = 1;
                }
            }
        }
        return number;
    }
}
