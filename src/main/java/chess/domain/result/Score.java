package chess.domain.result;

import java.util.Objects;

public class Score {

    public static final Score MIN = new Score(0);

    private final double score;

    public Score(double score) {
        this.score = score;
    }

    public Score add(Score that) {
        return new Score(score + that.score);
    }

    @Override
    public String toString() {
        return Double.toString(score);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Score score1 = (Score) o;
        return Double.compare(score1.score, score) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(score);
    }
}