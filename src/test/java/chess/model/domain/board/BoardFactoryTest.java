package chess.model.domain.board;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import chess.model.domain.piece.Bishop;
import chess.model.domain.piece.King;
import chess.model.domain.piece.Knight;
import chess.model.domain.piece.Pawn;
import chess.model.domain.piece.Piece;
import chess.model.domain.piece.Queen;
import chess.model.domain.piece.Rook;
import chess.model.domain.position.Position;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardFactoryTest {

    @Test
    @DisplayName("createInitialBoard() : 체스피스들의 위치를 초기화하는 Board를 생성할 수 있다.")
    void test_createInitialBoard() {
        //given
        final BoardFactory boardFactory = new BoardFactory();

        //when
        final Map<Position, Piece> board = boardFactory
                .createInitialBoard()
                .getBoard();
        final Map<Class<? extends Piece>, Long> result = board.values()
                .stream()
                .collect(groupingBy(Piece::getClass, counting()));

        //then
        assertAll(
                () -> assertEquals(board.size(), 32),
                () -> assertEquals(result.get(King.class), 2),
                () -> assertEquals(result.get(Queen.class), 2),
                () -> assertEquals(result.get(Bishop.class), 4),
                () -> assertEquals(result.get(Knight.class), 4),
                () -> assertEquals(result.get(Rook.class), 4),
                () -> assertEquals(result.get(Pawn.class), 16)
        );
    }
}