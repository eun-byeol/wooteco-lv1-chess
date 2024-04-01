package chess.model.outcome;

import static chess.model.material.Color.BLACK;
import static chess.model.material.Color.NONE;
import static chess.model.material.Color.WHITE;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class WinnerTest {

    @DisplayName("점수가 높은 진영이 승리한다")
    @Test
    void selectWinner() {
        ColorScore white = new ColorScore(WHITE, 19.5);
        ColorScore black = new ColorScore(BLACK, 19);

        Winner Winner = new Winner(white, black);
        assertThat(Winner.getColor()).isEqualTo(WHITE);
    }

    @DisplayName("점수가 동일하면 무승부이다")
    @Test
    void checkDrawWithSameScores() {
        ColorScore white = new ColorScore(WHITE, 19.5);
        ColorScore black = new ColorScore(BLACK, 19.5);

        Winner winner = new Winner(white, black);
        assertThat(winner.getColor()).isEqualTo(NONE);
    }
}
