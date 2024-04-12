package chess.db;

import java.sql.Connection;

public interface DataBaseConnector {

    String SERVER = "localhost:13306";
    String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    String USERNAME = "root";
    String PASSWORD = "root";

    Connection getConnection();
}
