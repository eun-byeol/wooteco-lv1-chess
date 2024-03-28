package chess.model.game;

import static chess.model.game.Status.MOVE;
import static chess.model.game.Status.START;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import chess.model.board.Board;
import chess.model.board.InitialBoardFactory;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GameStatusTest {

    @DisplayName("ready에서 start를 할 수 있다")
    @Test
    void readyToStart() {
        GameStatus gameStatus = new GameStatus(
            (board) -> System.out.println("executeStart"),
            (commands, board) -> true,
            (board) -> System.out.println("executeStatus")
        );
        Board board = new InitialBoardFactory().generate();
        GameStatus currentStatus = gameStatus.action(List.of("start"), board);
        assertThat(currentStatus.isStarted()).isTrue();
    }

    @DisplayName("start에서 start를 하면 예외가 발생한다")
    @Test
    void startToStart() {
        GameStatus gameStatus = new GameStatus(
            START,
            (board) -> System.out.println("executeStart"),
            (commands, board) -> true,
            (board) -> System.out.println("executeStatus")
        );
        Board board = new InitialBoardFactory().generate();
        assertThatThrownBy(() -> gameStatus.action(List.of("start"), board))
            .isInstanceOf(UnsupportedOperationException.class)
            .hasMessage("게임이 이미 진행 중 입니다.");
    }

    @DisplayName("start에서 move를 할 수 있다")
    @Test
    void startToMove() {
        GameStatus gameStatus = new GameStatus(
            START,
            (board) -> System.out.println("executeStart"),
            (commands, board) -> true,
            (board) -> System.out.println("executeStatus")
        );
        Board board = new InitialBoardFactory().generate();
        GameStatus currentStatus = gameStatus.action(List.of("move", "a2", "a4"), board);
        assertThat(currentStatus.isMoved()).isTrue();
    }

    @DisplayName("move에서 move를 할 수 있다")
    @Test
    void moveToMove() {
        GameStatus gameStatus = new GameStatus(
            MOVE,
            (board) -> System.out.println("executeStart"),
            (commands, board) -> true,
            (board) -> System.out.println("executeStatus")
        );
        Board board = new InitialBoardFactory().generate();
        GameStatus currentStatus = gameStatus.action(List.of("move", "a7", "a5"), board);
        assertThat(currentStatus.isMoved()).isTrue();
    }

    @DisplayName("ready에서 move를 하면 예외가 발생한다")
    @Test
    void readyToMove() {
        GameStatus gameStatus = new GameStatus(
            (board) -> System.out.println("executeStart"),
            (commands, board) -> true,
            (board) -> System.out.println("executeStatus")
        );
        Board board = new InitialBoardFactory().generate();
        assertThatThrownBy(() -> gameStatus.action(List.of("move", "a2", "a4"), board))
            .isInstanceOf(UnsupportedOperationException.class)
            .hasMessage("게임을 start 해 주세요.");
    }

    @DisplayName("ready에서 finish를 할 수 있다")
    @Test
    void readyToFinish() {
        GameStatus gameStatus = new GameStatus(
            (board) -> System.out.println("executeStart"),
            (commands, board) -> true,
            (board) -> System.out.println("executeStatus")
        );
        Board board = new InitialBoardFactory().generate();
        GameStatus currentStatus = gameStatus.action(List.of("end"), board);
        assertThat(currentStatus.isRunning()).isFalse();
    }

    @DisplayName("start에서 finish를 할 수 있다")
    @Test
    void startToFinish() {
        GameStatus gameStatus = new GameStatus(
            START,
            (board) -> System.out.println("executeStart"),
            (commands, board) -> true,
            (board) -> System.out.println("executeStatus")
        );
        Board board = new InitialBoardFactory().generate();
        GameStatus currentStatus = gameStatus.action(List.of("end"), board);
        assertThat(currentStatus.isRunning()).isFalse();
    }

    @DisplayName("move에서 finish를 할 수 있다")
    @Test
    void moveToFinish() {
        GameStatus gameStatus = new GameStatus(
            MOVE,
            (board) -> System.out.println("executeStart"),
            (commands, board) -> true,
            (board) -> System.out.println("executeStatus")
        );
        Board board = new InitialBoardFactory().generate();
        GameStatus currentStatus = gameStatus.action(List.of("end"), board);
        assertThat(currentStatus.isRunning()).isFalse();
    }

    @DisplayName("move에서 결과값이 false이면 end 상태로 바뀐다")
    @Test
    void moveToEndStatus() {
        GameStatus gameStatus = new GameStatus(
            MOVE,
            (board) -> System.out.println("executeStart"),
            (commands, board) -> false,
            (board) -> System.out.println("executeStatus")
        );
        Board board = new InitialBoardFactory().generate();
        GameStatus currentStatus = gameStatus.action(List.of("end"), board);
        assertThat(currentStatus.isRunning()).isFalse();
    }
}
