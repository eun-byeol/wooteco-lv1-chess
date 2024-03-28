package chess.model.score;

import static chess.model.material.Color.WHITE;
import static org.assertj.core.api.Assertions.assertThat;

import chess.dto.PieceMapper;
import chess.model.piece.Piece;
import chess.model.position.Column;
import chess.model.position.Position;
import chess.model.position.Row;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ScoreCalculatorTest {

    @DisplayName("White 기물의 총 점수를 구한다")
    @Test
    void calculateWhiteTotalScore() {
        List<String> snapShot = List.of(
            ".KR.....",
            "P.PB....",
            ".P..Q...",
            "........",
            ".....nq.",
            ".....p.p",
            ".....pp.",
            "....rk.."
        );
        ScoreCalculator scoreCalculator = generate(snapShot);
        assertThat(scoreCalculator.calculate(WHITE)).isEqualTo(19.5);
    }

    private ScoreCalculator generate(List<String> snapShot) {
        Map<Position, Piece> pieces = new HashMap<>();
        for (int i = 0; i < 8; i++) {
            String rank = snapShot.get(i);
            for (int j = 0; j < 8; j++) {
                String pieceName = String.valueOf(rank.charAt(j));
                Piece piece = PieceMapper.deserialize(pieceName);
                Column column = Column.findColumn(j);
                Row row = Row.findRow(i);
                pieces.put(new Position(column, row), piece);
            }
        }
        return new ScoreCalculator(pieces);
    }
}
