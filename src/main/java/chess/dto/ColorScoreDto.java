package chess.dto;

import chess.dto.mapper.ColorMapper;
import chess.model.material.Color;

public record ColorScoreDto(String color, double score) {

    public static ColorScoreDto of(Color color, double score) {
        String colorName = ColorMapper.serialize(color);
        return new ColorScoreDto(colorName, score);
    }
}
