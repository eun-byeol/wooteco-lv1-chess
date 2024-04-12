package chess.dto.mapper;

import chess.model.material.Color;

public enum ColorMapper {

    WHITE("백팀"),
    BLACK("흑팀"),
    NONE("없음");

    private final String displayName;

    ColorMapper(String displayName) {
        this.displayName = displayName;
    }

    public static String serialize(Color color) {
        if (WHITE.name().equals(color.name())) {
            return WHITE.displayName;
        }
        if (BLACK.name().equals(color.name())) {
            return BLACK.displayName;
        }
        return NONE.displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
