package chess.dao;

import chess.db.DataBaseConnector;
import chess.dto.MovementDto;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class MovementDaoImpl extends DaoTemplate implements MovementDao {

    private static final Long AUTO_INCREMENT_DEFAULT = 0L;

    private final DataBaseConnector connector;

    public MovementDaoImpl(DataBaseConnector connector) {
        this.connector = connector;
    }

    @Override
    protected Connection getConnection() {
        return connector.getConnection();
    }

    @Override
    public Long add(MovementDto movementDto) {
        String query = "INSERT INTO movement VALUES(?, ?, ?)";
        String errorMessage = "움직임 저장 실패";
        executeUpdate(query, errorMessage, AUTO_INCREMENT_DEFAULT, movementDto.pieces(),
            movementDto.gameId());
        return findLastId();
    }

    private Long findLastId() {
        String query = "SELECT MAX(id) lastId FROM movement";
        String errorMessage = "움직임 마지막 id 조회 실패";
        return executeQueryForSingleData(query, errorMessage, this::getLastId)
            .orElse(AUTO_INCREMENT_DEFAULT);
    }

    private Long getLastId(ResultSet resultSet) {
        try {
            return resultSet.getLong("lastId");
        } catch (SQLException e) {
            throw new RuntimeException("움직임 마지막 id 조회 실패");
        }
    }

    @Override
    public Optional<MovementDto> findLatestByGameId(Long gameId) {
        String query = "select * from movement WHERE gameId = ? ORDER BY id DESC LIMIT 1";
        String errorMessage = "가장 최근 움직임 조회 실패";
        return executeQueryForSingleData(query, errorMessage, this::createMovementDto, gameId);
    }

    private MovementDto createMovementDto(ResultSet resultSet) {
        try {
            return new MovementDto(
                resultSet.getLong("id"),
                resultSet.getString("pieces"),
                resultSet.getLong("gameId")
            );
        } catch (SQLException e) {
            throw new RuntimeException("가장 최근 움직임 조회 실패");
        }
    }

    @Override
    public void deleteAllByGameId(Long gameId) {
        String query = "DELETE FROM movement WHERE gameId = ?";
        String errorMessage = "움직임 삭제 실패";
        executeUpdate(query, errorMessage, gameId);
    }
}
