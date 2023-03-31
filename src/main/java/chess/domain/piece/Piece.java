package chess.domain.piece;

import chess.domain.piece.property.Color;
import chess.domain.position.Path;
import chess.domain.position.Position;
import chess.exception.ChessException;
import chess.exception.ExceptionCode;

import java.util.Objects;

public abstract class Piece {

    protected final Position position;
    protected final Color color;

    protected Piece(final Position position, final Color color) {
        this.position = position;
        this.color =color;
    }

    public final boolean isSameColor(final Color otherColor) {
        return this.color == otherColor;
    }

    protected final void validateSamePosition(final Position targetPosition) {
        if (position.equals(targetPosition)) {
            throw new ChessException(ExceptionCode.INVALID_DESTINATION);
        }
    }

    protected final void validateDestination(final Position targetPosition) {
        if (!canMove(targetPosition)) {
            throw new ChessException(ExceptionCode.INVALID_DESTINATION);
        }
    }

    protected abstract boolean canMove(final Position targetPosition);

    public abstract Piece move(final Piece pieceInTargetPosition);

    protected final void validateCatchingSameColor(final Piece pieceInTargetPosition) {
        if (pieceInTargetPosition.isSameColor(color)) {
            throw new ChessException(ExceptionCode.TARGET_IS_SAME_COLOR);
        }
    }

    public abstract Path getPassingPositions(final Position targetPosition);

    public final Color getColor() {
        return color;
    }

    public final Position getPosition() {
        return position;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Piece piece = (Piece) o;
        return Objects.equals(position, piece.position) && color == piece.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, color);
    }
}
