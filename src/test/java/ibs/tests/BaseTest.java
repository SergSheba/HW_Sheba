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
import java.util.Map;

public abstract class BaseTest extends BasePage {
    protected static WebDriver driver;

    @BeforeAll
    public static void setUp() {
        if ("remote".equalsIgnoreCase(ConfigPage.getProperty("type_driver"))) {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setBrowserName(ConfigPage.getProperty("type_browser"));
            capabilities.setVersion("109.0");
            capabilities.setCapability("selenoid:options", Map.<String, Object>of(
                    "enableVNC", true,
                    "enableVideo", false
            ));
            try {
                driver = new RemoteWebDriver(URI.create(ConfigPage.getProperty("selenoid_url")).toURL(),
                        capabilities, true);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
            driver.manage().window().maximize();
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            BasePage.setDriver(driver);
            driver.get(ConfigPage.getProperty("remote_url"));

        } else {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            BasePage.setDriver(driver);
            driver.get(ConfigPage.getProperty("base_url"));
        }
    }
    @AfterAll
    public static void tearDown() {
        if (driver != null) {
            driver.close();
            driver.quit();
        }
    }
}
