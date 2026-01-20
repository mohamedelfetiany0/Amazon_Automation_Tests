package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.locators.RelativeLocator;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static org.openqa.selenium.support.locators.RelativeLocator.with;

public class cart {
    WebDriver driver;
    public String productName;
    public String productPrice;
    public String SubtotalPrice;
    public String Quantity;

    public cart(WebDriver driver) {
        this.driver = driver;
    }
    public void addToCart() throws InterruptedException {
    driver.findElement(By.partialLinkText("Today's Deals")).click();
    Thread.sleep(2000);
    driver.findElement(By.xpath("(//div[@role='listitem'])[2]")).click();
    Thread.sleep(2000);
    driver.findElement(By.xpath("(//div[@data-testid='product-card'])[1]")).click();
    JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript("window.scrollBy(0, 200);");
    Thread.sleep(1000);
    productName = driver.findElement(By.id("productTitle")).getText().trim();

        if (driver.findElements(By.xpath("//*[contains(text(), 'Regular Price')]")).size() > 0) {
            // if regular price container exists
            driver.findElement(By.xpath("//*[contains(text(), 'Regular Price')]")).click();
            Thread.sleep(1000);

            productPrice = driver.findElement(
                    RelativeLocator.with(By.className("a-price-whole"))
                            .below(driver.findElement(By.xpath("//*[contains(text(),'Regular Price')]")))
            ).getText()
                    + "." +
                    driver.findElement(
                            RelativeLocator.with(By.className("a-price-fraction"))
                                    .below(driver.findElement(By.xpath("//*[contains(text(),'Regular Price')]")))
                    ).getText();

        } else {
            // if regular price container does not exist
            productPrice = String.valueOf(ProductPrice());
        }
    driver.findElement(By.cssSelector("[data-action='a-dropdown-button']")).click();
    driver.findElement(By.id("quantity_1")).click();
    Thread.sleep(500);
    Quantity = driver.findElement(By.cssSelector("span.a-dropdown-prompt")).getText().trim();
    driver.findElement(By.id("add-to-cart-button")).click();
    Thread.sleep(1000);
    PopUpHandle();
    Thread.sleep(1000);
    SubtotalPrice = driver.findElement(By.cssSelector("[class='a-price sw-subtotal-amount']")).getText().trim();
    driver.findElement(By.partialLinkText("Go to basket")).click();
    }

    public double ProductPrice(){
        WebElement price = driver.findElement(
                By.cssSelector("#corePriceDisplay_desktop_feature_div span.priceToPay")
        );

        String whole = price.findElement(By.className("a-price-whole")).getText().replace(",", "");
        String fraction = price.findElement(By.className("a-price-fraction")).getText();

        // combine whole + fraction with a dot for decimal
        String finalPrice = whole + "." + fraction;

        return Double.parseDouble(finalPrice);
    }
    public String getProductName() {
        return driver.findElement(By.cssSelector(".sc-product-title.sc-grid-item-product-title")).getText().trim();
    }
    public String getProductPrice() {

        return driver.findElement(By.className("sc-badge-price-to-pay")).getText().trim();
    }
    public String getSubtotalPrice() {
        return driver.findElement(By.id("sc-subtotal-amount-buybox")).getText().trim();
    }
    public int getQuantity() {
        Quantity = driver.findElement(By.cssSelector("[data-a-selector='inner-value']")).getText().trim();
        return Integer.parseInt(Quantity);
    }
    public void PopUpHandle() {
        if (driver.findElements(By.id("attach-warranty-close-icon")).size() > 0) {
            driver.findElement(By.id("attach-warranty-close-icon")).click();
        }
    }
    public boolean isSameProductByName(String productName, String cartName) {

        Set<String> stopWords = Set.of(
                "gym", "exercise", "bar", "fitness",
                "adjustable", "size", "home", "entrance",
                "black", "cm"
        );

        Set<String> productWords = Arrays.stream(productName.toLowerCase().split(" "))
                .filter(w -> w.length() > 3 && !stopWords.contains(w))
                .collect(Collectors.toSet());

        Set<String> cartWords = Arrays.stream(cartName.toLowerCase().split(" "))
                .filter(w -> w.length() > 3 && !stopWords.contains(w))
                .collect(Collectors.toSet());

        productWords.retainAll(cartWords);

        return productWords.size() >= 2;
    }
}
