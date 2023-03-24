package chess.domain.square;

import chess.domain.exception.FileNotFoundException;
import chess.domain.exception.InvalidSquareIndexException;

import java.util.Arrays;

public enum File {

    A('a'),
    B('b'),
    C('c'),
    D('d'),
    E('e'),
    F('f'),
    G('g'),
    H('h');

    private final char value;

    File(final char value) {
        this.value = value;
    }

    public static File from(final char value) {
        return Arrays.stream(File.values())
                .filter(file -> file.value == value)
                .findFirst()
                .orElseThrow(FileNotFoundException::new);
    }

    public static File getFileByIndex(final int index) {
        return Arrays.stream(values())
                .filter(file -> file.value == file.getValueFrom(index))
                .findFirst()
                .orElseThrow(InvalidSquareIndexException::new);
    }

    private int getValueFrom(final int index) {
        return index + A.value;
    }

    public File move(int difference) {
        try {
            return File.from((char) (value + difference));
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("File의 범위를 벗어났습니다.");
        }
    }

    public char getValue() {
        return value;
    }

    public int getDifference(final File other) {
        return other.value - value;
    }
}