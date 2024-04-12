package chess.dto;

import static java.util.stream.Collectors.joining;

import chess.dto.mapper.PieceMapper;
import chess.model.board.Board;
import chess.model.position.Column;
import chess.model.position.Position;
import chess.model.position.Row;
import java.util.Arrays;
import java.util.List;

public record MovementDto(String pieces, Long gameId) {

    private static final String ROW_DELIMITER = "/";

    public static MovementDto from(Board board) {
        String pieces = Arrays.stream(Row.values())
            .map(row -> joinPieces(row, board))
            .collect(joining(ROW_DELIMITER));
        return new MovementDto(pieces, board.getId());
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
