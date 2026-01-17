package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class homepage {

    WebDriver driver;

    // Constructor
    public homepage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickSignupLogin() {
        driver.findElement(By.id("nav-link-accountList")).click();
    }
}
