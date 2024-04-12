package chess.model.piece;

import static chess.model.material.Color.BLACK;
import static chess.model.material.Color.WHITE;
import static chess.model.position.Movement.DOWN;
import static chess.model.position.Movement.DOWN_DOWN;
import static chess.model.position.Movement.DOWN_LEFT;
import static chess.model.position.Movement.DOWN_RIGHT;
import static chess.model.position.Movement.UP;
import static chess.model.position.Movement.UP_LEFT;
import static chess.model.position.Movement.UP_RIGHT;
import static chess.model.position.Movement.UP_UP;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

import chess.model.material.Color;
import chess.model.position.Movement;
import chess.model.position.Position;
import chess.model.position.Route;
import chess.model.position.Row;
import java.util.List;
import java.util.Map;

public class Pawn extends Piece {

    private static final Map<Color, Pawn> CACHE = Color.allColors()
        .stream()
        .collect(toMap(identity(), Pawn::new));

    private static final List<Movement> WHITE_ATTACK_MOVEMENTS = List.of(UP_LEFT, UP_RIGHT);
    private static final List<Movement> BLACK_ATTACK_MOVEMENTS = List.of(DOWN_LEFT, DOWN_RIGHT);
    private static final Row WHITE_INITIAL_ROW = Row.TWO;
    private static final Row BLACK_INITIAL_ROW = Row.SEVEN;
    private static final double POINT = 1;
    private static final double HALF_POINT = 0.5;

    private Pawn(Color color) {
        super(color);
    }

    public static Pawn of(Color color) {
        return CACHE.get(color);
    }

    @Override
    public Route findRoute(Position source, Position target) {
        validateMovement(source, target);
        return Route.of(source, target);
    }

    private void validateMovement(Position source, Position target) {
        Movement movement = Movement.findMovement(source, target);
        if (isSameColor(WHITE) && whiteCanMove(movement, source)) {
            return;
        }
        if (isSameColor(BLACK) && blackCanMove(movement, source)) {
            return;
        }
        throw new IllegalArgumentException("Pawn은 1칸 전진 이동 혹은 최초 2칸 전진 이동만 가능합니다.");
    }

    private boolean whiteCanMove(Movement movement, Position source) {
        if (movement == UP_UP && source.isSameRow(WHITE_INITIAL_ROW)) {
            return true;
        }
        return movement == UP;
    }

    private boolean blackCanMove(Movement movement, Position source) {
        if (movement == DOWN_DOWN && source.isSameRow(BLACK_INITIAL_ROW)) {
            return true;
        }
        return movement == DOWN;
    }

    @Override
    public boolean canAttack(Position source, Position target) {
        Movement movement = Movement.findMovement(source, target);
        if (isSameColor(WHITE) && WHITE_ATTACK_MOVEMENTS.contains(movement)) {
            return true;
        }
        if (isSameColor(BLACK) && BLACK_ATTACK_MOVEMENTS.contains(movement)) {
            return true;
        }
        throw new IllegalArgumentException("Pawn은 공격 시 전방 대각선 1칸 이동만 가능합니다.");
    }

    @Override
    public double totalPoint(int count) {
        if (count > 1) {
            return count * HALF_POINT;
        }
        return count * POINT;
    }
}
