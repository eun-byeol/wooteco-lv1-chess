package chess.dao;

import chess.db.DataBaseConnector;
import chess.dto.ChessGameDto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ChessGameDao {

    private static final Long AUTO_INCREMENT_DEFAULT = 0L;

    private final DataBaseConnector connector;

    public ChessGameDao(DataBaseConnector connector) {
        this.connector = connector;
    }

    public Long add(ChessGameDto chessGameDto) {
        String query = "INSERT INTO chessgame VALUES(?, ?, ?)";
        try (Connection connection = connector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, AUTO_INCREMENT_DEFAULT);
            preparedStatement.setString(2, chessGameDto.turn());
            preparedStatement.setString(3, chessGameDto.pieces());
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
            return getLastIdFrom(resultSet);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("체스 게임 마지막 id 조회 실패");
        }
    }

    private long getLastIdFrom(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            return resultSet.getLong("lastId");
        }
        return AUTO_INCREMENT_DEFAULT;
    }

    public Optional<ChessGameDto> findById(Long id) {
        String query = "SELECT * FROM chessgame WHERE id = ?";
        try (Connection connection = connector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return getChessGameDtoFrom(resultSet);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("체스 게임 조회 실패");
        }
    }

    private Optional<ChessGameDto> getChessGameDtoFrom(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            return Optional.of(createChessGameDto(resultSet));
        }
        return Optional.empty();
    }

    private ChessGameDto createChessGameDto(ResultSet resultSet) throws SQLException {
        return new ChessGameDto(
            resultSet.getLong("id"),
            resultSet.getString("turn"),
            resultSet.getString("pieces")
        );
    }

    public List<ChessGameDto> findAll() {
        String query = "SELECT * FROM chessgame";
        try (Connection connection = connector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            return getAllChessGameDtoFrom(resultSet);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("체스 게임 전체 조회 실패");
        }
    }

    private List<ChessGameDto> getAllChessGameDtoFrom(ResultSet resultSet) throws SQLException {
        List<ChessGameDto> chessGameDtos = new ArrayList<>();
        while (resultSet.next()) {
            chessGameDtos.add(createChessGameDto(resultSet));
        }
        return chessGameDtos;
    }

    public void update(ChessGameDto chessGameDto) {
        String query = "UPDATE chessgame SET turn = ?, pieces = ? WHERE id = ?";
        try (Connection connection = connector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, chessGameDto.turn());
            preparedStatement.setString(2, chessGameDto.pieces());
            preparedStatement.setLong(3, chessGameDto.id());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("체스 게임 수정 실패");
        }
    }

    public void delete(Long id) {
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
