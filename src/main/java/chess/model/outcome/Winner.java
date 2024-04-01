package chess.model.outcome;

import chess.model.material.Color;

public final class Winner {

    private final ColorScore colorScore;

    public Winner(ColorScore firstScore, ColorScore secondScore) {
        colorScore = determine(firstScore, secondScore);
    }

    public ColorScore determine(ColorScore firstScore, ColorScore secondScore) {
        if (firstScore.isHigherThan(secondScore)) {
            return firstScore;
        }
        if (secondScore.isHigherThan(firstScore)) {
            return secondScore;
        }
        return ColorScore.empty();
    }

    public Color getColor() {
        return colorScore.getColor();
    }
}
