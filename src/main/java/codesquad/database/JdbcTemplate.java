package codesquad.database;

import java.util.List;

public interface JdbcTemplate {

    void update(String sql, Object... arguments);

    <T> T queryForObject(String sql, Class<T> clazz, Object... arguments);

    <T> List<T> queryForList(String sql, Class<T> clazz, Object... arguments);
}
