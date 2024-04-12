package chess.dto.mapper;

import static chess.model.material.Color.BLACK;
import static chess.model.material.Color.WHITE;

import chess.model.material.Color;
import chess.model.piece.Bishop;
import chess.model.piece.King;
import chess.model.piece.Knight;
import chess.model.piece.None;
import chess.model.piece.Pawn;
import chess.model.piece.Piece;
import chess.model.piece.Queen;
import chess.model.piece.Rook;
import java.util.Arrays;
import java.util.function.Function;

public enum PieceMapper {

    PAWN("p", "P", Pawn::of),
    ROOK("r", "R", Rook::of),
    KNIGHT("n", "N", Knight::of),
    BISHOP("b", "B", Bishop::of),
    QUEEN("q", "Q", Queen::of),
    KING("k", "K", King::of),
    NONE(".", ".", color -> None.of());

    private final String whitePiece;
    private final String blackPiece;
    private final Function<Color, Piece> generate;

    PieceMapper(String whitePiece, String blackPiece, Function<Color, Piece> generate) {
        this.whitePiece = whitePiece;
        this.blackPiece = blackPiece;
        this.generate = generate;
    }

    public static String serialize(Piece piece) {
        PieceMapper mapper = findPieceMapper(piece);
        Color color = Color.findColor(piece);
        if (color.isSame(WHITE)) {
            return mapper.whitePiece;
        }
        return mapper.blackPiece;
    }

    private static PieceMapper findPieceMapper(Piece piece) {
        Color color = Color.findColor(piece);
        return Arrays.stream(values())
            .filter(pieceMapper -> pieceMapper.generate.apply(color) == piece)
            .findFirst()
            .orElse(NONE);
    }

    public static Piece deserialize(String pieceName) {
        for (PieceMapper mapper : values()) {
            if (mapper.whitePiece.equals(pieceName)) {
                return mapper.generate.apply(WHITE);
            }
            if (mapper.blackPiece.equals(pieceName)) {
                return mapper.generate.apply(BLACK);
            }
        }
        return NONE.generate.apply(Color.NONE);
    }
}
