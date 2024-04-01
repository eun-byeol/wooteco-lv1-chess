package chess.dao;

import chess.dto.ChessGameDto;
import java.util.List;
import java.util.Optional;

public interface ChessGameDao {

    Long add(ChessGameDto chessGameDto);

    Optional<ChessGameDto> findById(Long id);

    List<ChessGameDto> findAll();

    void update(ChessGameDto chessGameDto);

    void delete(Long id);
}
