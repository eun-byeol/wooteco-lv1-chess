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
            return changeEnd();
        }
        if (command.isStart()) {
            GameStatus gameStatus = changeStart();
            onStart.accept(board);
            return gameStatus;
        }
        if (command.isMove()) {
            return changeStatus(commands, board);
        }
        if (command.isStatus()) {
            executeStatus(board);
        }
        return this;
    }

    private GameStatus changeEnd() {
        return new GameStatus(Status.END, onStart, onMove, onStatus, onEnd);
    }

    private GameStatus changeStart() {
        if (status.isReady()) {
            return new GameStatus(Status.START, onStart, onMove, onStatus, onEnd);
        }
        throw new UnsupportedOperationException("게임이 이미 진행 중 입니다.");
    }

    private GameStatus changeStatus(List<String> commands, Board board) {
        GameStatus gameStatus = changeMove();
        boolean isKingCaught = onMove.test(commands, board);
        if (isKingCaught) {
            return changeEnd();
        }
        return gameStatus;
    }

    private GameStatus changeMove() {
        if (status.isMove()) {
            return this;
        }
        if (status.isStart()) {
            return new GameStatus(Status.MOVE, onStart, onMove, onStatus, onEnd);
        }
        throw new UnsupportedOperationException("게임을 start 해 주세요.");
    }

    private void executeStatus(Board board) {
        if (isReady()) {
            throw new UnsupportedOperationException("게임을 start 해 주세요.");
        }
        onStatus.accept(board);
    }

    public boolean isRunning() {
        return status.isRunning();
    }

    public boolean isStarted() {
        return status.isStart();
    }

    public boolean isMoved() {
        return status.isMove();
    }

    private boolean isReady() {
        return status.isReady();
    }
}
