package chess.dao;

import static org.assertj.core.api.Assertions.assertThat;

import chess.dto.ChessGameDto;
import chess.model.material.Color;
import chess.util.DataBaseConnector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChessGameDaoTest {

    private final DataBaseConnector connector = new DataBaseConnector();
    private ChessGameDao chessGameDao;

    @BeforeEach
    void setUp() {
        chessGameDao = new ChessGameDao(connector);
    }

    @DisplayName("체스 게임 저장 성공")
    @Test
    void addChessGame() {
        ChessGameDto chessGameDto = new ChessGameDto(null, Color.WHITE.name(), 1);
        Long id = chessGameDao.addChessGame(chessGameDto);
        assertThat(chessGameDao.findById(id)).isPresent();
    }

    @DisplayName("진행 중인 체스 게임 조회 성공")
    @Test
    void findRunningChessGame() {
        ChessGameDto runningGame = new ChessGameDto(null, Color.WHITE.name(), 1);
        ChessGameDto finishedGame = new ChessGameDto(null, Color.WHITE.name(), 0);
        Long runningGameId = chessGameDao.addChessGame(runningGame);
        Long finishedGameId = chessGameDao.addChessGame(finishedGame);

        ChessGameDto updatedRunningGame = new ChessGameDto(runningGameId, Color.WHITE.name(), 1);
        ChessGameDto updatedFinishedGame = new ChessGameDto(finishedGameId, Color.WHITE.name(), 0);

        assertThat(chessGameDao.findRunningGame())
            .contains(updatedRunningGame)
            .doesNotContain(updatedFinishedGame);
    }

    @DisplayName("체스 게임 수정 성공")
    @Test
    void updateChessGame() {
        ChessGameDto chessGameDto = new ChessGameDto(null, Color.BLACK.name(), 1);
        Long id = chessGameDao.addChessGame(chessGameDto);

        ChessGameDto finishedGameDto = new ChessGameDto(id, Color.WHITE.name(), 0);
        chessGameDao.updateChessGame(finishedGameDto);

        assertThat(chessGameDao.findById(id)).contains(finishedGameDto);
    }
}
