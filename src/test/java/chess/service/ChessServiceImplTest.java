package chess.service;

import static chess.model.Fixtures.A2;
import static chess.model.Fixtures.A4;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import chess.TestConnector;
import chess.dao.ChessGameDao;
import chess.dao.ChessGameDaoImpl;
import chess.dao.MovementDao;
import chess.dao.MovementDaoImpl;
import chess.db.DataBaseConnector;
import chess.dto.ChessGameDto;
import chess.dto.MovementDto;
import chess.model.board.Board;
import chess.model.board.BoardFactory;
import chess.model.board.CustomBoardFactory;
import chess.model.board.InitialBoardFactory;
import chess.model.material.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChessServiceImplTest {

    private final DataBaseConnector connector = new TestConnector();
    private final ChessGameDao chessGameDao = new ChessGameDaoImpl(connector);
    private final MovementDao movementDao = new MovementDaoImpl(connector);
    private final ChessService chessService = new ChessServiceImpl(chessGameDao, movementDao);

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

    @DisplayName("저장된 게임이 있으면 로드한다")
    @Test
    void loadGameWithSavedGame() {
        BoardFactory boardFactory = new CustomBoardFactory(
            List.of(
                "........",
                "K...P...",
                "PP......",
                "........",
                "...p....",
                ".pn.....",
                ".p.q....",
                "....k..."
            ),
            0L,
            Color.BLACK
        );
        Board expectedBoard = chessService.saveGame(boardFactory.generate());
        Board actualBoard = chessService.loadGame();
        assertThat(actualBoard.getId()).isEqualTo(expectedBoard.getId());
    }

    @DisplayName("게임을 저장한다")
    @Test
    void saveGame() {
        BoardFactory boardFactory = new InitialBoardFactory();
        Board savedBoard = chessService.saveGame(boardFactory.generate());
        Long gameId = savedBoard.getId();

        assertAll(
            () -> assertThat(chessGameDao.findById(gameId)).isPresent(),
            () -> assertThat(movementDao.findLatestByGameId(gameId)).isPresent()
        );
    }

    @DisplayName("저장된 게임을 삭제한다")
    @Test
    void deleteGame() {
        BoardFactory boardFactory = new InitialBoardFactory();
        Board savedBoard = chessService.saveGame(boardFactory.generate());
        Long gameId = savedBoard.getId();

        chessService.deleteGame(gameId);

        assertAll(
            () -> assertThat(chessGameDao.findById(gameId)).isEmpty(),
            () -> assertThat(movementDao.findLatestByGameId(gameId)).isEmpty()
        );
    }

    @DisplayName("저장된 게임을 수정한다")
    @Test
    void updateGame() {
        BoardFactory boardFactory = new InitialBoardFactory();
        Board board = chessService.saveGame(boardFactory.generate());
        Long gameId = board.getId();

        board.move(A2, A4);
        chessService.updateGame(board);

        ChessGameDto expectedGameDto = ChessGameDto.from(board);
        MovementDto expectedMovementDto = MovementDto.from(board);

        Optional<ChessGameDto> actualGameDto = chessGameDao.findById(gameId);
        Optional<MovementDto> actualMovementDto = movementDao.findLatestByGameId(gameId);

        assertAll(
            () -> assertThat(actualGameDto).contains(expectedGameDto),
            () -> assertThat(actualMovementDto).contains(expectedMovementDto)
        );
    }
}
