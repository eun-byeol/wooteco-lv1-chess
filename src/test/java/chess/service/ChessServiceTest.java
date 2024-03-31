package chess.service;

import static chess.model.Fixtures.A2;
import static chess.model.Fixtures.A4;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import chess.dao.ChessGameDao;
import chess.dto.BoardDto;
import chess.dto.ChessGameDto;
import chess.model.board.Board;
import chess.model.board.BoardFactory;
import chess.model.board.CustomBoardFactory;
import chess.model.board.InitialBoardFactory;
import chess.model.material.Color;
import chess.util.DataBaseConnector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChessServiceTest {

    private final ChessService chessService = new ChessService();

    @BeforeEach
    void initializeDataBase() {
        String query = "TRUNCATE TABLE chessgame";
        try (Connection connection = new DataBaseConnector().getConnection();
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
            Color.BLACK,
            0L
        );
        Board board = boardFactory.generate();
        Long id = chessService.saveGame(board);
        Board savedBoard = chessService.loadGame();
        assertThat(savedBoard.getId()).isEqualTo(id);
    }

    @DisplayName("저장된 게임이 없으면 생성하여 로드한다")
    @Test
    void loadGameWithInitialGame() {
        boolean isGameSaved = chessService.isGameSaved();
        Board actualBoard = chessService.loadGame();
        Board expectedBoard = new InitialBoardFactory().generate();

        BoardDto actualBoardDto = BoardDto.from(actualBoard);
        BoardDto expectedBoardDto = BoardDto.from(expectedBoard);

        assertAll(
            () -> assertThat(isGameSaved).isFalse(),
            () -> assertThat(actualBoardDto.getRanks()).isEqualTo(expectedBoardDto.getRanks())
        );
    }

    @DisplayName("저장된 게임을 삭제한다")
    @Test
    void deleteGame() {
        BoardFactory boardFactory = new InitialBoardFactory();
        Board board = boardFactory.generate();
        Long id = chessService.saveGame(board);

        chessService.deleteGame(id);

        ChessGameDao chessGameDao = new ChessGameDao(new DataBaseConnector());
        assertThat(chessGameDao.findById(id)).isEmpty();
    }

    @DisplayName("저장된 게임을 수정한다")
    @Test
    void updateGame() {
        BoardFactory boardFactory = new InitialBoardFactory();
        Board board = boardFactory.generate();
        Long id = chessService.saveGame(board);
        board = board.setId(id);
        board.move(A2, A4);
        chessService.updateGame(board);
        ChessGameDto expectedGameDto = ChessGameDto.from(board);

        ChessGameDao chessGameDao = new ChessGameDao(new DataBaseConnector());
        assertThat(chessGameDao.findById(id))
            .contains(expectedGameDto);
    }
}
