package chess.service;

import static chess.domain.Color.BLACK;
import static chess.domain.Color.WHITE;
import static chess.domain.state.Turn.BLACK_TURN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import chess.dao.PieceDao;
import chess.dao.TurnDao;
import chess.domain.Position;
import chess.domain.PromotionPiece;
import chess.domain.piece.Piece;
import chess.domain.piece.pawn.Pawn;
import chess.domain.piece.single.Knight;
import chess.domain.state.Turn;
import chess.testutil.H2Connection;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChessGameServiceTest {

    private PieceDao pieceDao;
    private TurnDao turnDao;
    private ChessGameService chessGameService;

    @BeforeEach
    void setUp() {
        H2Connection.setUpTable();
        pieceDao = new PieceDao(H2Connection.getConnection());
        turnDao = new TurnDao(H2Connection.getConnection());
        chessGameService = new ChessGameService(pieceDao, turnDao);
    }

    @Test
    @DisplayName("이미 진행중인 게임이 있는데 start한 경우 예외발생")
    void startException() {
        turnDao.updateTurn(Turn.END, Turn.WHITE_TURN);

        assertThatThrownBy(() -> chessGameService.start())
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("아직 진행 중인 게임이 있습니다.");
    }

    @Test
    @DisplayName("start 시 체스 보드 생성")
    void start() {
        // when
        chessGameService.start();
        Map<Position, Piece> results = pieceDao.findAllPieces();

        // then
        assertThat(results).hasSize(32);
    }

    @Test
    @DisplayName("Position을 받아 상대 기물이 있는 곳에 move")
    void moveTargetPosition() {
        // given
        turnDao.updateTurn(Turn.END, Turn.WHITE_TURN);
        Position source = Position.of('a', '1');
        Position target = Position.of('b', '2');
        pieceDao.savePieces(Map.of(
                source, new Piece(WHITE, new Pawn(WHITE)),
                target, new Piece(BLACK, new Knight())
        ));

        // when
        chessGameService.move(source, target);
        Piece piece = pieceDao.findAllPieces().get(target);
        Turn turn = turnDao.findCurrentTurn().orElse(null);

        // then
        assertAll(
                () -> assertThat(piece.name()).isEqualTo("pawn"),
                () -> assertThat(piece.color()).isEqualTo(WHITE),
                () -> assertThat(turn).isEqualTo(BLACK_TURN)
        );
    }

    @Test
    @DisplayName("Position을 받아 빈 곳에 move")
    void moveEmptyPosition() {
        // given
        turnDao.updateTurn(Turn.END, Turn.WHITE_TURN);
        Position source = Position.of('a', '1');
        Position target = Position.of('a', '2');
        pieceDao.savePieces(Map.of(
                source, new Piece(WHITE, new Pawn(WHITE))
        ));

        // when
        chessGameService.move(source, target);
        Piece piece = pieceDao.findAllPieces().get(target);
        Turn turn = turnDao.findCurrentTurn().orElse(null);

        // then
        assertAll(
                () -> assertThat(piece.name()).isEqualTo("pawn"),
                () -> assertThat(piece.color()).isEqualTo(WHITE),
                () -> assertThat(turn).isEqualTo(BLACK_TURN)
        );
    }

    @Test
    @DisplayName("pawn 프로모션")
    void promotion() {
        // given
        turnDao.updateTurn(Turn.END, Turn.WHITE_TURN);
        Position source = Position.of('a', '8');
        pieceDao.savePieces(Map.of(
                source, new Piece(WHITE, new Pawn(WHITE))
        ));

        // when
        chessGameService.promotion(PromotionPiece.BISHOP);
        Piece piece = pieceDao.findAllPieces().get(source);
        Turn turn = turnDao.findCurrentTurn().orElse(null);

        // then
        assertAll(
                () -> assertThat(piece.name()).isEqualTo("bishop"),
                () -> assertThat(piece.color()).isEqualTo(WHITE),
                () -> assertThat(turn).isEqualTo(BLACK_TURN)
        );
    }
}