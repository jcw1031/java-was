package codesquad.database;

import codesquad.model.Article;

public final class ArticleRepository {

    private static ArticleRepository instance;

    private final JdbcTemplate jdbcTemplate = JdbcTemplate.getInstance();

    private ArticleRepository() {
    }

    public static ArticleRepository getInstance() {
        if (instance == null) {
            instance = new ArticleRepository();
        }
        return instance;
    }

    public void save(Article article) {
        String sql = "INSERT INTO article (content, userId) VALUES (?, ?)";
        jdbcTemplate.update(sql, article.getContent(), article.getUserId());
    }
}
