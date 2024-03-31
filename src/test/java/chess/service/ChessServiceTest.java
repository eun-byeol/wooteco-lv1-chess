package chess.service;

import static chess.model.Fixtures.A2;
import static chess.model.Fixtures.A4;
import static org.assertj.core.api.Assertions.assertThat;

import chess.TestConnector;
import chess.dao.ChessGameDao;
import chess.db.DataBaseConnector;
import chess.dto.ChessGameDto;
import chess.model.board.Board;
import chess.model.board.BoardFactory;
import chess.model.board.CustomBoardFactory;
import chess.model.board.InitialBoardFactory;
import chess.model.material.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChessServiceTest {

    private final DataBaseConnector connector = new TestConnector();
    private final ChessGameDao chessGameDao = new ChessGameDao(connector);
    private final ChessService chessService = new ChessService(chessGameDao);

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
        ChessGameDto chessGameDto = ChessGameDto.from(savedBoard);
        assertThat(chessGameDao.findById(chessGameDto.id())).isPresent();
    }

    @DisplayName("저장된 게임을 삭제한다")
    @Test
    void deleteGame() {
        BoardFactory boardFactory = new InitialBoardFactory();
        Board savedBoard = chessService.saveGame(boardFactory.generate());
        ChessGameDto chessGameDto = ChessGameDto.from(savedBoard);

        chessService.deleteGame(chessGameDto.id());

        assertThat(chessGameDao.findById(chessGameDto.id())).isEmpty();
    }

    @DisplayName("저장된 게임을 수정한다")
    @Test
    void updateGame() {
        BoardFactory boardFactory = new InitialBoardFactory();
        Board board = chessService.saveGame(boardFactory.generate());

        board.move(A2, A4);
        chessService.updateGame(board);
        ChessGameDto expectedGameDto = ChessGameDto.from(board);

        assertThat(chessGameDao.findById(board.getId()))
            .contains(expectedGameDto);
    }
}
