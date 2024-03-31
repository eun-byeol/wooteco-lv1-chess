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

    public Board loadGame() {
        if (isGameSaved()) {
            ChessGameDto chessGameDto = chessGameDao.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("저장된 게임이 없습니다."));
            List<String> pieces = convert(chessGameDto.pieces());
            Color turn = Color.valueOf(chessGameDto.turn());
            CustomBoardFactory customBoardFactory = new CustomBoardFactory(
                pieces,
                turn,
                chessGameDto.id()
            );
            return customBoardFactory.generate();
        }
        BoardFactory boardFactory = new InitialBoardFactory();
        Board board = boardFactory.generate();
        Long gameId = saveGame(board);
        return board.setId(gameId);
    }

    private List<String> convert(String pieces) {
        return Arrays.stream(pieces.split("/"))
            .toList();
    }

    public Long saveGame(Board board) {
        ChessGameDto chessGameDto = ChessGameDto.from(board);
        return chessGameDao.add(chessGameDto);
    }

    public boolean isGameSaved() {
        List<ChessGameDto> chessGames = chessGameDao.findAll();
        return chessGames.size() > 0;
    }

    public void updateGame(Board board) {
        ChessGameDto chessGameDto = ChessGameDto.from(board);
        chessGameDao.update(chessGameDto);
    }

    public void deleteGame(Long id) {
        chessGameDao.delete(id);
    }
}
