package chess.dto;

public record PieceDto(Long id, Integer row, Integer column, String piece, Long chessGameId) {

}
