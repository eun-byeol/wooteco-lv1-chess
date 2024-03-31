package chess.service;

import chess.dao.ChessGameDao;
import chess.dto.ChessGameDto;
import chess.model.board.Board;
import chess.model.board.BoardFactory;
import chess.model.board.CustomBoardFactory;
import chess.model.board.InitialBoardFactory;
import chess.model.material.Color;
import chess.util.DataBaseConnector;
import java.util.Arrays;
import java.util.List;

public class ChessService {

    private final ChessGameDao chessGameDao;

    public ChessService() {
        DataBaseConnector connector = new DataBaseConnector();
        chessGameDao = new ChessGameDao(connector);
    }

    public boolean isGameSaved() {
        List<ChessGameDto> chessGames = chessGameDao.findAll();
        return chessGames.size() > 0;
    }

    public Board loadGame() {
        ChessGameDto chessGameDto = chessGameDao.findAll()
            .stream()
            .findFirst()
            .orElseThrow(() -> new UnsupportedOperationException("저장된 게임이 없습니다."));
        List<String> pieces = convert(chessGameDto.pieces());
        Color turn = Color.valueOf(chessGameDto.turn());
        CustomBoardFactory customBoardFactory = new CustomBoardFactory(
            pieces,
            turn,
            chessGameDto.id()
        );
        return customBoardFactory.generate();
    }

    private List<String> convert(String pieces) {
        return Arrays.stream(pieces.split("/"))
            .toList();
    }

    public Board saveGame(Board board) {
        ChessGameDto chessGameDto = ChessGameDto.from(board);
        Long id = chessGameDao.add(chessGameDto);
        return board.setId(id);
    }

    public void updateGame(Board board) {
        ChessGameDto chessGameDto = ChessGameDto.from(board);
        chessGameDao.update(chessGameDto);
    }

    public void deleteGame(Long id) {
        chessGameDao.delete(id);
    }
}
