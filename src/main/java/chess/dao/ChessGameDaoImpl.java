package chess.dao;

import chess.db.DataBaseConnector;
import chess.dto.ChessGameDto;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ChessGameDaoImpl extends DaoTemplate implements ChessGameDao {

    private static final Long AUTO_INCREMENT_DEFAULT = 0L;

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
        String query = "INSERT INTO chessgame VALUES(?, ?)";
        String errorMessage = "체스 게임 저장 실패";
        executeUpdate(query, errorMessage, AUTO_INCREMENT_DEFAULT, chessGameDto.turn());
        return findLastId();
    }

    private Long findLastId() {
        String query = "SELECT MAX(id) lastId FROM chessgame";
        String errorMessage = "체스 게임 마지막 id 조회 실패";
        return executeQueryForSingleData(query, errorMessage, this::getLastId)
            .orElse(AUTO_INCREMENT_DEFAULT);
    }

    private Long getLastId(ResultSet resultSet) {
        try {
            return resultSet.getLong("lastId");
        } catch (SQLException e) {
            throw new RuntimeException("체스 게임 마지막 id 조회 실패");
        }
    }

    @Override
    public Optional<ChessGameDto> findById(Long id) {
        String query = "SELECT * FROM chessgame WHERE id = ?";
        String errorMessage = "체스 게임 조회 실패";
        return executeQueryForSingleData(query, errorMessage, this::createChessGameDto, id);
    }

    private ChessGameDto createChessGameDto(ResultSet resultSet) {
        try {
            return new ChessGameDto(
                resultSet.getLong("id"),
                resultSet.getString("turn")
            );
        } catch (SQLException e) {
            throw new RuntimeException("체스 게임 조회 실패");
        }
    }

    @Override
    public List<ChessGameDto> findAll() {
        String query = "SELECT * FROM chessgame";
        String errorMessage = "체스 게임 전체 조회 실패";
        return executeQueryForMultiData(query, errorMessage, this::createChessGameDto);
    }

    @Override
    public void update(ChessGameDto chessGameDto) {
        String query = "UPDATE chessgame SET turn = ? WHERE id = ?";
        String errorMessage = "체스 게임 수정 실패";
        executeUpdate(query, errorMessage, chessGameDto.turn(), chessGameDto.id());
    }

    @Override
    public void delete(Long id) {
        String query = "DELETE FROM chessgame WHERE id = ?";
        String errorMessage = "체스 게임 삭제 실패";
        executeUpdate(query, errorMessage, id);
    }
}
