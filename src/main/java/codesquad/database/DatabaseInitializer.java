package codesquad.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public final class DataBaseInitializer {

    public static void initialize() {
        try (Connection connection = DriverManager.getConnection(H2Constants.JDBC_URL, H2Constants.USERNAME, H2Constants.PASSWORD);
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(H2Constants.CREATE_USER_TABLE);
            statement.executeUpdate(H2Constants.CREATE_ARTICLE_TABLE);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
