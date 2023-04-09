package domain.piece;

import dao.Movement;
import domain.Turn;
import domain.point.Direction;
import domain.point.Point;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Rook extends Piece {

    public static final float SCORE = 5f;

    public Rook(Turn turn) {
        super(turn);
    }

    @Override
    public Map<Direction, Integer> getMovableDirectionAndRange() {
        Map<Direction, Integer> movableRange = new HashMap<>();
        movableRange.put(Direction.UP, 8);
        movableRange.put(Direction.DOWN, 8);
        movableRange.put(Direction.LEFT, 8);
        movableRange.put(Direction.RIGHT, 8);

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
        return "r";
    }

    @Override
    public boolean equals(Object obj) {
        return getClass() == obj.getClass()
                && turn == ((Rook) obj).turn;
    }
}
