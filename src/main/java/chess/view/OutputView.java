package chess.view;

import chess.dto.BoardDto;
import chess.dto.ColorScoreDto;
import chess.dto.RankDto;
import chess.dto.WinnerDto;
import java.util.Arrays;

public final class OutputView {

    private static final String NEWLINE = System.lineSeparator();
    private static final String CHESS_GAME_INTRO = "> 체스 게임을 시작합니다.";
    private static final String CONTINUE_GAME_INTRO = NEWLINE + "## 진행 중인 게임을 불러왔습니다." + NEWLINE;
    private static final CharSequence RANK_DELIMITER = "";
    private static final String SCORE_STATUS_INTRO = NEWLINE + "## 점수 집계";
    private static final String SCORE_FORMAT = "%s : %.1f점" + NEWLINE;
    private static final String RESULT_STATUS_INTRO = "## 게임 결과";
    private static final String WINNER_FORMAT = "%s 승리" + NEWLINE;
    private static final String DRAW_FORMAT = "무승부";
    private static final String SAVE_GAME = "## 게임이 종료되지 않아 저장했습니다.";
    private static final String ERROR_PREFIX = "[ERROR] ";

    public void printGameIntro() {
        System.out.println(CHESS_GAME_INTRO);
    }

    public void printContinueGame() {
        System.out.println(CONTINUE_GAME_INTRO);
    }

    public void printChessBoard(BoardDto boardDto) {
        boardDto.getRanks()
            .forEach(this::printRank);
        System.out.println();
    }

    private void printRank(RankDto rankDto) {
        String rank = String.join(RANK_DELIMITER, rankDto.rank());
        System.out.println(rank);
    }

    public void printScoreStatus(ColorScoreDto... colorScoreDto) {
        System.out.println(SCORE_STATUS_INTRO);
        Arrays.stream(colorScoreDto)
            .forEach(this::printScore);
        System.out.println();
    }

    private void printScore(ColorScoreDto colorScoreDto) {
        System.out.printf(SCORE_FORMAT, colorScoreDto.color(), colorScoreDto.score());
    }

    public void printGameResult(WinnerDto resultDto) {
        System.out.println(RESULT_STATUS_INTRO);
        if (resultDto.isDraw()) {
            System.out.println(DRAW_FORMAT);
            return;
        }
        System.out.printf(WINNER_FORMAT, resultDto.winner());
    }

    public void printSaveGame() {
        System.out.println(SAVE_GAME);
    }

    public void printException(Exception e) {
        System.out.println(ERROR_PREFIX + e.getMessage() + NEWLINE);
    }
}
