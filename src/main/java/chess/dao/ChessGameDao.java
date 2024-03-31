package chess.dao;

import chess.dto.ChessGameDto;
import chess.util.DataBaseConnector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ChessGameDao {

    private final DataBaseConnector connector;

    public ChessGameDao(DataBaseConnector connector) {
        this.connector = connector;
    }

    public Long addChessGame(ChessGameDto chessGameDto) {
        String query = "INSERT INTO chessgame VALUES(?, ?)";
        try (Connection connection = connector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, "0");
            preparedStatement.setString(2, chessGameDto.turn());
            preparedStatement.executeUpdate();
            return findLastId();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("체스 게임 저장 실패");
        }
    }

    private Long findLastId() {
        String query = "SELECT MAX(id) lastId FROM chessgame";
        try (Connection connection = connector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getLong("lastId");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("체스 게임 마지막 id 조회 실패");
        }
        return 0L;
    }

    public Optional<ChessGameDto> findById(Long id) {
        String query = "SELECT * FROM chessgame WHERE id = (?)";
        try (Connection connection = connector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, String.valueOf(id));
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(
                    new ChessGameDto(
                        resultSet.getLong("id"),
                        resultSet.getString("turn")
                    )
                );
            }
            return Optional.empty();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("체스 게임 조회 실패");
        }
    }

    public List<ChessGameDto> findAll() {
        String query = "SELECT * FROM chessgame";
        try (Connection connection = connector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<ChessGameDto> chessGameDtos = new ArrayList<>();
            while (resultSet.next()) {
                chessGameDtos.add(
                    new ChessGameDto(
                        resultSet.getLong("id"),
                        resultSet.getString("turn")
                    )
                );
            }
            return chessGameDtos;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("체스 게임 전체 조회 실패");
        }
    }

    public void updateChessGame(ChessGameDto chessGameDto) {
        String query = "UPDATE chessgame SET turn = ? WHERE id = ?";
        try (Connection connection = connector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, chessGameDto.turn());
            preparedStatement.setLong(2, chessGameDto.id());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("체스 게임 수정 실패");
        }
    }

    public void deleteChessGame(Long id) {
        String query = "DELETE FROM chessgame WHERE id = ?";
        try (Connection connection = connector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("체스 게임 삭제 실패");
        }
    }
}
