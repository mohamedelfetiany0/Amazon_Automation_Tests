package Tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import Pages.accountMenu;
import utils.utils;

import java.awt.*;
import java.io.File;

public class accountMenuTest {

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
        test = extent.createTest("Account Menu Test", "Verify access to (Your Orders), (Your Addresses) and (Your Lists) for unregistered user");
    }

    @Test
    public void verifyAccountMenuForGuestUser() throws InterruptedException {
        driver.get("https://www.amazon.eg/");
        Thread.sleep(2000);

        accountMenu account = new accountMenu(driver);
        account.hoverAccountAndLists();
        Thread.sleep(1000);

        // Verify "Your Orders"
        account.clickYourOrders();
        Thread.sleep(2000);
        test.log(Status.INFO, "Clicked on Your Orders");
        try {
            Assert.assertTrue(account.isLoginRequiredMessageDisplayed(), "'Your Orders' is visible to guest user!");
            test.log(Status.PASS, "'Your Orders' is not accessible for guest user");
        } catch (AssertionError e) {
            String path = utils.takeScreenshot(driver, "yourOrdersFail");
            test.addScreenCaptureFromPath(path);
            test.log(Status.FAIL, e.getMessage());
            throw e;
        }

        driver.navigate().back();
        Thread.sleep(2000);
        account.hoverAccountAndLists();
        account.clickYourAddresses();
        Thread.sleep(2000);
        test.log(Status.INFO, "Clicked on Your Addresses");
        try {
            Assert.assertTrue(account.isLoginRequiredMessageDisplayed(), "'Your Addresses' is visible to guest user!");
            test.log(Status.PASS, "'Your Addresses' is not accessible for guest user");
        } catch (AssertionError e) {
            String path = utils.takeScreenshot(driver, "yourAddressesFail");
            test.addScreenCaptureFromPath(path);
            test.log(Status.FAIL, e.getMessage());
            throw e;
        }

        driver.navigate().back();
        Thread.sleep(2000);
        account.hoverAccountAndLists();
        // Verify "Your Lists"
        account.clickYourLists();
        Thread.sleep(2000);
        test.log(Status.INFO, "Clicked on Your Lists");
        try {
            Assert.assertTrue(account.isYourListsIntroDisplayed(), "'Your Lists' intro screen is not displayed!");
            test.log(Status.PASS, "'Your Lists' intro screen is visible to guest user");
        } catch (AssertionError e) {
            String path = utils.takeScreenshot(driver, "yourListsFail");
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
