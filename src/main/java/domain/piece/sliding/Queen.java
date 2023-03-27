package domain.piece.sliding;

import domain.piece.move.Coordinate;
import domain.piece.move.Direction;
import domain.piece.move.Inclination;
import domain.piece.Color;
import domain.piece.Piece;

import java.util.List;

public final class Queen extends Piece {

    private static final Direction DIRECTION = new Direction(List.of(
            Inclination.ONE, Inclination.MINUS_ONE, Inclination.NEGATIVE_INFINITY,
            Inclination.POSITIVE_INFINITY, Inclination.ZERO, Inclination.MINUS_ZERO
    ));

    private static final double POINT = 9;

    public Queen(final Color color) {
        super(color);
    }

    @Override
    public boolean isMovable(
            final Coordinate start,
            final Coordinate end
    ) {
        Inclination inclination = Inclination.of(start.getInclination(end));

        return DIRECTION.canBeDirectionOf(inclination);
    }

    @Override
    public boolean isAttackable(
            final Coordinate start,
            final Coordinate end,
            final Piece target
    ) {
        return isMovable(start, end);
    }

    @Override
    public double getPoint() {
        return POINT;
    }
}
