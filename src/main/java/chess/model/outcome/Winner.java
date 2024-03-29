package chess.model.outcome;

import chess.dto.ColorScoreDto;
import chess.dto.WinnerDto;
import chess.model.material.Color;

public final class Winner {

    private Winner() {
    }

    public static WinnerDto of(ColorScoreDto firstColor, ColorScoreDto secondColor) {
        if (firstColor.score() > secondColor.score()) {
            return new WinnerDto(firstColor.color());
        }
        if (firstColor.score() < secondColor.score()) {
            return new WinnerDto(secondColor.color());
        }
        return WinnerDto.from(Color.NONE);
    }
}
