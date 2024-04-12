package chess.model.outcome;

import chess.model.material.Color;

public class ColorScore {

    private final Color color;
    private final double score;

    public ColorScore(Color color, double score) {
        this.color = color;
        this.score = score;
    }

    public static ColorScore empty() {
        return new ColorScore(Color.NONE, 0L);
    }

    public boolean isHigherThan(ColorScore firstScore) {
        return score > firstScore.getScore();
    }

    public Color getColor() {
        return color;
    }

    public double getScore() {
        return score;
    }
}
