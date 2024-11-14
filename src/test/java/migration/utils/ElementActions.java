package migration.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ElementActions {

    private WebDriver driver;

 
    public ElementActions(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Generic method to enter text into a field.
     *
     * @param locator The locator of the input field (e.g., By.id, By.name, By.xpath, etc.)
     * @param text    The text to enter into the field
     */
    public void enterText(By locator, String text) {
        WebElement element = driver.findElement(locator);
        element.clear(); // Clear the field first
        element.sendKeys(text);
    }

    /**
     * Generic method to click an element.
     *
     * @param locator The locator of the element to be clicked
     */
    public void clickElement(By locator) {
        driver.findElement(locator).click();
    }
}
