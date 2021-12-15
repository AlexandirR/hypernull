package ru.croccode.hypernull.statistic.DAO;

import ru.croccode.hypernull.statistic.entities.MatchInfo;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAOMatchInfo {
    private Connection connection;

    public DAOMatchInfo(Connection connection) {
        this.connection = connection;
    }

    public MatchInfo findMatchInfo(String idMatch) throws SQLException {
        String SQL = "SELECT * FROM " + MatchInfo.class.getSimpleName() +
                " WHERE id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setString(1,  idMatch);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    String idMatchInfo = result.getString("id");
                    int coins = result.getInt("coinsSpawn");
                    String mode = result.getString("mode");
                    int kills = result.getInt("kills");
                    return new MatchInfo(idMatchInfo, coins, mode, kills);
                }
                else {
                    return null;
                }
            }
        }
    }

    public MatchInfo createMatchInfo(MatchInfo matchInfo) throws SQLException {
        if(this.findMatchInfo(matchInfo.getId()) != null)
            return null;
        Field[] fields = MatchInfo.class.getDeclaredFields();
        String SQL = "INSERT INTO " + MatchInfo.class.getSimpleName() +"(";
        for(int i = 0; i < fields.length - 1; ++i) {
            SQL += fields[i].getName() + ",";
        }
        SQL += fields[fields.length - 1].getName() + ") VALUES(";
        for(int i = 0; i < fields.length - 1; ++i) {
            SQL += "?,";
        }
        SQL += "?);";
        try (PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setString(1, matchInfo.getId());
            statement.setInt(2, matchInfo.getCoinsSpawn());
            statement.setString(3,matchInfo.getMode());
            statement.setInt(4, matchInfo.getKills());
            statement.execute();
        }
        return matchInfo;
    }
}
