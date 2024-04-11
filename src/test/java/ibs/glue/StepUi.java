package ibs.glue;

import io.cucumber.java.en.Given;
import org.junit.jupiter.api.Assertions;
import ibs.pages.ProductPage;
import ibs.pages.HomePage;
import ibs.tests.BaseTest;

import java.util.Arrays;
import java.util.List;


public class StepUi {
    private final List<String> productList = Arrays.asList("Дуриан", "Фрукт", "true");

    @Given("Загружаем главную страницу сайта")
    public void init() {
        BaseTest.setUp();
    }

    @Given("Переходим в раздел Песочница на главной странице")
    public HomePage goToSandBoxSection() {
        return HomePage.getInstance().switchtodropDown();
    }

    @Given("Выбираем раздел Товары во всплывающем меню")
    public void selectGoodsSection() {
        HomePage.getInstance().foodButtonClick();
    }

    @Given("Инициируем добавление нового товара, нажимая на кнопку Добавить")
    public ProductPage initiateAddingNewProduct() {
        return ProductPage.getInstance().clickAddButton();
    }

    @Given("Вводим название нового товара {string} в форме добавления")
    public ProductPage inputProductName(String productName) {
        return ProductPage.getInstance().enterFruitName(productName);
    }

    @Given("Выбираем тип товара как Фрукт")
    public ProductPage selectProductTypeAsFruit() {
        return ProductPage.getInstance().checkType();
    }

    @Given("Отмечаем товар как Экзотический, активируя соответствующий чекбокс")
    public ProductPage markProductAsExotic() {
        return ProductPage.getInstance().checkBoxClick();
    }

    @Given("Подтверждаем добавление товара, нажимая кнопку Сохранить")
    public void confirmProductAddition() {
        ProductPage.getInstance().saveButtonClick();
    }

    @Given("Убеждаемся, что новый товар отображается в списке товаров")
    public void verifyProductAdditionInList() {
        Assertions.assertEquals(productList, ProductPage.getInstance().getGoodsValueFromTheTable(5));
    }

    @Given("Удаляем добавленный товар, сбрасывая все данные через раздел Песочница")
    public void removeAddedProduct() {
        ProductPage.resetAll();
    }

    @Given("Проверяем, что товар был успешно удален из списка")
    public void verifyProductDeletionFromList() {
        Assertions.assertNotEquals(productList, ProductPage.getInstance().getGoodsValueFromTheTable(4));
    }

    @Given("Завершающий шаг: Закрываем браузер")
    public void closeBrowser() {
        BaseTest.tearDown();
    }
}

