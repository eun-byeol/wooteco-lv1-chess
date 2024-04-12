package chess.dto;

import chess.dto.mapper.PieceMapper;
import chess.model.board.Board;
import chess.model.piece.Piece;
import chess.model.position.Column;
import chess.model.position.Position;
import chess.model.position.Row;
import java.util.ArrayList;
import java.util.List;

public record RankDto(List<String> rank) {

    private static final int MAX_INDEX = 7;

    public static RankDto of(Board board, Row row) {
        List<String> rank = new ArrayList<>();
        for (int i = 0; i <= MAX_INDEX; i++) {
            Column column = Column.findColumn(i);
            Position position = new Position(column, row);
            Piece piece = board.findPiece(position);
            rank.add(PieceMapper.serialize(piece));
        }
        return new RankDto(rank);
    }
}
