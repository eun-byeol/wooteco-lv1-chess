package chess.dao;

import static org.assertj.core.api.Assertions.assertThat;

import chess.TestConnector;
import chess.db.DataBaseConnector;
import chess.dto.ChessGameDto;
import chess.model.material.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChessGameDaoImplTest {

    private final DataBaseConnector connector = new TestConnector();
    private final ChessGameDao chessGameDao = new ChessGameDaoImpl(connector);

    @BeforeEach
    void initializeDataBase() {
        String query = "TRUNCATE TABLE chessgame";
        try (Connection connection = connector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("DB 초기화 실패");
        }
    }

    @DisplayName("체스 게임 저장 성공")
    @Test
    void addChessGame() {
        ChessGameDto chessGameDto = new ChessGameDto(0L, Color.WHITE.name());
        Long id = chessGameDao.add(chessGameDto);
        assertThat(chessGameDao.findById(id)).isPresent();
    }

    @DisplayName("체스 게임 전체 조회 성공")
    @Test
    void findAllChessGame() {
        ChessGameDto firstGame = new ChessGameDto(0L, Color.WHITE.name());
        ChessGameDto secondGame = new ChessGameDto(0L, Color.BLACK.name());
        Long firstGameId = chessGameDao.add(firstGame);
        Long secondGameId = chessGameDao.add(secondGame);

        List<ChessGameDto> chessGameDtos = chessGameDao.findAll();

        assertThat(chessGameDtos.stream().map(ChessGameDto::id))
            .contains(firstGameId)
            .contains(secondGameId);
    }

    @DisplayName("체스 게임 수정 성공")
    @Test
    void updateChessGame() {
        ChessGameDto before = new ChessGameDto(0L, Color.WHITE.name());
        Long id = chessGameDao.add(before);

        ChessGameDto after = new ChessGameDto(id, Color.BLACK.name());

        chessGameDao.update(after);

        assertThat(chessGameDao.findById(id))
            .contains(after);
    }
}
