package migration.setup;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class Setup {
    private final static String CHROME_DRIVER_FULL_PATH = "/Users/ghazalashahin/Downloads/chromedriver-mac-arm64/chromedriver";
    private WebDriver driver;

    // Initialize WebDriver
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_FULL_PATH);
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    // Quit WebDriver
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    // Getter for WebDriver
    public WebDriver getDriver() {
        return driver;
    }
}
