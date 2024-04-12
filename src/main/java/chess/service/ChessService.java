package chess.service;

import chess.model.board.Board;

public interface ChessService {

    boolean isGameSaved();

    Board loadGame();

    Board saveGame(Board board);

    void updateGame(Board board);

    void deleteGame(Long id);
}
