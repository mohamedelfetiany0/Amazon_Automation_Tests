package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class accountMenu {

    WebDriver driver;

    public accountMenu(WebDriver driver) {
        this.driver = driver;
    }

    // Hover over "Hello, Sign in Account & Lists"
    public void hoverAccountAndLists() {
        WebElement accountLists = driver.findElement(By.id("nav-link-accountList"));
        Actions actions = new Actions(driver);
        actions.moveToElement(accountLists).perform();
    }

    public void clickYourOrders() {
        driver.findElement(By.linkText("Your Orders")).click();
    }

    public void clickYourAddresses() {
        driver.findElement(By.linkText("Your Addresses")).click();
    }

    public void clickYourLists() {
        driver.findElement(By.linkText("Your Lists")).click();
    }

    // Verification methods
    public boolean isLoginRequiredMessageDisplayed() {
        try {
            return driver.findElement(By.name("email")).isDisplayed();
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }

    public boolean isYourListsIntroDisplayed() {
        try {
            return driver.findElement(By.id("my-lists-tab")).isDisplayed();
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }
}
