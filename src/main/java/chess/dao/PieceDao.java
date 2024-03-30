package chess.dao;

import chess.dto.PieceDto;
import chess.util.DataBaseConnector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PieceDao {

    private final DataBaseConnector connector;

    public PieceDao(DataBaseConnector connector) {
        this.connector = connector;
    }

    public void addPiece(PieceDto pieceDto) {
        String query = "INSERT INTO piece VALUES(?, ?, ?, ?)";
        try (Connection connection = connector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, pieceDto.row());
            preparedStatement.setInt(2, pieceDto.column());
            preparedStatement.setString(3, pieceDto.piece());
            preparedStatement.setLong(4, pieceDto.chessGameId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("기물 저장 실패");
        }
    }

    public List<PieceDto> findAll() {
        String query = "SELECT * FROM piece";
        try (Connection connection = connector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<PieceDto> pieceDtos = new ArrayList<>();
            while (resultSet.next()) {
                pieceDtos.add(
                    new PieceDto(
                        resultSet.getInt("row"),
                        resultSet.getInt("column"),
                        resultSet.getString("piece"),
                        resultSet.getLong("chessGameId")
                    )
                );
            }
            return pieceDtos;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("기물 전체 조회 실패");
        }
    }
}
