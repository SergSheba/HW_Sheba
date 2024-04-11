package ibs.pages;

import lombok.Setter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


/**
 * Основной класс страницы, устанавливающий основу для работы с веб-драйвером для всех наследуемых страниц;
 * Включает в себя метод resetAll() для очистки данных, введенных во время тестирования.
 */

public class BasePage {

    @Setter
    protected static WebDriver driver;

    //Проверка, что элемент кликабелен и виден
    public static boolean isElementVisibleAndClickable(WebElement webElement) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.visibilityOf(webElement));
            wait.until(ExpectedConditions.elementToBeClickable(webElement));
            return true;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }


}
