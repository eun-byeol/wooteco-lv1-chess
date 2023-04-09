package domain.board;

import dao.Movement;
import domain.Board;
import domain.Turn;
import domain.piece.*;
import domain.piece.Pawn;
import domain.point.Point;
import exception.CheckMateException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import util.ExceptionMessages;

import java.util.List;
import java.util.Map;

import static domain.point.File.A;
import static domain.point.Rank.THREE;
import static domain.point.Rank.TWO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class BoardTest {
    @Test
    @DisplayName("체스판의 현재 상태를 조회할 수 있다.")
    void initialize() {
        // given
        Board board = new Board();

        // when
        List<List<Piece>> status = board.findCurrentStatus();

        // then
        assertThat(status).hasSize(8);
        assertStatusOfPieces(status);
    }

    private void assertStatusOfPieces(List<List<Piece>> status) {
        assertFirstWhiteRank(status.get(0));
        assertSecondWhiteRank(status.get(1));
        assertEmptyRanks(status);
        assertSecondBlackRank(status.get(6));
        assertFirstBlackRank(status.get(7));
    }

    private void assertEmptyRanks(List<List<Piece>> status) {
        for (int rankIndex = 2; rankIndex < 5; rankIndex++) {
            assertEmptyRank(status.get(rankIndex));
        }
    }

    private void assertEmptyRank(List<Piece> pieces) {
        assertAll(
                () -> assertThat(pieces).hasSize(8),
                () -> assertThat(pieces).containsOnly(new Empty())
        );
    }

    private void assertFirstWhiteRank(List<Piece> pieces) {
        assertAll(
                () -> assertThat(pieces).hasSize(8),
                () -> assertThat(pieces.get(0)).isEqualTo(new Rook(Turn.WHITE)),
                () -> assertThat(pieces.get(1)).isEqualTo(new Knight(Turn.WHITE)),
                () -> assertThat(pieces.get(2)).isEqualTo(new Bishop(Turn.WHITE)),
                () -> assertThat(pieces.get(3)).isEqualTo(new Queen(Turn.WHITE)),
                () -> assertThat(pieces.get(4)).isEqualTo(new King(Turn.WHITE)),
                () -> assertThat(pieces.get(5)).isEqualTo(new Bishop(Turn.WHITE)),
                () -> assertThat(pieces.get(6)).isEqualTo(new Knight(Turn.WHITE)),
                () -> assertThat(pieces.get(7)).isEqualTo(new Rook(Turn.WHITE))
        );
    }

    private void assertSecondWhiteRank(List<Piece> pieces) {
        assertAll(
                () -> assertThat(pieces).hasSize(8),
                () -> assertThat(pieces).containsOnly(new Pawn(Turn.WHITE))
        );
    }

    private void assertFirstBlackRank(List<Piece> pieces) {
        assertAll(
                () -> assertThat(pieces).hasSize(8),
                () -> assertThat(pieces.get(0)).isEqualTo(new Rook(Turn.BLACK)),
                () -> assertThat(pieces.get(1)).isEqualTo(new Knight(Turn.BLACK)),
                () -> assertThat(pieces.get(2)).isEqualTo(new Bishop(Turn.BLACK)),
                () -> assertThat(pieces.get(3)).isEqualTo(new Queen(Turn.BLACK)),
                () -> assertThat(pieces.get(4)).isEqualTo(new King(Turn.BLACK)),
                () -> assertThat(pieces.get(5)).isEqualTo(new Bishop(Turn.BLACK)),
                () -> assertThat(pieces.get(6)).isEqualTo(new Knight(Turn.BLACK)),
                () -> assertThat(pieces.get(7)).isEqualTo(new Rook(Turn.BLACK))
        );
    }

    private void assertSecondBlackRank(List<Piece> pieces) {
        assertThat(pieces).containsOnly(new Pawn(Turn.BLACK));
    }

    @Nested
    @DisplayName("장기말 이동과 관련한 메서드 테스트")
    class moveTest {
        @Test
        @DisplayName("출발 좌표에 아무 장기말이 없으면 예외가 발생한다.")
        void moveFromEmptyPoint() {
            // given
            Board board = Textures.makeBoard(Map.of(
                    new Point(A, TWO), new Pawn(Turn.BLACK)
            ));

            // when & then
            assertThatThrownBy(() -> board.move(new Movement(Point.fromSymbol("a1"), Point.fromSymbol("a3")), Turn.BLACK))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ExceptionMessages.TARGET_PIECE_NOT_FOUND);
        }

        @Test
        @DisplayName("주어진 진영의 차례에 알맞지 않은 기물의 이동이 일어나면 예외가 발생한다.")
        void invalidTurn() {
            // given
            Board board = Textures.makeBoard(Map.of(
                    new Point(A, THREE), new Pawn(Turn.BLACK),
                    new Point(A, TWO), new Empty()
            ));

            // when & then
            assertThatThrownBy(() -> board.move(new Movement(Point.fromSymbol("a3"), Point.fromSymbol("a2")), Turn.WHITE))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ExceptionMessages.INVALID_GAME_TURN);
        }

        @Test
        @DisplayName("King을 잡으면 체크메이트 되었다는 의미의 예외가 발생한다.")
        void checkMate() {
            // given
            Board board = Textures.makeBoard(Map.of(
                    new Point(A, THREE), new Rook(Turn.BLACK),
                    new Point(A, TWO), new King(Turn.WHITE)
            ));

            // when & then
            assertThatThrownBy(() -> board.move(new Movement(Point.fromSymbol("a3"), Point.fromSymbol("a2")), Turn.BLACK))
                    .isEqualTo(new CheckMateException(Turn.BLACK));
        }
    }
}