package chess.model.outcome;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

import chess.model.material.Color;
import chess.model.piece.Piece;
import chess.model.position.Column;
import chess.model.position.Position;
import java.util.Map;
import java.util.Map.Entry;

public final class ScoreCalculator {

    private static final Integer INITIAL_COUNT = 1;

    private final Map<Position, Piece> pieces;

    public ScoreCalculator(Map<Position, Piece> pieces) {
        this.pieces = pieces;
    }

    public ColorScore calculate(Color color) {
        double total = 0;
        for (Column column : Column.values()) {
            Map<Piece, Integer> counts = countPiece(column, color);
            total += scores(counts);
        }
        return new ColorScore(color, total);
    }

    private Map<Piece, Integer> countPiece(Column column, Color color) {
        return Position.rows(column)
            .stream()
            .map(pieces::get)
            .filter(piece -> piece.isSameColor(color))
            .collect(toMap(identity(), piece -> INITIAL_COUNT, Integer::sum));
    }

    private double scores(Map<Piece, Integer> counts) {
        return counts.entrySet()
            .stream()
            .mapToDouble(this::calculateScore)
            .sum();
    }

    private double calculateScore(Entry<Piece, Integer> entry) {
        Piece piece = entry.getKey();
        int count = entry.getValue();
        return piece.totalPoint(count);
    }
}
