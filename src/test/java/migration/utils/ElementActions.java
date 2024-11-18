package migration.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;


public class ElementActions {

    private WebDriver driver;
	private Object actions;

 
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
        element.clear(); 
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
    
// getText
    
    public String getElementText(By locator) {
        return driver.findElement(locator).getText();
    }
 
    
    //Enter Key
    public void pressKey(By locator, Keys key) {
        WebElement element = driver.findElement(locator);
        element.sendKeys(key);
    }
    
    // Check if element is displaye
    
    public boolean isElementDisplayed(By locator) {
        return driver.findElement(locator).isDisplayed();
    }
    //
    
    /**
     * Generic method to check if an element is enabled.
     *
     * @param locator The locator of the element
     * @return True if the element is enabled, otherwise false
     */
    public boolean isElementEnabled(By locator) {
        return driver.findElement(locator).isEnabled();
    }

   
    
    /* @param locator The locator of the element
    * @return True if the element is selected, otherwise false
    */
   public boolean isElementSelected(By locator) {
       return driver.findElement(locator).isSelected();
   }
   



public class WaitUtils {

    private WebDriverWait wait;

    // Constructor to initialize WebDriver and WebDriverWait
    public WaitUtils(WebDriver driver, int timeoutInSeconds) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
    }

    public WebElement waitForElementVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement waitForElementClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public boolean waitForElementInvisible(By locator) {
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }
}

/*@param timeout The time to sleep in milliseconds.
*/
public static void sleepForTimeout(long timeout) {
   try {
       Thread.sleep(timeout); // Sleep for the given timeout in milliseconds
   } catch (InterruptedException e) {
       // Handle interruption if the sleep is interrupted
       System.out.println("Thread was interrupted during sleep: " + e.getMessage());
   }
   
  
}

// Method to close the current browser window
public void closeBrowser() {
    if (driver != null) {
        driver.close(); // Closes the current browser window
        System.out.println("Browser window closed.");
    }
}

// Method to quit the browser session completely
public void quitBrowser() {
    if (driver != null) {
        driver.quit(); // Closes all browser windows and ends the WebDriver session
        System.out.println("All browser windows closed, session ended.");
    }
}

public void openUrl(String url) {
    driver.get(url);  // Open the provided URL in the browser
    System.out.println("Opened website: " + url);
}


// Method to get the value of an attribute from an element
public String getElementAttribute(By locator, String attributeName) {
    WebElement element = driver.findElement(locator);  // Locate the element
    String attributeValue = element.getAttribute(attributeName);  // Get the attribute value
    return attributeValue;
}


// Method to execute JavaScript
public Object executeJavaScript(String script) {
    JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
    return jsExecutor.executeScript(script); // Execute the script
}


public void navigateBack() {
    driver.navigate().back();  // Simulates clicking the browser's "Back" button
    System.out.println("Navigated back.");
}

public void navigateForward() {
    driver.navigate().forward();  // Simulates clicking the browser's "Forward" button
    System.out.println("Navigated forward.");
}

// Method to find elements by locator and click the element at the specified index
public void findElementAndClick(By locator, int index) {
    List<WebElement> elements = driver.findElements(locator);  // Find all elements matching the locator
    if (elements.size() > index) {
        WebElement elementToClick = elements.get(index);  // Get the element at the specified index
        elementToClick.click();  // Click on the element
        System.out.println("Clicked element at index: " + index);
    } else {
        System.out.println("No element found at index: " + index);
    }
}


// Method to find elements by locator and set text on the element at the specified index
public void findElementAndSetText(By locator, int index, String text) {
    List<WebElement> elements = driver.findElements(locator);  // Find all elements matching the locator
    if (elements.size() > index) {
        WebElement elementToSetText = elements.get(index);  // Get the element at the specified index
        elementToSetText.clear();  // Clear the field first if it's an input or text field
        elementToSetText.sendKeys(text);  // Set the text on the element
        System.out.println("Set text on element at index: " + index);
    } else {
        System.out.println("No element found at index: " + index);
    }
}

// Method to verify if an element is visible
public boolean isElementVisible(By locator) {
    WebElement element = driver.findElement(locator);
    boolean isVisible = element.isDisplayed();  // Check if the element is visible
    if (isVisible) {
        System.out.println("Element is visible.");
    } else {
        System.out.println("Element is not visible.");
    }
    return isVisible;
}

}
