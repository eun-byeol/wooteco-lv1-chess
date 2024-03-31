package chess.model.board;

import chess.model.material.Color;
import chess.model.outcome.ScoreCalculator;
import chess.model.piece.None;
import chess.model.piece.Piece;
import chess.model.position.Position;
import chess.model.position.Route;
import java.util.Map;

public class Board {

    private final Map<Position, Piece> pieces;
    private final Long id;
    private Color turn;

    public Board(Map<Position, Piece> pieces, Color turn) {
        this(pieces, 0L, turn);
    }

    private Board(Map<Position, Piece> pieces, Long id, Color turn) {
        this.pieces = pieces;
        this.id = id;
        this.turn = turn;
    }

    public boolean move(Position source, Position target) {
        validatePosition(source, target);
        validateMovement(source, target);
        return applyMove(source, target);
    }

    private boolean applyMove(Position source, Position target) {
        Piece targetPiece = pieces.get(target);
        pieces.put(target, findPiece(source));
        pieces.put(source, None.of());
        turn = turn.rotate();
        return targetPiece.isKing();
    }

    private void validatePosition(Position source, Position target) {
        Piece sourcePiece = findPiece(source);
        if (sourcePiece.isNone()) {
            throw new IllegalArgumentException("source 위치에 기물이 존재하지 않습니다.");
        }
        if (sourcePiece.isDifferentColor(turn)) {
            throw new IllegalArgumentException("지금은 " + turn.name() + " 차례입니다.");
        }
        if (findPiece(target).isAllyWith(sourcePiece)) {
            throw new IllegalArgumentException("target 위치에 내 기물이 존재합니다.");
        }
    }

    private void validateMovement(Position source, Position target) {
        Piece sourcePiece = findPiece(source);
        Piece targetPiece = findPiece(target);
        if (sourcePiece.isEnemyWith(targetPiece) && sourcePiece.canAttack(source, target)) {
            return;
        }
        Route route = sourcePiece.findRoute(source, target);
        if (route.isBlocked(pieces)) {
            throw new IllegalArgumentException("경로 상에 다른 기물이 존재합니다.");
        }
    }

    public ScoreCalculator calculateScore() {
        return new ScoreCalculator(pieces);
    }

    public Color lastTurn() {
        return turn.rotate();
    }

    public String turnName() {
        return turn.name();
    }

    public Piece findPiece(Position position) {
        return pieces.get(position);
    }

    public Long getId() {
        return id;
    }

    public Board setId(Long gameId) {
        return new Board(pieces, gameId, turn);
    }
}
