package chess.model.board;

import chess.model.material.Color;
import java.util.List;

public final class CustomBoardFactory extends BoardFactory {

    private final List<String> pieces;
    private final Long id;
    private final Color turn;

    public CustomBoardFactory(List<String> pieces, Long id, Color turn) {
        this.pieces = pieces;
        this.id = id;
        this.turn = turn;
    }

    @Override
    public Board generate() {
        return new Board(generatePieces(pieces), id, turn);
    }
}
