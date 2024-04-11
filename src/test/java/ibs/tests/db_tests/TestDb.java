package ibs.tests.db_tests;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;


public class TestDb extends BaseTestDb {
    /**
     * Подтверждение возможности соединения с базой данных.
     */
    @DisplayName("Удостоверение в возможности соединения с базой данных")
    public void verifyDatabaseConnection() throws SQLException {
        assertTrue(connection.isValid(1), "Отсутствует подключение к БД");
        assertFalse(connection.isClosed(), "Соединение с БД должно быть активным");
    }

    /**
     * Тестирование процесса добавления и удаления записи в базе данных.
     */
    @DisplayName("Тест добавления нового товара в БД и его последующего удаления")
    public void testAddAndRemoveProductInDatabase() throws SQLException {
        String selectionQuery = "SELECT * FROM FOOD;";
        String insertQuery = "INSERT INTO FOOD (FOOD_NAME, FOOD_TYPE, FOOD_EXOTIC) VALUES (?, ?, ?);";
        String deleteQuery = "DELETE FROM FOOD WHERE FOOD_NAME = 'Ананас';";

        // Подготовка к добавлению записи в БД
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(selectionQuery);
        resultSet.first();
        // Удостоверение, что товар отсутствует перед добавлением
        do {
            assertNotEquals("Ананас", resultSet.getString("FOOD_NAME"));
        } while (resultSet.next());

        PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
        insertStatement.setString(1, "Ананас");
        insertStatement.setString(2, "FRUIT");
        insertStatement.setInt(3, 1);
        insertStatement.executeUpdate();

        // Проверка наличия добавленной записи
        ResultSet postInsertResult = statement.executeQuery(selectionQuery);
        postInsertResult.last();
        Assertions.assertAll("Проверка соответствия данных добавленной записи",
                () -> assertEquals(postInsertResult.getString("FOOD_NAME"), "Ананас"),
                () -> assertEquals(postInsertResult.getString("FOOD_TYPE"), "FRUIT"),
                () -> assertEquals(postInsertResult.getInt("FOOD_EXOTIC"), 1)
        );

        // Удаление добавленной записи
        statement.execute(deleteQuery);
        ResultSet postDeleteResult = statement.executeQuery(selectionQuery);
        postDeleteResult.last();

        // Подтверждение удаления записи
        Assertions.assertFalse("Ананас".equals(postDeleteResult.getString("FOOD_NAME"))
                        && "FRUIT".equals(postDeleteResult.getString("FOOD_TYPE"))
                        && Integer.valueOf(1).equals(postDeleteResult.getInt("FOOD_EXOTIC")),
                "Запись не была удалена из БД");
    }

}
