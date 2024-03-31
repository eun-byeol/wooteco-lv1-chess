package chess;

import chess.db.DataBaseConnector;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class TestConnector implements DataBaseConnector {

    private static final String DATABASE = "chess_test";

    public Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION,
                USERNAME, PASSWORD);
        } catch (final SQLException e) {
            System.err.println("DB 연결 오류:" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
