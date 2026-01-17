package Tests;
import utils.utils;
import Pages.cart;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.Status;

import java.awt.*;
import java.io.File;

public class cartTest {

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

        test = extent.createTest("Cart Test", "Verify adding product to cart and validations");
    }

    @Test
    public void TestAddToCart() throws InterruptedException {
        test.log(Status.INFO, "Navigating to Amazon.eg");
        driver.get("https://www.amazon.eg/");
        Thread.sleep(2000);

        cart cartPage = new cart(driver);
        cartPage.addToCart();
        test.log(Status.INFO, "Product added to cart");
        System.out.println("totalprice = " + cartPage.SubtotalPrice);
        System.out.println("product price = " + cartPage.productPrice);
        System.out.println(("cart product name = " + cartPage.getProductName()));
        System.out.println("cart product price = " + cartPage.getProductPrice());
        System.out.println("cart subtotal price = " + cartPage.getSubtotalPrice());
        System.out.println("Quantity = " + cartPage.getQuantity());

        try {
            Assert.assertTrue(cartPage.isSameProductByName(cartPage.productName, cartPage.getProductName()),
                    "Product name in cart doesn't match the added product.");
            Assert.assertEquals(
                    cartPage.getProductPrice().replaceAll("[^0-9]", ""),
                    cartPage.productPrice.replaceAll("[^0-9]", ""),
                    "Product price in cart doesn't match the added product price"
            );
            Assert.assertEquals(
                    cartPage.getSubtotalPrice().replaceAll("[^0-9]", ""),
                    cartPage.SubtotalPrice.replaceAll("[^0-9]", ""),
                    "Subtotal price in cart doesn't match the expected subtotal price");
            Assert.assertEquals(cartPage.getQuantity(), 2, "Product quantity in cart is not 2.");
            test.log(Status.PASS, "Product verification passed");
        } catch (AssertionError e) {
            String path = utils.takeScreenshot(driver, "cart_fail");
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
