package chess.dao;

import chess.db.DataBaseConnector;
import chess.dto.MovementDto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class MovementDaoImpl implements MovementDao {

    private static final Long AUTO_INCREMENT_DEFAULT = 0L;

    private final DataBaseConnector connector;

    public MovementDaoImpl(DataBaseConnector connector) {
        this.connector = connector;
    }

    @Override
    public Long add(MovementDto movementDto) {
        String query = "INSERT INTO movement VALUES(?, ?, ?)";
        try (Connection connection = connector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, AUTO_INCREMENT_DEFAULT);
            preparedStatement.setString(2, movementDto.pieces());
            preparedStatement.setLong(3, movementDto.gameId());
            preparedStatement.executeUpdate();
            return findLastId();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("움직임 저장 실패");
        }
    }

    private Long findLastId() {
        String query = "SELECT MAX(id) lastId FROM movement";
        try (Connection connection = connector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            return getLastIdFrom(resultSet);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("움직임 마지막 id 조회 실패");
        }
    }

    private long getLastIdFrom(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            return resultSet.getLong("lastId");
        }
        return AUTO_INCREMENT_DEFAULT;
    }

    @Override
    public Optional<MovementDto> findLatestByGameId(Long gameId) {
        return Optional.empty();
    }

    @Override
    public List<MovementDto> findAllSortedByTime() {
        return null;
    }

    @Override
    public void delete(Long id) {
    }
}
