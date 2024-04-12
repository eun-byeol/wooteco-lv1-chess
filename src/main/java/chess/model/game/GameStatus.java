package chess.model.game;

import chess.model.board.Board;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

public class GameStatus {

    private static final int COMMAND_INDEX = 0;

    private final Status status;
    private final Consumer<Board> onStart;
    private final BiPredicate<List<String>, Board> onMove;
    private final Consumer<Board> onStatus;
    private final Consumer<Board> onEnd;

    public GameStatus(
        Consumer<Board> onStart,
        BiPredicate<List<String>, Board> onMove,
        Consumer<Board> onStatus,
        Consumer<Board> onEnd
    ) {
        this(Status.READY, onStart, onMove, onStatus, onEnd);
    }

    public GameStatus(
        Status status,
        Consumer<Board> onStart,
        BiPredicate<List<String>, Board> onMove,
        Consumer<Board> onStatus,
        Consumer<Board> onEnd
    ) {
        this.status = status;
        this.onStart = onStart;
        this.onMove = onMove;
        this.onStatus = onStatus;
        this.onEnd = onEnd;
    }

    public GameStatus action(List<String> commands, Board board) {
        Command command = Command.findCommand(commands.get(COMMAND_INDEX));
        if (command.isEnd()) {
            onEnd.accept(board);
            return finishGame();
        }
        if (command.isStart()) {
            return executeStart(board);
        }
        if (command.isMove()) {
            return executeMove(commands, board);
        }
        if (command.isStatus()) {
            executeStatus(board);
        }
        return this;
    }

    private GameStatus finishGame() {
        return new GameStatus(Status.FINISHED, onStart, onMove, onStatus, onEnd);
    }

    private GameStatus executeStart(Board board) {
        if (status.isReady()) {
            onStart.accept(board);
            return new GameStatus(Status.RUNNING, onStart, onMove, onStatus, onEnd);
        }
        throw new UnsupportedOperationException("게임이 이미 진행 중 입니다.");
    }

    private GameStatus executeMove(List<String> commands, Board board) {
        if (status.isReady()) {
            throw new UnsupportedOperationException("게임을 start 해 주세요.");
        }
        boolean isKingCaught = onMove.test(commands, board);
        if (isKingCaught) {
            return finishGame();
        }
        return this;
    }

    private void executeStatus(Board board) {
        if (isReady()) {
            throw new UnsupportedOperationException("게임을 start 해 주세요.");
        }
        onStatus.accept(board);
    }

    private boolean isReady() {
        return status.isReady();
    }

    public boolean isNotFinished() {
        return status.isNotFinished();
    }

    public boolean isFinished() {
        return status.isFinished();
    }

    public boolean isRunning() {
        return status.isRunning();
    }
}
