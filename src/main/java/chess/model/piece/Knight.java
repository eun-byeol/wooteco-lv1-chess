package chess.model.piece;

import static chess.model.Direction.DOWN_DOWN_LEFT;
import static chess.model.Direction.DOWN_DOWN_RIGHT;
import static chess.model.Direction.LEFT_LEFT_DOWN;
import static chess.model.Direction.LEFT_LEFT_UP;
import static chess.model.Direction.RIGHT_RIGHT_DOWN;
import static chess.model.Direction.RIGHT_RIGHT_UP;
import static chess.model.Direction.UP_UP_LEFT;
import static chess.model.Direction.UP_UP_RIGHT;

import chess.model.Direction;
import chess.model.material.Color;
import chess.model.material.Type;
import chess.model.position.Position;
import java.util.List;
import java.util.Map;

public class Knight extends Piece {

    private static final List<Direction> DIRECTIONS = List.of(
        UP_UP_LEFT, UP_UP_RIGHT, DOWN_DOWN_LEFT, DOWN_DOWN_RIGHT,
        LEFT_LEFT_UP, LEFT_LEFT_DOWN, RIGHT_RIGHT_UP, RIGHT_RIGHT_DOWN
    );

    public Knight(Type type, Color color) {
        super(type, color);
    }

    @Override
    public void move(Position source, Position target, Map<Position, Piece> pieces) {
        validateDirection(source, target);
    }

    public void validateDirection(Position source, Position target) {
        Direction direction = Direction.findDirectionByDelta(source, target);
        if (DIRECTIONS.contains(direction)) {
            return;
        }
        throw new IllegalArgumentException("Knight는 L자 이동만 가능합니다.");
    }
}
