package migration.pages;

import org.openqa.selenium.By;

public class DashboardPage {

    // Locators for the login page
    public By usernameField = By.name("username");
    public By passwordField = By.name("password");
    public By loginButton = By.xpath("//button[@type='submit']");
    public By gitProfile = By.xpath("//img[@class='avatar circle']");
}
