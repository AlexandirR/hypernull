package ru.croccode.hypernull.statistic;

import ru.croccode.hypernull.statistic.DAO.DAOBotInfo;
import ru.croccode.hypernull.statistic.DAO.DAOMatchInfo;
import ru.croccode.hypernull.statistic.DAO.DAOMatchTable;
import ru.croccode.hypernull.statistic.entities.BotInfo;
import ru.croccode.hypernull.statistic.entities.MatchInfo;
import ru.croccode.hypernull.statistic.entities.MatchTable;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class ParseLogs {

    private Path path;
    private List<MatchTable> bots = new ArrayList<>();
    private Map<Integer, Set<String>> botsFields = new HashMap<>();
    private MatchInfo matchInfo;
    DAOMatchInfo daoMatchInfo;
    DAOBotInfo daoBotInfo;
    DAOMatchTable daoMatchTable;

    public ParseLogs(Path path) {
        this.path = path;
    }

    private void parseIter(String line) throws SQLException {
        String[] words = line.split(" ");
        switch (words[0]) {
            case "num_bots":
                bots = new ArrayList<>(Integer.parseInt(words[1]));
                for(Integer id = 0; id < Integer.parseInt(words[1]); ++id) {
                    botsFields.put(id, new HashSet<>());
                }
                break;
            case "mode":
                matchInfo.setMode(words[1]);
                break;
            case "bot_name":
                BotInfo botInfo = daoBotInfo.findBotInfo(words[2]);
                if(botInfo == null) {
                    botInfo = daoBotInfo.createBotInfo(new BotInfo(daoBotInfo.newId(), words[2], 0, 0, 0));
                }
                if(bots.size() <= Integer.parseInt(words[1])) {
                    bots.add(Integer.parseInt(words[1]), new MatchTable(matchInfo.getId(), botInfo.getId(), 0, 0, 0, 0));
                }
                else {
                    bots.set(Integer.parseInt(words[1]), new MatchTable(matchInfo.getId(), botInfo.getId(), 0, 0, 0, 0));
                }
                break;
            case "coin":
                matchInfo.setCoinsSpawn(matchInfo.getCoinsSpawn() + 1);
                break;
            case "bot":
                botsFields.get(Integer.parseInt(words[1])).add(words[2] + words[3]);
                break;
            case "coin_collected":
                bots.get(Integer.parseInt(words[3]))
                        .setCoins(bots.get(Integer.parseInt(words[3]))
                                .getCoins() + 1);
                botInfo = daoBotInfo.findBotInfo(bots.get(Integer.parseInt(words[3])).getIdBot());
                botInfo.setCoins(botInfo.getCoins() + 1);
                daoBotInfo.updateBotInfo(botInfo);
                break;
            case "attack":
                matchInfo.setKills(matchInfo.getKills() + 1);
                MatchTable attackBot =  bots.get(Integer.parseInt(words[1]));
                MatchTable defBot =  bots.get(Integer.parseInt(words[2]));
                botInfo = daoBotInfo.findBotInfo(attackBot.getIdBot());
                botInfo.setCoins(botInfo.getCoins() + attackBot.getCoins() + defBot.getCoins());
                botInfo.setKills(botInfo.getKills() + 1);
                daoBotInfo.updateBotInfo(botInfo);
                attackBot.setCoins(attackBot.getCoins() + defBot.getCoins());
                attackBot.setKills(attackBot.getKills() + 1);
                botInfo = daoBotInfo.findBotInfo(defBot.getIdBot());
                botInfo.setDeath(botInfo.getDeath() + 1);
                daoBotInfo.updateBotInfo(botInfo);
                defBot.setDeaths(defBot.getDeaths() + 1);
                bots.set(Integer.parseInt(words[1]), attackBot);
                bots.set(Integer.parseInt(words[2]), defBot);
                break;
        }
    }

    public void parse(Connection connection) throws IOException {
        List<Path> logs = Files.list(path).collect(Collectors.toList());
        daoMatchInfo = new DAOMatchInfo(connection);
        daoBotInfo = new DAOBotInfo(connection);
        daoMatchTable = new DAOMatchTable(connection);
        for(Path pathIt : logs) {
            try(BufferedReader reader = new BufferedReader(
                    new FileReader(pathIt.getParent().toString() + "\\" + pathIt.getFileName().toString()))) {
                String line = reader.readLine();
                line = reader.readLine();
                String matchId = line.split(" ")[1];
                if(daoMatchInfo.findMatchInfo(matchId) != null) {
                    continue;
                }
                line = reader.readLine();
                MatchInfo matchInfo = new MatchInfo(matchId, 0, "", 0);
                while (line != null) {
                    parseIter(line);
                    line = reader.readLine();
                }
                daoMatchInfo.createMatchInfo(matchInfo);
                for(Map.Entry<Integer, Set<String>> entry: botsFields.entrySet()) {
                    MatchTable matchTable = bots.get(entry.getKey());
                    matchTable.setFieldsOpen(entry.getValue().size());
                }
                for (MatchTable matchTable : bots) {
                    daoMatchTable.createMatchTable(matchTable);
                }
            } catch (SQLException ex) {
                throw new RuntimeException(
                        "Ошибка работы с бд " + ex.getMessage(),
                        ex
                );
            }
        }
    }
}
