package chess.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.SQLException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DataBaseConnectorTest {

    DataBaseConnector connector = new DataBaseConnector();

    @DisplayName("DB 접속 테스트")
    @Test
    void connection() throws SQLException {
        try (Connection connection = connector.getConnection()) {
            assertThat(connection).isNotNull();
        }
    }
}
