package chess.domain.board;

import chess.domain.piece.Piece;
import chess.domain.position.Position;
import java.util.ArrayList;
import java.util.List;

public final class Path {

    private final List<Position> positions;

    public Path(final List<Position> positions) {
        this.positions = positions;
    }

    public boolean contains(final Position targetPosition) {
        return positions.contains(targetPosition);
    }

    public List<Position> removeObstacleInPath(final Position position, final Board board) {
        final Piece piece = board.pieceAt(position);
        if (piece.isPawn()) {
            return calculatePawnPath(position, board);
        }
        return calculateNonePawnPath(piece, board);
    }

    private List<Position> calculatePawnPath(final Position position, final Board board) {
        final List<Position> cleanPath = new ArrayList<>();
        for (Position target : positions) {
            if (straightPositionIsEmpty(position, target, board)) {
                cleanPath.add(target);
            }
            if (diagonalPositionHasEnemy(position, target, board)) {
                cleanPath.add(target);
            }
        }
        return cleanPath;
    }

    private boolean straightPositionIsEmpty(final Position position, final Position target,
            final Board board) {
        final Piece otherPiece = board.pieceAt(target);
        return position.isOfColumn(target.column()) && otherPiece.isEmpty();
    }

    private boolean diagonalPositionHasEnemy(final Position position, final Position target,
            final Board board) {
        final Piece piece = board.pieceAt(position);
        final Piece otherPiece = board.pieceAt(target);
        return !position.isOfColumn(target.column())
                && piece.isDifferentColor(otherPiece)
                && !otherPiece.isEmpty();

    }

    private List<Position> calculateNonePawnPath(final Piece piece, final Board board) {
        final List<Position> cleanPath = new ArrayList<>();
        for (Position position : positions) {
            final Piece otherPiece = board.pieceAt(position);
            if (piece.isSameColor(otherPiece)) {
                break;
            }
            cleanPath.add(position);
            if (piece.isDifferentColor(otherPiece) && !otherPiece.isEmpty()) {
                break;
            }
        }
        return cleanPath;
    }

    public List<Position> positions() {
        return positions;
    }
}