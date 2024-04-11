package ibs.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import ibs.pages.BasePage;
import ibs.pages.ConfigPage;

import java.time.Duration;


public abstract class BaseTest extends BasePage {
    protected static WebDriver driver;

    @BeforeAll
    public static void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        BasePage.setDriver(driver);
        driver.get(ConfigPage.getProperty("base_url"));
    }

    @AfterAll
    public static void tearDown() {
        driver.close();
        driver.quit();
    }
}
