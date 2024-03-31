package chess.model.board;

import chess.model.material.Color;
import java.util.List;

public final class CustomBoardFactory extends BoardFactory {

    private final List<String> pieces;
    private final Color turn;
    private final Long id;

    public CustomBoardFactory(List<String> pieces, Color turn, Long id) {
        this.pieces = pieces;
        this.turn = turn;
        this.id = id;
    }

    @Override
    public Board generate() {
        return new Board(generatePieces(pieces), id, turn);
    }
}
