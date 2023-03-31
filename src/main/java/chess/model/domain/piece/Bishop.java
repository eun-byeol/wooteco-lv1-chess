package chess.model.domain.piece;

import chess.model.domain.position.Movement;
import java.util.List;

public class Bishop extends SlidingPiece {

    private static final List<Movement> CAN_MOVE_DESTINATION = List.of(
            Movement.UR, Movement.UL, Movement.DR, Movement.DL);

    public Bishop(final Color color) {
        super(color, PieceType.BISHOP);
    }

    @Override
    protected final List<Movement> getCanMoveDestination() {
        return CAN_MOVE_DESTINATION;
    }
}