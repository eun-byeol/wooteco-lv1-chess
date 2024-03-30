package chess.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import chess.dto.BoardDto;
import chess.model.board.Board;
import chess.model.board.BoardFactory;
import chess.model.board.InitialBoardFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChessServiceTest {

    private final ChessService chessService = new ChessService();

    @DisplayName("저장된 게임을 로드한다")
    @Test
    void loadGame() {
        BoardFactory boardFactory = new InitialBoardFactory();
        Board board = boardFactory.generate();
        chessService.saveGame(board);
        Board savedBoard = chessService.loadGame();

        BoardDto boardDto = BoardDto.from(board);
        BoardDto savedBoardDto = BoardDto.from(savedBoard);

        assertAll(
            () -> assertThat(savedBoard.turnName())
                .isEqualTo(board.turnName()),
            () -> assertThat(savedBoardDto.getRanks())
                .containsExactlyElementsOf(boardDto.getRanks())
        );
    }
}
