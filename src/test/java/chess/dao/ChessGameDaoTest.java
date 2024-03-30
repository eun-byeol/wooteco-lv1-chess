package chess.dao;

import static org.assertj.core.api.Assertions.assertThat;

import chess.dto.ChessGameDto;
import chess.model.material.Color;
import chess.util.DataBaseConnector;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChessGameDaoTest {

    @DisplayName("체스 게임 저장 성공")
    @Test
    void addChessGame() {
        DataBaseConnector jdbcConnector = new DataBaseConnector();
        ChessGameDao chessGameDao = new ChessGameDao(jdbcConnector);

        ChessGameDto chessGameDto = new ChessGameDto(null, Color.WHITE.name(), 1);
        Long id = chessGameDao.addChessGame(chessGameDto);
        assertThat(chessGameDao.findById(id)).isPresent();
    }
}
