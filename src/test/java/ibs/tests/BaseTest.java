package ibs.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import ibs.pages.BasePage;
import ibs.pages.ConfigPage;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URI;
import java.time.Duration;


public abstract class BaseTest extends BasePage {
    protected static WebDriver driver;

    @BeforeAll
    public static void setUp() {
        // Проверяем, следует ли использовать Selenoid
        String useSelenoid = System.getProperty("useSelenoid", "false");

        if (Boolean.parseBoolean(useSelenoid)) {
            // Конфигурация для Selenoid
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setBrowserName("firefox");
            capabilities.setVersion("109.0");
            capabilities.setCapability("enableVNC", true);
            capabilities.setCapability("enableVideo", false);
            try {
                driver = new RemoteWebDriver(
                        URI.create(ConfigPage.getProperty("selenoid.url")).toURL(),
                        capabilities
                );
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        } else {
            // Локальный запуск через ChromeDriver
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        BasePage.setDriver(driver);
        driver.get(ConfigPage.getProperty("base_url"));
    }

    @AfterAll
    public static void tearDown() {
        if (driver != null) {
            driver.close();
            driver.quit();
        }
    }
}

