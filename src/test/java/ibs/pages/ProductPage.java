package ibs.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Страница управления товарами, предоставляющая функциональность для добавления новых продуктов.
 */

public class ProductPage extends BasePage {
    private static ProductPage INSTANCE;
    @FindBy(xpath = "//button[text()='Добавить']")
    private WebElement addButton;

    @FindBy(xpath = "//input[@placeholder='Наименование']")
    private WebElement nameProd;

    @FindBy(xpath = "//*[@id = 'type']")
    private WebElement type;

    @FindBy(xpath = "//select[@id='type']/option[@value='FRUIT']")
    private WebElement typeFruit;

    @FindBy(xpath = "//*[@class='form-check-input']")
    private WebElement checkBox;

    @FindBy(xpath = "//button[text()='Сохранить']")
    private WebElement buttonSave;

    @FindBy(xpath = "//*[text()='5']/following-sibling::*")
    private WebElement newSandBoxLine;

    @FindBy(xpath = "//a[contains(text(), 'Песочница')]")
    private static WebElement buttonNav;
    @FindBy(xpath = "//a[text()='Сброс данных']")
    private static WebElement buttonReset;

    private ProductPage() {
        PageFactory.initElements(driver, this);
    }

    public static ProductPage getInstance() {
        if (INSTANCE == null)
            INSTANCE = new ProductPage();
        return INSTANCE;
    }

    //Добавляем новый товар
    public ProductPage clickAddButton() {
        // Проверка, наличия кнопки для добавления товара
        if (isElementVisibleAndClickable(addButton))
            addButton.click();
        return this;
    }

    public ProductPage enterFruitName(String fruitName) {
        if (isElementVisibleAndClickable(nameProd))
            nameProd.sendKeys(fruitName);
        return this;
    }

    public ProductPage checkType() {
        type.click();
        typeFruit.click();
        return this;
    }

    public ProductPage checkBoxClick() {
        checkBox.click();
        return this;
    }

    public void saveButtonClick() {
        // Проверка, наличия кнопки сохранения введенных данных
        if (isElementVisibleAndClickable(buttonSave))
            buttonSave.click();
        try {
            Thread.sleep(1500L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // Извлекаем список товаров и проверяем соответствие ожидаемым значениям: Дуриан, Фрукт, Экзотический (true)
    public List<String> checkProduct() {
        List<String> newProductList = new ArrayList<>();
        if (isElementVisibleAndClickable(newSandBoxLine)) {
            newProductList = getGoodsValueFromTheTable(5);
        }
        return newProductList;
    }

    // Извлечение информации о товарах из таблицы для последующей верификации
    public List<String> getGoodsValueFromTheTable(int number) {
        List<WebElement> goodsList = driver.findElements
                (By.xpath("//*[text()='" + number + "']/following-sibling::*"));
        return Arrays.asList(goodsList.get(0).getText(),
                goodsList.get(1).getText(),
                goodsList.get(2).getText());
    }

    // Постусловие: удаление данных
    public static void resetAll() {
        if (isElementVisibleAndClickable(buttonNav))
            buttonNav.click();
        if (isElementVisibleAndClickable(buttonReset))
            buttonReset.click();
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
