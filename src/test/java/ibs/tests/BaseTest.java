package ibs.tests;

import ibs.pages.BasePage;
import ibs.pages.ConfigPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URI;
import java.time.Duration;

public abstract class BaseTest extends BasePage {
    protected static WebDriver driver;

    @BeforeAll
    public static void setUp() {
        String browserType = System.getProperty("browser", "chrome");  // Дефолтное значение - Chrome
        String useSelenoid = System.getProperty("useSelenoid", "false");  // По умолчанию не использовать Selenoid

        if (Boolean.parseBoolean(useSelenoid)) {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setBrowserName(browserType);
            if (browserType.equals("firefox")) {
                capabilities.setVersion("109.0");  // Указываем версию для Firefox
            } else {
                capabilities.setVersion("109.0");  // Указываем версию для Chrome
            }
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
            if (browserType.equals("firefox")) {
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
            } else {
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
            }
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
