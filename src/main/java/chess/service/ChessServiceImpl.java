package chess.service;

import chess.dao.ChessGameDao;
import chess.dao.MovementDao;
import chess.dto.ChessGameDto;
import chess.dto.MovementDto;
import chess.model.board.Board;
import chess.model.board.CustomBoardFactory;
import chess.model.material.Color;
import java.util.List;

public class ChessServiceImpl implements ChessService {

    private final ChessGameDao chessGameDao;
    private final MovementDao movementDao;

    public ChessServiceImpl(ChessGameDao chessGameDao, MovementDao movementDao) {
        this.chessGameDao = chessGameDao;
        this.movementDao = movementDao;
    }

    @Override
    public boolean isGameSaved() {
        List<ChessGameDto> chessGames = chessGameDao.findAll();
        return chessGames.size() > 0;
    }

    @Override
    public Board loadGame() {
        ChessGameDto chessGameDto = findFirstChessGameDto();
        List<String> pieces = chessGameDto.unJoinPieces();
        Color turn = Color.valueOf(chessGameDto.turn());
        CustomBoardFactory customBoardFactory = new CustomBoardFactory(
            pieces,
            chessGameDto.id(),
            turn
        );
        return customBoardFactory.generate();
    }

    private ChessGameDto findFirstChessGameDto() {
        return chessGameDao.findAll()
            .stream()
            .findFirst()
            .orElseThrow(() -> new UnsupportedOperationException("저장된 게임이 없습니다."));
    }

    @Override
    public Board saveGame(Board board) {
        ChessGameDto chessGameDto = ChessGameDto.from(board);
        Long id = chessGameDao.add(chessGameDto);
        Board savedBoard = board.setId(id);

        MovementDto movementDto = MovementDto.from(savedBoard);
        movementDao.add(movementDto);
        return savedBoard;
    }

    @Override
    public void updateGame(Board board) {
        ChessGameDto chessGameDto = ChessGameDto.from(board);
        chessGameDao.update(chessGameDto);
    }

    @Override
    public void deleteGame(Long id) {
        chessGameDao.delete(id);
    }
}
