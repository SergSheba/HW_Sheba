package ibs.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Главная страница сайта
 */
public class HomePage extends BasePage {
    private static HomePage INSTANCE;
    @FindBy(xpath = "//a[normalize-space()='Песочница']")
    private WebElement dropDown;

    @FindBy(xpath = "//a[contains(@class, 'dropdown-item') and @href='/food']")
    private WebElement food;

    private HomePage() {
        PageFactory.initElements(driver, this);
    }

    public static HomePage getInstance() {
        if (INSTANCE == null)
            INSTANCE = new HomePage();
        return INSTANCE;
    }

    // Действия по переходу с главной страницы на страницу с товарами
    public HomePage switchtodropDown() {
        if (isElementVisibleAndClickable(dropDown))
            dropDown.click();
        return this;
    }
    public void foodButtonClick() {
        if (isElementVisibleAndClickable(food))
            food.click();
    }

}
