package ru.croccode.hypernull.statistic.DAO;

import ru.croccode.hypernull.statistic.entities.MatchTable;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAOMatchTable {

    private Connection connection;

    public DAOMatchTable(Connection connection) {
        this.connection = connection;
    }

    public MatchTable findMatchTable(String idMatch, int idBot) throws SQLException {
        String SQL = "SELECT * FROM " + MatchTable.class.getSimpleName() +
                " WHERE idMatch = ? AND idBot = ?;";
        try (PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setString(1,  idMatch);
            statement.setInt(2,  idBot);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    String idMatchTable = result.getString("idMatch");
                    int idBotTable = result.getInt("idBot");
                    int coins = result.getInt("coins");
                    int kills = result.getInt("kills");
                    int deaths = result.getInt("deaths");
                    int fieldsOpen = result.getInt("fieldsOpen");
                    return new MatchTable(idMatchTable, idBotTable, coins, kills, deaths, fieldsOpen);
                }
                else {
                    return null;
                }
            }
        }
    }

    public MatchTable createMatchTable(MatchTable matchTable) throws SQLException {
        if(this.findMatchTable(matchTable.getIdMatch(), matchTable.getIdBot()) != null)
            return null;
        Field[] fields = MatchTable.class.getDeclaredFields();
        String SQL = "INSERT INTO " + MatchTable.class.getSimpleName() +"(";
        for(int i = 0; i < fields.length - 1; ++i) {
            SQL += fields[i].getName() + ",";
        }
        SQL += fields[fields.length - 1].getName() + ") VALUES(";
        for(int i = 0; i < fields.length - 1; ++i) {
            SQL += "?,";
        }
        SQL += "?);";
                //"('" + item.getArticle() + "', '" + item.getName() + "', " + item.getPrice() + ");";
        try (PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setString(1, matchTable.getIdMatch());
            statement.setInt(2, matchTable.getIdBot());
            statement.setInt(3, matchTable.getCoins());
            statement.setInt(4, matchTable.getKills());
            statement.setInt(5, matchTable.getDeaths());
            statement.setInt(6, matchTable.getFieldsOpen());
            statement.execute();
        }
        return matchTable;
    }
}
