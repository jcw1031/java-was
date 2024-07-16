package codesquad.database;

public abstract class H2Constants {

    public static final String JDBC_URL = "jdbc:h2:~/codestagram;DB_CLOSE_ON_EXIT=FALSE";
    public static final String USERNAME = "sa";
    public static final String PASSWORD = "sa";

    public static final String CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS `user` ("
            + "id BIGINT PRIMARY KEY AUTO_INCREMENT, "
            + "userId VARCHAR(20) NOT NULL, "
            + "nickname VARCHAR(20) NOT NULL, "
            + "password VARCHAR(20) NOT NULL);";
    public static final String CREATE_ARTICLE_TABLE = "CREATE TABLE IF NOT EXISTS `article` ("
            + "id BIGINT PRIMARY KEY AUTO_INCREMENT, "
            + "content TEXT NOT NULL, "
            + "userId BIGINT NOT NULL, "
            + "FOREIGN KEY (userId) REFERENCES `user`(id));";
}
