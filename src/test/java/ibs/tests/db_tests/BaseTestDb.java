package ibs.tests.db_tests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import ibs.pages.ConfigPage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BaseTestDb {
    protected static Connection connection;

    @BeforeAll
    public static void init() throws SQLException {
        connection = getNewConnection();
    }

    @AfterAll
    public static void close() throws SQLException {
        connection.close();
    }

    protected static Connection getNewConnection() throws SQLException {
        return DriverManager.getConnection(ConfigPage.getProperty("selenoid.url.db"),
                ConfigPage.getProperty("db_login"), ConfigPage.getProperty("db_password"));
    }
}
