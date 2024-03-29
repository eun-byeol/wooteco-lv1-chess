package chess.model.outcome;

import chess.dto.ColorScoreDto;
import chess.model.material.Color;
import chess.model.piece.Piece;
import chess.model.position.Column;
import chess.model.position.Position;
import java.util.HashMap;
import java.util.Map;

public final class ScoreCalculator {

    private final Map<Position, Piece> pieces;

    public ScoreCalculator(Map<Position, Piece> pieces) {
        this.pieces = pieces;
    }

    public ColorScoreDto calculate(Color color) {
        double total = 0;
        for (Column column : Column.values()) {
            Map<Piece, Integer> counts = new HashMap<>();
            for (Position position : Position.rows(column)) {
                Piece piece = pieces.get(position);
                if (piece.isSameColor(color)) {
                    counts.put(piece, counts.getOrDefault(piece, 0) + 1);
                }
            }
            total += scores(counts);
        }
        return ColorScoreDto.of(color, total);
    }

    private double scores(Map<Piece, Integer> counts) {
        double total = 0;
        for (Piece piece : counts.keySet()) {
            int count = counts.get(piece);
            total += piece.totalPoint(count);
        }
        return total;
    }
}
