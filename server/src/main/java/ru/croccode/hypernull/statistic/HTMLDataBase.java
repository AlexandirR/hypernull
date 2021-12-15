package ru.croccode.hypernull.statistic;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.file.Path;
import java.sql.*;

public class HTMLDataBase {

    private Path path;
    private Connection connection;

    public HTMLDataBase(Path path, Connection connection) {
        this.path = path;
        this.connection = connection;
    }

    public void update() {
        updateBots();
        updateMatchInfo();
    }

    private void updateBots() {
        try (PrintWriter out = new PrintWriter(path.getFileName().toString() + "\\" + "BotInfo.html")) {
            try (Statement statement = connection.createStatement()) {
                try (ResultSet result = statement.executeQuery("SELECT * FROM BotInfo")) {
                    out.println("<P ALIGN='center'><TABLE BORDER=1>");
                    ResultSetMetaData rsmd = result.getMetaData();
                    int columnCount = rsmd.getColumnCount();
                    // table header
                    out.println("<TR>");
                    for (int i = 0; i < columnCount; i++) {
                        out.println("<TH>" + rsmd.getColumnLabel(i + 1) + "</TH>");
                    }
                    out.println("</TR>");
                    // the data
                    while (result.next()) {
                        out.println("<TR>");
                        for (int i = 0; i < columnCount; i++) {
                            out.println("<TD>" + result.getObject(i + 1) + "</TD>");
                        }
                        out.println("</TR>");
                    }
                    out.println("</TABLE></P>");
                }
            }
        } catch (FileNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateMatchInfo() {
        try (PrintWriter out = new PrintWriter(path.getFileName().toString() + "\\" + "MatchInfo.html")) {
            try (Statement statement = connection.createStatement()) {
                try (ResultSet result = statement.executeQuery("SELECT * FROM MatchInfo")) {
                        out.println("<P ALIGN='center'><TABLE BORDER=1>");
                        ResultSetMetaData rsmd = result.getMetaData();
                        int columnCount = rsmd.getColumnCount();
                        // table header
                        out.println("<TR>");
                        for (int i = 0; i < columnCount; i++) {
                            out.println("<TH>" + rsmd.getColumnLabel(i + 1) + "</TH>");
                        }
                        out.println("</TR>");
                        // the data
                        while (result.next()) {
                            out.println("<TR>");
                            for (int i = 0; i < columnCount; i++) {
                                out.println("<TD>" + result.getObject(i + 1) + "</TD>");
                            }
                            out.println("</TR>");
                        }
                        out.println("</TABLE></P>");
                }
                try (ResultSet result = statement.executeQuery("SELECT * FROM MatchTable")) {
                    out.println("<P ALIGN='center'><TABLE BORDER=1>");
                    ResultSetMetaData rsmd = result.getMetaData();
                    int columnCount = rsmd.getColumnCount();
                    // table header
                    out.println("<TR>");
                    for (int i = 0; i < columnCount; i++) {
                        out.println("<TH>" + rsmd.getColumnLabel(i + 1) + "</TH>");
                    }
                    out.println("</TR>");
                    // the data
                    while (result.next()) {
                        out.println("<TR>");
                        for (int i = 0; i < columnCount; i++) {
                            out.println("<TD>" + result.getObject(i + 1) + "</TD>");
                        }
                        out.println("</TR>");
                    }
                    out.println("</TABLE></P>");
                }
            }
        } catch (FileNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
