package chess.dto;

import static chess.dto.mapper.ColorMapper.NONE;

import chess.dto.mapper.ColorMapper;
import chess.model.material.Color;

public record WinnerDto(String winner) {

    public static WinnerDto from(Color color) {
        String colorName = ColorMapper.serialize(color);
        return new WinnerDto(colorName);
    }

    public boolean isDraw() {
        return NONE.getDisplayName().equals(winner);
    }
}
