package chess.domain.board;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public enum File {
    A(1),
    B(2),
    C(3),
    D(4),
    E(5),
    F(6),
    G(7),
    H(8);

    private final int value;

    File(final int value) {
        this.value = value;
    }

    public int calculateDistance(final File otherFile) {
        return Math.abs(otherFile.value - value);
    }

    public List<File> getFilesInRange(final File otherFile) {
        int max = Math.max(value, otherFile.value);
        int min = Math.min(value, otherFile.value);

        final List<File> files = Arrays.stream(values())
                .filter(file -> file.value > min && file.value < max)
                .collect(Collectors.toList());
        if (value > otherFile.value) {
            Collections.reverse(files);
        }
        return files;
    }
}
