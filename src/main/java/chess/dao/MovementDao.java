package chess.dao;

import chess.dto.MovementDto;
import java.util.Optional;

public interface MovementDao {

    Long add(MovementDto movementDto);

    Optional<MovementDto> findLatestByGameId(Long gameId);

    void deleteAllByGameId(Long gameId);
}
