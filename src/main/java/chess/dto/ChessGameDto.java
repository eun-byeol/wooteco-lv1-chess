package chess.dto;

import static java.util.stream.Collectors.joining;

import chess.dto.mapper.PieceMapper;
import chess.model.board.Board;
import chess.model.position.Column;
import chess.model.position.Position;
import chess.model.position.Row;
import java.util.Arrays;
import java.util.List;

public record ChessGameDto(Long id, String turn, String pieces) {

    private static final String ROW_DELIMITER = "/";

    public static ChessGameDto from(Board board) {
        String pieces = Arrays.stream(Row.values())
            .map(row -> joinPieces(row, board))
            .collect(joining(ROW_DELIMITER));
        return new ChessGameDto(board.getId(), board.turnName(), pieces);
    }

    private static String joinPieces(Row row, Board board) {
        return Arrays.stream(Column.values())
            .map(column -> PieceMapper.serialize(board.findPiece(new Position(column, row))))
            .collect(joining());
    }

    public List<String> unJoinPieces() {
        return Arrays.stream(pieces.split(ROW_DELIMITER))
            .toList();
    }
}
