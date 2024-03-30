package chess.dao;

import static org.assertj.core.api.Assertions.assertThat;

import chess.dto.PieceDto;
import chess.util.DataBaseConnector;
import java.util.List;
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
        PieceDto pieceDto = new PieceDto(null, 1, 1, "p", 1L);
        Long id = pieceDao.addPiece(pieceDto);
        assertThat(pieceDao.findById(id)).isPresent();
    }

    @DisplayName("체스 게임별 전체 기물 조회 성공")
    @Test
    void findAllByChessGameId() {
        PieceDto firstPiece = new PieceDto(null, 1, 1, "p", 2L);
        PieceDto secondPiece = new PieceDto(null, 1, 2, "P", 3L);
        Long firstPieceId = pieceDao.addPiece(firstPiece);
        Long secondPieceId = pieceDao.addPiece(secondPiece);

        List<PieceDto> pieceDtos = pieceDao.findAllByChessGameId(2L);

        assertThat(pieceDtos.stream().map(PieceDto::id))
            .contains(firstPieceId)
            .doesNotContain(secondPieceId);
    }
}
