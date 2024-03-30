package chess.dao;

import static org.assertj.core.api.Assertions.assertThat;

import chess.dto.ChessGameDto;
import chess.model.material.Color;
import chess.util.DataBaseConnector;
import java.util.List;
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
        ChessGameDto chessGameDto = new ChessGameDto(null, Color.WHITE.name());
        Long id = chessGameDao.addChessGame(chessGameDto);
        assertThat(chessGameDao.findById(id)).isPresent();
    }

    @DisplayName("체스 게임 전체 조회 성공")
    @Test
    void findAllChessGame() {
        ChessGameDto firstGame = new ChessGameDto(null, Color.WHITE.name());
        ChessGameDto secondGame = new ChessGameDto(null, Color.WHITE.name());
        Long firstGameId = chessGameDao.addChessGame(firstGame);
        Long secondGameId = chessGameDao.addChessGame(secondGame);

        List<ChessGameDto> chessGameDtos = chessGameDao.findAll();

        assertThat(chessGameDtos.stream().map(ChessGameDto::id))
            .contains(firstGameId)
            .contains(secondGameId);
    }

    @DisplayName("체스 게임 수정 성공")
    @Test
    void updateChessGame() {
        ChessGameDto chessGameDto = new ChessGameDto(null, Color.BLACK.name());
        Long id = chessGameDao.addChessGame(chessGameDto);

        ChessGameDto finishedGameDto = new ChessGameDto(id, Color.WHITE.name());
        chessGameDao.updateChessGame(finishedGameDto);

        assertThat(chessGameDao.findById(id)).contains(finishedGameDto);
    }
}
