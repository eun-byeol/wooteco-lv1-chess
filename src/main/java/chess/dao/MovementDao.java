package chess.dao;

import chess.dto.MovementDto;
import java.util.List;
import java.util.Optional;

public interface MovementDao {

    Long add(MovementDto movementDto);

    Optional<MovementDto> findLatestByGameId(Long gameId);

    List<MovementDto> findAllSortedByTime();

    void deleteAllByGameId(Long gameId);
}
