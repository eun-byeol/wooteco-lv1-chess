package domain.piece;

import dao.Movement;
import domain.Turn;
import domain.point.Direction;
import domain.point.Point;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Knight extends Piece {

    public static final float SCORE = 2.5f;

    public Knight(Turn turn) {
        super(turn);
    }

    @Override
    public Map<Direction, Integer> getMovableDirectionAndRange() {
        HashMap<Direction, Integer> movableRange = new HashMap<>();
        movableRange.put(Direction.UP_LEFT_L, 1);
        movableRange.put(Direction.UP_RIGHT_L, 1);
        movableRange.put(Direction.RIGHT_UP_L, 1);
        movableRange.put(Direction.RIGHT_DOWN_L, 1);
        movableRange.put(Direction.DOWN_LEFT_L, 1);
        movableRange.put(Direction.DOWN_RIGHT_L, 1);
        movableRange.put(Direction.LEFT_UP_L, 1);
        movableRange.put(Direction.LEFT_DOWN_L, 1);
        return movableRange;
    }

    @Override
    protected List<Point> findSpecializedPoints(Movement movement, Map<Point, Piece> status) {
        return List.of();
    }

    @Override
    public boolean exists() {
        return false;
    }

    @Override
    public float getScore(List<Piece> line) {
        return SCORE;
    }

    @Override
    protected String getInitial() {
        return "n";
    }

    @Override
    public boolean equals(Object obj) {
        return getClass() == obj.getClass()
                && turn == ((Knight) obj).turn;
    }
}