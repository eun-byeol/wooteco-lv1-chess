package chess;

import chess.controller.ChessGame;
import chess.dao.ChessGameDao;
import chess.db.DataBaseConnector;
import chess.db.ProductionConnector;
import chess.service.ChessService;
import chess.view.InputView;
import chess.view.OutputView;

public final class Application {

    public static void main(String[] args) {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();

        DataBaseConnector dataBaseConnector = new ProductionConnector();
        ChessGameDao chessGameDao = new ChessGameDao(dataBaseConnector);
        ChessService chessService = new ChessService(chessGameDao);

        ChessGame chessGame = new ChessGame(inputView, outputView, chessService);
        chessGame.run();
    }
}
