package chess.dao;

import static org.assertj.core.api.Assertions.assertThat;

import chess.dto.PieceDto;
import chess.util.DataBaseConnector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PieceDaoTest {

    private final DataBaseConnector connector = new DataBaseConnector();
    private PieceDao pieceDao;

    @BeforeEach
    void setUp() {
        pieceDao = new PieceDao(connector);
    }

    @DisplayName("기물 저장 성공")
    @Test
    void addPiece() {
        PieceDto pieceDto = new PieceDto(1, 1, "p", 1L);
        pieceDao.addPiece(pieceDto);
        assertThat(pieceDao.findAll()).contains(pieceDto);
    }
}
