package migration.specs;

import migration.pages.DashboardPage;
import migration.setup.Setup;
import migration.testdata.TestDataProvider;
import migration.utils.ElementActions;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.openqa.selenium.By;

public class LoginBasicTest {

    private Setup setup;
    private WebDriver driver;
    private ElementActions elementActions;
    private DashboardPage dashboardPage;
    private JSONObject loginData;

    @BeforeTest
    public void initialize() {
        // Initialize Setup class and set up WebDriver
        setup = new Setup();
        setup.setUp();
        driver = setup.getDriver();

        // Initialize ElementActions and DashboardPage
        elementActions = new ElementActions(driver);
        dashboardPage = new DashboardPage();

        // Load login data from JSON
        loginData = TestDataProvider.getLoginData();
        if (loginData == null) {
            throw new RuntimeException("Failed to load login data.");
        }
    }

    @AfterTest
    public void cleanUp() {
        // Clean up WebDriver
        setup.tearDown();
    }

    @Test
    public void loginTest() {
        // Extract login credentials from JSON
        String username = (String) loginData.get("username");
        String password = (String) loginData.get("password");

        // Open the login page
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

        // Perform login using ElementActions methods and DashboardPage locators
        elementActions.enterText(dashboardPage.usernameField, username);
        elementActions.enterText(dashboardPage.passwordField, password);
        elementActions.clickElement(dashboardPage.loginButton);

        // Validate successful login
        By dashboardElement = By.xpath("//h6");  
        Assert.assertTrue(driver.findElement(dashboardElement).isDisplayed(), "Login failed, dashboard not displayed.");
    }
}
