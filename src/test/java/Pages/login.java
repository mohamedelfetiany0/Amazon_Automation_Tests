package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class login {

    WebDriver driver;

    public login(WebDriver driver) {
        this.driver = driver;
    }


    public void login(String email) {
        driver.findElement(By.id("ap_email_login")).sendKeys(email);
        driver.findElement(By.className("a-button-input")).click();
    }
    public boolean isAtSignupPage() {
        try {
            return driver.findElement(By.xpath("//h1[contains(text(), 'Looks like you')]")).isDisplayed();
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }
}

