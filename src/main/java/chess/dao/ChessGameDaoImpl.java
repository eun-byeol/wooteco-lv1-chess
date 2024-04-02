package chess.dao;

import chess.db.DataBaseConnector;
import chess.dto.ChessGameDto;
import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class ChessGameDaoImpl extends DaoTemplate implements ChessGameDao {

    private static final Long AUTO_INCREMENT_DEFAULT = 0L;
    private static final String TABLE_NAME = "chessgame";

    private final DataBaseConnector connector;

    public ChessGameDaoImpl(DataBaseConnector connector) {
        this.connector = connector;
    }

    @Override
    protected Connection getConnection() {
        return connector.getConnection();
    }

    @Override
    public Long add(ChessGameDto chessGameDto) {
        String query = "INSERT INTO " + TABLE_NAME + " VALUES(?, ?)";
        String errorMessage = "체스 게임 저장 실패";
        executeUpdate(query, errorMessage, AUTO_INCREMENT_DEFAULT, chessGameDto.turn());
        return findLastId();
    }

    private Long findLastId() {
        String query = "SELECT MAX(id) lastId FROM " + TABLE_NAME;
        String errorMessage = "체스 게임 마지막 id 조회 실패";
        return executeQueryForSingleData(
            query,
            errorMessage,
            resultSet -> resultSet.getLong("lastId")
        ).orElse(AUTO_INCREMENT_DEFAULT);
    }

    @Override
    public Optional<ChessGameDto> findById(Long id) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";
        String errorMessage = "체스 게임 조회 실패";
        return executeQueryForSingleData(
            query,
            errorMessage,
            resultSet -> new ChessGameDto(
                resultSet.getLong("id"),
                resultSet.getString("turn")
            ),
            id
        );
    }

    @Override
    public List<ChessGameDto> findAll() {
        String query = "SELECT * FROM " + TABLE_NAME;
        String errorMessage = "체스 게임 전체 조회 실패";
        return executeQueryForMultiData(
            query,
            errorMessage,
            resultSet -> new ChessGameDto(
                resultSet.getLong("id"),
                resultSet.getString("turn")
            )
        );
    }

    @Override
    public void update(ChessGameDto chessGameDto) {
        String query = "UPDATE " + TABLE_NAME + " SET turn = ? WHERE id = ?";
        String errorMessage = "체스 게임 수정 실패";
        executeUpdate(query, errorMessage, chessGameDto.turn(), chessGameDto.id());
    }

    @Override
    public void delete(Long id) {
        String query = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";
        String errorMessage = "체스 게임 삭제 실패";
        executeUpdate(query, errorMessage, id);
    }
}
