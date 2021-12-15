package ru.croccode.hypernull.statistic;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDataBase {

    static final String JDBC_DRIVER = "org.h2.Driver";

    static final String USER = "hypernull";
    static final String PASSWORD = "hypernull";

    private Connection connection;
    private ParseLogs parseLogs;
    private HTMLDataBase htmlDataBase;

    public ConnectionDataBase(Path logs, String URL, String htmlPath) throws ClassNotFoundException, IOException {
        Class.forName(JDBC_DRIVER);
        try {
        connection = DriverManager.getConnection(URL, USER, PASSWORD);
        parseLogs = new ParseLogs(logs);
        htmlDataBase = new HTMLDataBase(Paths.get(htmlPath), connection);
        htmlDataBase.update();
        } catch (SQLException ex) {
            throw new RuntimeException(
                    "Ошибка подключения к базе данных: " + ex.getMessage(),
                    ex
            );
        }
    }
    public void run() throws IOException {
        parseLogs.parse(connection);
        htmlDataBase.update();
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
