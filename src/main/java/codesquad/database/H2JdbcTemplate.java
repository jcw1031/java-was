package codesquad.database;

import org.h2.jdbcx.JdbcConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class H2JdbcTemplate implements JdbcTemplate {

    private static H2JdbcTemplate instance;

    private final JdbcConnectionPool connectionPool;

    private H2JdbcTemplate() {
        connectionPool = JdbcConnectionPool.create(H2Constants.JDBC_URL, H2Constants.USERNAME, H2Constants.PASSWORD);
    }

    public static H2JdbcTemplate getInstance() {
        if (instance == null) {
            instance = new H2JdbcTemplate();
        }
        return instance;
    }

    @Override
    public void update(String sql, Object... arguments) {
        execute(statement -> {
            try {
                return statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }, sql, arguments);
    }

    @Override
    public <T> T queryForObject(String sql, Class<T> clazz, Object... arguments) {
        return execute(statement -> {
            try (ResultSet resultSet = statement.executeQuery()) {
                return RowMapper.mapRow(resultSet, clazz);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }, sql, arguments);
    }

    @Override
    public <T> List<T> queryForList(String sql, Class<T> clazz, Object... arguments) {
        return execute(statement -> {
            try (ResultSet resultSet = statement.executeQuery()) {
                return RowMapper.mapRows(resultSet, clazz);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }, sql, arguments);
    }

    private <T> T execute(Function<PreparedStatement, T> function, String sql, Object... arguments) {
        if (Objects.isNull(sql) || sql.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] SQL 문이 비어있습니다.");
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < arguments.length; i++) {
                preparedStatement.setObject(i + 1, arguments[i]);
            }
            return function.apply(preparedStatement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeStatement(preparedStatement);
            releaseConnection(connection);
        }
    }

    private void releaseConnection(Connection connection) {
        try {
            if (Objects.nonNull(connection)) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void closeStatement(Statement statement) {
        try {
            if (Objects.nonNull(statement)) {
                statement.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
