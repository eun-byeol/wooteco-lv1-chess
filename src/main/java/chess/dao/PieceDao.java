package chess.dao;

import chess.dto.PieceDto;
import chess.util.DataBaseConnector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PieceDao {

    private final DataBaseConnector connector;

    public PieceDao(DataBaseConnector connector) {
        this.connector = connector;
    }

    public Long addPiece(PieceDto pieceDto) {
        String query = "INSERT INTO piece VALUES(?, ?, ?, ?, ?)";
        try (Connection connection = connector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, 0);
            preparedStatement.setInt(2, pieceDto.row());
            preparedStatement.setInt(3, pieceDto.column());
            preparedStatement.setString(4, pieceDto.piece());
            preparedStatement.setLong(5, pieceDto.chessGameId());
            preparedStatement.executeUpdate();
            return findLastId();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("기물 저장 실패");
        }
    }

    private Long findLastId() {
        String query = "SELECT MAX(id) lastId FROM piece";
        try (Connection connection = connector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getLong("lastId");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("기물 마지막 id 조회 실패");
        }
        return 0L;
    }

    public Optional<PieceDto> findById(Long id) {
        String query = "SELECT * FROM piece WHERE id = ?";
        try (Connection connection = connector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(
                    new PieceDto(
                        resultSet.getLong("id"),
                        resultSet.getInt("row"),
                        resultSet.getInt("column"),
                        resultSet.getString("piece"),
                        resultSet.getLong("chessGameId")
                    )
                );
            }
            return Optional.empty();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("기물 조회 실패");
        }
    }
}
