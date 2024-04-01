package chess.dto;

import chess.model.board.Board;

public record ChessGameDto(Long id, String turn) {

    public static ChessGameDto from(Board board) {
        return new ChessGameDto(board.getId(), board.turnName());
    }
}
