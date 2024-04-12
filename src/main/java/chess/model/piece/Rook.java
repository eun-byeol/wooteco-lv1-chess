package chess.model.piece;

import static chess.model.position.Movement.DOWN;
import static chess.model.position.Movement.LEFT;
import static chess.model.position.Movement.RIGHT;
import static chess.model.position.Movement.UP;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

import chess.model.material.Color;
import chess.model.position.Movement;
import chess.model.position.Position;
import chess.model.position.Route;
import java.util.List;
import java.util.Map;

public class Rook extends Piece {

    private static final Map<Color, Rook> CACHE = Color.allColors()
        .stream()
        .collect(toMap(identity(), Rook::new));
    private static final List<Movement> MOVEMENTS = List.of(UP, DOWN, LEFT, RIGHT);
    private static final double POINT = 5;

    private Rook(Color color) {
        super(color);
    }

    public static Rook of(Color color) {
        return CACHE.get(color);
    }

    @Override
    public Route findRoute(Position source, Position target) {
        validateMovement(source, target);
        return Route.of(source, target);
    }

    private void validateMovement(Position source, Position target) {
        Movement movement = Movement.findDirection(source, target);
        if (MOVEMENTS.contains(movement)) {
            return;
        }
        throw new IllegalArgumentException("Rook은 상하좌우 이동만 가능합니다.");
    }

    @Override
    public double totalPoint(int count) {
        return count * POINT;
    }
}
