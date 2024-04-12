package chess.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class DaoTemplate {

    public void executeUpdate(String query, String errorMessage, Object... parameters) {
        try (Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            setParameters(preparedStatement, parameters);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(errorMessage);
        }
    }

    protected abstract Connection getConnection();

    private void setParameters(PreparedStatement preparedStatement, Object... parameters)
        throws SQLException {
        for (int i = 0; i < parameters.length; i++) {
            preparedStatement.setString(i + 1, String.valueOf(parameters[i]));
        }
    }

    public <T> Optional<T> executeQueryForSingleData(
        String query,
        String errorMessage,
        ResultSetMapper<T> extractData,
        Object... parameters
    ) {
        try (Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            setParameters(preparedStatement, parameters);
            ResultSet resultSet = preparedStatement.executeQuery();
            return extractSingle(resultSet, extractData);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(errorMessage);
        }
    }

    private <T> Optional<T> extractSingle(ResultSet resultSet, ResultSetMapper<T> extractData)
        throws SQLException {
        if (resultSet.next()) {
            return Optional.of(extractData.map(resultSet));
        }
        return Optional.empty();
    }

    public <T> List<T> executeQueryForMultiData(
        String query,
        String errorMessage,
        ResultSetMapper<T> extractData,
        Object... parameters
    ) {
        try (Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            setParameters(preparedStatement, parameters);
            ResultSet resultSet = preparedStatement.executeQuery();
            return extractMultitude(resultSet, extractData);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(errorMessage);
        }
    }

    private <T> List<T> extractMultitude(ResultSet resultSet, ResultSetMapper<T> extractData)
        throws SQLException {
        List<T> data = new ArrayList<>();
        while (resultSet.next()) {
            data.add(extractData.map(resultSet));
        }
        return data;
    }
}
