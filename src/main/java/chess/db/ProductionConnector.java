package chess.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ProductionConnector implements DataBaseConnector {

    private static final String DATABASE = "chess";
    private static final String URL = "jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION;

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (final SQLException e) {
            System.err.println("DB 연결 오류:" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
