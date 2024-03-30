package chess.service;

import static java.util.stream.Collectors.toMap;

import chess.dao.ChessGameDao;
import chess.dao.PieceDao;
import chess.dto.ChessGameDto;
import chess.dto.PieceDto;
import chess.dto.mapper.PieceMapper;
import chess.model.board.Board;
import chess.model.material.Color;
import chess.model.piece.Piece;
import chess.model.position.Column;
import chess.model.position.Position;
import chess.model.position.Row;
import chess.util.DataBaseConnector;
import java.util.Map;

public class ChessService {

    private final ChessGameDao chessGameDao;
    private final PieceDao pieceDao;

    public ChessService() {
        DataBaseConnector connector = new DataBaseConnector();
        chessGameDao = new ChessGameDao(connector);
        pieceDao = new PieceDao(connector);
    }

    public Long saveGame(Board board) {
        ChessGameDto chessGameDto = new ChessGameDto(0L, board.turnName());
        Long chessGameId = chessGameDao.addChessGame(chessGameDto);

        for (Row row : Row.values()) {
            for (Column column : Column.values()) {
                Piece piece = board.findPiece(new Position(column, row));
                String pieceName = PieceMapper.serialize(piece);
                PieceDto pieceDto = new PieceDto(0L, row.getIndex(), column.getIndex(), pieceName,
                    chessGameId);
                pieceDao.addPiece(pieceDto);
            }
        }
        return chessGameId;
    }

    public Board loadGame() {
        ChessGameDto chessGameDto = chessGameDao.findAll()
            .stream()
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("저장된 게임이 없습니다."));
        Long gameId = chessGameDto.id();
        Map<Position, Piece> pieces = pieceDao.findAllByChessGameId(gameId)
            .stream()
            .collect(toMap(this::createPosition, this::createPiece));
        return new Board(pieces, Color.valueOf(chessGameDto.turn()));
    }

    private Position createPosition(PieceDto pieceDto) {
        int columnIndex = pieceDto.column();
        int rowIndex = pieceDto.row();
        return new Position(Column.findColumn(columnIndex), Row.findRow(rowIndex));
    }

    private Piece createPiece(PieceDto pieceDto) {
        return PieceMapper.deserialize(pieceDto.piece());
    }
}
