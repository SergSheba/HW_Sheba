package ibs.glue;

import io.cucumber.java.en.Given;
import org.junit.jupiter.api.Assertions;
import ibs.pages.ConfigPage;

import java.sql.*;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


public class StepDb {

    private static final String QUERY = "SELECT * FROM FOOD;";
    private static Connection connection;
    private static final String ADD_REQUEST = "INSERT INTO FOOD (FOOD_NAME, FOOD_TYPE, FOOD_EXOTIC) " +
            "VALUES (?, ?, ?);";
    private static final String DELETE_REQUEST = "DELETE FROM FOOD WHERE FOOD_NAME = 'Ананас'";

    @Given("Инициируем подключение к базе данных")
    public void init() throws SQLException {
        connection = DriverManager.getConnection(ConfigPage.getProperty("db_url"),
                ConfigPage.getProperty("db_login"), ConfigPage.getProperty("db_password"));
    }

    @Given("Убеждаемся, что подключение к базе данных успешно установлено")
    public void checkConnection() throws SQLException {
        assertTrue(connection.isValid(1), "Подключение к базе данных отсутствует");
    }

    @Given("Убеждаемся в отсутствии в базе данных дубликатов нового продукта")
    public void checkNewGood() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet set = statement.executeQuery(QUERY);
        set.first();
        do {
            assertNotEquals("Ананас", set.getString("FOOD_NAME"));
        } while (set.next());
    }

    @Given("Добавляем новый продукт в базу данных, заполняя указанные поля:")
    public void addNewGood(Map<String, String> requestParams) throws SQLException {
        PreparedStatement add = connection.prepareStatement(ADD_REQUEST);
        add.setString(1, requestParams.get("FOOD_NAME"));
        add.setString(2, requestParams.get("FOOD_TYPE"));
        add.setInt(3, Integer.parseInt(requestParams.get("FOOD_EXOTIC")));
        add.executeUpdate();
    }

    @Given("Подтверждаем добавление продукта в базу данных")
    public void checkAddNewGood() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet afterUpdSelectResult = statement.executeQuery(QUERY);
        afterUpdSelectResult.last();
        Assertions.assertAll("Проверка совпадения данных последнего добавленного продукта с заданными значениями",
                () -> assertEquals(afterUpdSelectResult.getString("FOOD_NAME"), "Ананас"),
                () -> assertEquals(afterUpdSelectResult.getString("FOOD_TYPE"), "FRUIT"),
                () -> assertEquals(afterUpdSelectResult.getInt("FOOD_EXOTIC"), 1)
        );
    }

    @Given("Удаляем из базы данных ранее добавленный продукт")
    public void deleteNewGood() throws SQLException {
        PreparedStatement statement = connection.prepareStatement(DELETE_REQUEST);
        statement.execute();
    }

    @Given("Убеждаемся, что продукт был успешно удален из базы данных")
    public void checkDeleteNewGood() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet afterDeleteResult = statement.executeQuery(QUERY);
        afterDeleteResult.last();

        Assertions.assertFalse("Ананас".equals(afterDeleteResult.getString("FOOD_NAME"))
                        && ("FRUIT".equals(afterDeleteResult.getString("FOOD_TYPE")))
                        && (Integer.valueOf(1).equals(afterDeleteResult.getInt("FOOD_EXOTIC"))),
                "Продукт не был удален");
    }

    @Given("Завершающий шаг: закрываем подключение к базе данных")
    public static void close() throws SQLException {
        connection.close();
    }

}
