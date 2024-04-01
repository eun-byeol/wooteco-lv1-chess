package chess.dto;

import chess.dto.mapper.ColorMapper;
import chess.model.outcome.ColorScore;

public record ColorScoreDto(String color, double score) {

    public static ColorScoreDto from(ColorScore colorScore) {
        String colorName = ColorMapper.serialize(colorScore.getColor());
        return new ColorScoreDto(colorName, colorScore.getScore());
    }
}
