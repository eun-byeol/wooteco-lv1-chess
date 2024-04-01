package chess;

import chess.controller.ChessGame;
import chess.dao.ChessGameDaoImpl;
import chess.db.DataBaseConnector;
import chess.db.ProductionConnector;
import chess.service.ChessServiceImpl;
import chess.view.InputView;
import chess.view.OutputView;

public final class Application {

    public static void main(String[] args) {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();

        DataBaseConnector dataBaseConnector = new ProductionConnector();
        ChessGameDaoImpl chessGameDao = new ChessGameDaoImpl(dataBaseConnector);
        ChessServiceImpl chessService = new ChessServiceImpl(chessGameDao);

        ChessGame chessGame = new ChessGame(inputView, outputView, chessService);
        chessGame.run();
    }
}
