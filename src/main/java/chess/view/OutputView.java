package chess.view;

import chess.dto.BoardDto;
import chess.dto.ColorScoreDto;
import chess.dto.RankDto;
import java.util.List;

public final class OutputView {

    private static final String NEWLINE = System.lineSeparator();
    private static final CharSequence RANK_DELIMITER = "";
    private static final String SCORE_STATUS_INTRO = NEWLINE + "## 점수 집계";
    private static final String SCORE_FORMAT = "%s : %.1f점" + NEWLINE;
    private static final String ERROR_PREFIX = "[ERROR] ";

    public void printException(Exception e) {
        System.out.println(ERROR_PREFIX + e.getMessage() + NEWLINE);
    }

    public void printChessBoard(BoardDto boardDto) {
        boardDto.getRanks()
            .forEach(this::printRank);
    }

    private void printRank(RankDto rankDto) {
        String rank = String.join(RANK_DELIMITER, rankDto.getRank());
        System.out.println(rank);
    }

    public void printScoreStatus(List<ColorScoreDto> scores) {
        System.out.println(SCORE_STATUS_INTRO);
        scores.forEach(this::printScore);
    }

    private void printScore(ColorScoreDto colorScoreDto) {
        System.out.printf(SCORE_FORMAT, colorScoreDto.color(), colorScoreDto.score());
    }
}
