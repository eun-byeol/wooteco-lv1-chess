package chess.model.outcome;

import static chess.model.material.Color.BLACK;
import static chess.model.material.Color.WHITE;
import static org.assertj.core.api.Assertions.assertThat;

import chess.dto.ColorScoreDto;
import chess.dto.WinnerDto;
import chess.dto.mapper.ColorMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class WinnerTest {

    @DisplayName("점수가 높은 진영이 승리한다")
    @Test
    void selectWinner() {
        ColorScoreDto white = ColorScoreDto.of(WHITE, 19.5);
        ColorScoreDto black = ColorScoreDto.of(BLACK, 19);

        WinnerDto winnerDto = Winner.of(white, black);
        assertThat(winnerDto.winner()).isEqualTo(ColorMapper.WHITE.getDisplayName());
    }

    @DisplayName("점수가 동일하면 무승부이다")
    @Test
    void checkDrawWithSameScores() {
        ColorScoreDto white = ColorScoreDto.of(WHITE, 19.5);
        ColorScoreDto black = ColorScoreDto.of(BLACK, 19.5);

        WinnerDto winnerDto = Winner.of(white, black);
        assertThat(winnerDto.isDraw()).isTrue();
    }
}
