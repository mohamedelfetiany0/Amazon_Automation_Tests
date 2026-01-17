package Tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import Pages.homepage;
import Pages.login;
import utils.utils;

import java.awt.*;
import java.io.File;

public class loginTest {

    WebDriver driver;
    ExtentReports extent;
    ExtentTest test;

    @BeforeClass
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        ExtentSparkReporter spark = new ExtentSparkReporter("ExtentReport.html");
        extent = new ExtentReports();
        extent.attachReporter(spark);
        test = extent.createTest("Login Test", "Verify unregistered email login navigation to signup page");
    }

    @Test
    public void LoginWithValidEmailFormatButNotRegistered() throws InterruptedException {
        //login with valid email format but not registered should navigate the user to signup page

        driver.get("https://www.amazon.eg/");
        Thread.sleep(2000); // wait between screens

        homepage homePage = new homepage(driver);
        homePage.clickSignupLogin();
        Thread.sleep(2000);

        login loginPage = new login(driver);
        loginPage.login("fitiany@gmail.com");
        Thread.sleep(2000);
        test.log(Status.INFO, "Login attempted with unregistered email");
        try {
            Assert.assertTrue(loginPage.isAtSignupPage(), "User hasn't navigated to signup page");
            test.log(Status.PASS, "User successfully navigated to signup page after login attempt with unregistered email");
        }
        catch (AssertionError e) {
            String path = utils.takeScreenshot(driver, "redirects_fail");
            test.addScreenCaptureFromPath(path);
            test.log(Status.FAIL, e.getMessage());
            throw e;
        }

    }
    @Test
    public void LoginWithValidEmailFormatButRegistered() throws InterruptedException {
        //To test the screenshots functionality

        driver.get("https://www.amazon.eg/");
        Thread.sleep(2000); // wait between screens

        homepage homePage = new homepage(driver);
        homePage.clickSignupLogin();
        Thread.sleep(2000);

        login loginPage = new login(driver);
        loginPage.login("test@gmail.com");
        Thread.sleep(2000);
        test.log(Status.INFO, "Login attempted with unregistered email");
        try {
            Assert.assertTrue(loginPage.isAtSignupPage(), "User hasn't navigated to signup page");
            test.log(Status.PASS, "User successfully navigated to signup page after login attempt with unregistered email");
        }
        catch (AssertionError e) {
            String path = utils.takeScreenshot(driver, "redirects_fail");
            test.addScreenCaptureFromPath(path);
            test.log(Status.FAIL, e.getMessage());
            throw e;
        }

    }


    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        extent.flush();
        try {
            File reportFile = new File("ExtentReport.html");
            Desktop.getDesktop().browse(reportFile.toURI());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
