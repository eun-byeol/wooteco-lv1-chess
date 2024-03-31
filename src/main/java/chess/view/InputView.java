package chess.view;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public final class InputView {

    private static final Scanner SCANNER = new Scanner(System.in);
    private static final String COMMAND_MENU = """
        > 게임 시작 : start
        > 게임 종료 : end
        > 게임 이동 : move source위치 target위치 - 예. move b2 b3""";
    private static final String COMMAND_DELIMITER = " ";

    public List<String> askGameCommands() {
        System.out.println(COMMAND_MENU);
        String[] commands = SCANNER.nextLine()
            .strip()
            .split(COMMAND_DELIMITER);
        return Arrays.stream(commands)
            .toList();
    }
}
