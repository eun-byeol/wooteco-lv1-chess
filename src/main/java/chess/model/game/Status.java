package chess.model.game;

public enum Status {

    READY,
    RUNNING,
    FINISHED;

    public boolean isReady() {
        return this == READY;
    }

    public boolean isRunning() {
        return this == RUNNING;
    }

    public boolean isNotFinished() {
        return !isFinished();
    }

    public boolean isFinished() {
        return this == FINISHED;
    }
}
