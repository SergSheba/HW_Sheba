package ibs.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import ibs.pages.ProductPage;
import ibs.pages.HomePage;

import java.util.Arrays;
import java.util.List;

public class DelTest extends BaseTest {
    private final List<String> expectedProductDetails = Arrays.asList("Дуриан", "Фрукт", "true");

    @DisplayName("Тестирование процесса добавления и удаления продукта")
    public void verifyProductAdditionAndDeletion() {
        String productName = "Дуриан";
        HomePage.getInstance()
                .switchtodropDown()
                .foodButtonClick();
        ProductPage.getInstance()
                .clickAddButton()
                .enterFruitName(productName)
                .checkType()
                .checkBoxClick()
                .saveButtonClick();
        Assertions.assertEquals(expectedProductDetails, ProductPage.getInstance().checkProduct(),
                "Несоответствие данных: продукт не был добавлен.");
        ProductPage.resetAll();
        Assertions.assertNotEquals(expectedProductDetails, ProductPage.getInstance().getGoodsValueFromTheTable(4),
                "Данные совпадают: продукт не был удален.");
    }
}
