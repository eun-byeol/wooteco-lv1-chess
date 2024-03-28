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

    public GameStatus(Consumer<Board> onStart, BiPredicate<List<String>, Board> onMove,
        Consumer<Board> onStatus) {
        this(Status.READY, onStart, onMove, onStatus);
    }

    public GameStatus(
        Status status,
        Consumer<Board> onStart,
        BiPredicate<List<String>, Board> onMove,
        Consumer<Board> onStatus
    ) {
        this.status = status;
        this.onStart = onStart;
        this.onMove = onMove;
        this.onStatus = onStatus;
    }

    public GameStatus action(List<String> commands, Board board) {
        Command command = Command.findCommand(commands.get(COMMAND_INDEX));
        if (command.isEnd()) {
            return changeEnd();
        }
        if (command.isStart()) {
            GameStatus gameStatus = changeStart();
            onStart.accept(board);
            return gameStatus;
        }
        if (command.isMove()) {
            return updateStatus(commands, board);
        }
        if (command.isStatus()) {
            onStatus.accept(board);
        }
        return this;
    }

    private GameStatus updateStatus(List<String> commands, Board board) {
        GameStatus gameStatus = changeMove();
        boolean success = onMove.test(commands, board);
        if (success) {
            return gameStatus;
        }
        return changeEnd();
    }

    private GameStatus changeEnd() {
        return new GameStatus(Status.END, onStart, onMove, onStatus);
    }

    private GameStatus changeStart() {
        if (status.isReady()) {
            return new GameStatus(Status.START, onStart, onMove, onStatus);
        }
        throw new UnsupportedOperationException("게임이 이미 진행 중 입니다.");
    }

    private GameStatus changeMove() {
        if (status.isMove()) {
            return this;
        }
        if (status.isStart()) {
            return new GameStatus(Status.MOVE, onStart, onMove, onStatus);
        }
        throw new UnsupportedOperationException("게임을 start 해 주세요.");
    }

    public boolean isRunning() {
        return status.isRunning();
    }

    public boolean isReady() {
        return status.isReady();
    }

    public boolean isStarted() {
        return status.isStart();
    }

    public boolean isMoved() {
        return status.isMove();
    }
}
