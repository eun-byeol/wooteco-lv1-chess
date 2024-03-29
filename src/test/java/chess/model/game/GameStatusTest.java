package chess.model.game;

import static chess.model.game.Status.RUNNING;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import chess.model.board.Board;
import chess.model.board.InitialBoardFactory;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GameStatusTest {

    @DisplayName("READY 상태에서 start 명령어를 실행하면 RUNNING 상태로 바뀐다")
    @Test
    void changeReadyToRunning() {
        GameStatus gameStatus = new GameStatus(
            (board) -> System.out.println("executeStart"),
            (commands, board) -> false,
            (board) -> System.out.println("executeStatus"),
            (board) -> System.out.println("executeEnd")
        );
        Board board = new InitialBoardFactory().generate();
        GameStatus currentStatus = gameStatus.action(List.of("start"), board);
        assertThat(currentStatus.isRunning()).isTrue();
    }

    @DisplayName("READY 상태에서 status 명령어를 실행하면 예외가 발생한다")
    @Test
    void inValidCommandInReadyStatus() {
        GameStatus gameStatus = new GameStatus(
            (board) -> System.out.println("executeStart"),
            (commands, board) -> false,
            (board) -> System.out.println("executeStatus"),
            (board) -> System.out.println("executeEnd")
        );
        Board board = new InitialBoardFactory().generate();
        assertThatThrownBy(() -> gameStatus.action(List.of("status"), board))
            .isInstanceOf(UnsupportedOperationException.class)
            .hasMessage("게임을 start 해 주세요.");
    }

    @DisplayName("RUNNING 상태에서 start 명령어를 실행하면 예외가 발생한다")
    @Test
    void inValidCommandInRunningStatus() {
        GameStatus gameStatus = new GameStatus(
            RUNNING,
            (board) -> System.out.println("executeStart"),
            (commands, board) -> false,
            (board) -> System.out.println("executeStatus"),
            (board) -> System.out.println("executeEnd")
        );
        Board board = new InitialBoardFactory().generate();
        assertThatThrownBy(() -> gameStatus.action(List.of("start"), board))
            .isInstanceOf(UnsupportedOperationException.class)
            .hasMessage("게임이 이미 진행 중 입니다.");
    }

    @DisplayName("RUNNING 상태에서 move 명령어를 실행할 수 있다")
    @Test
    void moveCommandInRunningStatus() {
        GameStatus gameStatus = new GameStatus(
            RUNNING,
            (board) -> System.out.println("executeStart"),
            (commands, board) -> false,
            (board) -> System.out.println("executeStatus"),
            (board) -> System.out.println("executeEnd")
        );
        Board board = new InitialBoardFactory().generate();
        assertThatCode(() -> gameStatus.action(List.of("move", "a2", "a4"), board))
            .doesNotThrowAnyException();
    }

    @DisplayName("READY 상태에서 move 명령어를 실행하면 예외가 발생한다")
    @Test
    void invalidCommandInReadyStatus() {
        GameStatus gameStatus = new GameStatus(
            (board) -> System.out.println("executeStart"),
            (commands, board) -> false,
            (board) -> System.out.println("executeStatus"),
            (board) -> System.out.println("executeEnd")
        );
        Board board = new InitialBoardFactory().generate();
        assertThatThrownBy(() -> gameStatus.action(List.of("move", "a2", "a4"), board))
            .isInstanceOf(UnsupportedOperationException.class)
            .hasMessage("게임을 start 해 주세요.");
    }

    @DisplayName("READY 상태에서 end 명령어를 실행하면 FINISHED 상태로 바뀐다")
    @Test
    void changeReadyToFinished() {
        GameStatus gameStatus = new GameStatus(
            (board) -> System.out.println("executeStart"),
            (commands, board) -> false,
            (board) -> System.out.println("executeStatus"),
            (board) -> System.out.println("executeEnd")
        );
        Board board = new InitialBoardFactory().generate();
        GameStatus currentStatus = gameStatus.action(List.of("end"), board);
        assertThat(currentStatus.isFinished()).isTrue();
    }

    @DisplayName("RUNNING 상태에서 end 명령어를 실행하면 FINISHED 상태로 바뀐다")
    @Test
    void changeRunningToFinished() {
        GameStatus gameStatus = new GameStatus(
            RUNNING,
            (board) -> System.out.println("executeStart"),
            (commands, board) -> false,
            (board) -> System.out.println("executeStatus"),
            (board) -> System.out.println("executeEnd")
        );
        Board board = new InitialBoardFactory().generate();
        GameStatus currentStatus = gameStatus.action(List.of("end"), board);
        assertThat(currentStatus.isFinished()).isTrue();
    }

    @DisplayName("RUNNING 상태에서 move 명령어의 결과값이 false이면 FINISHED 상태로 바뀐다")
    @Test
    void changeRunningToFinishedWhenMoveFalse() {
        GameStatus gameStatus = new GameStatus(
            RUNNING,
            (board) -> System.out.println("executeStart"),
            (commands, board) -> false,
            (board) -> System.out.println("executeStatus"),
            (board) -> System.out.println("executeEnd")
        );
        Board board = new InitialBoardFactory().generate();
        GameStatus currentStatus = gameStatus.action(List.of("end"), board);
        assertThat(currentStatus.isFinished()).isTrue();
    }
}
