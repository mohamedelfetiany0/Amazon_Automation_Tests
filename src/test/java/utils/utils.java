package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;

public class utils {

    // Method to take screenshot
    public static String takeScreenshot(WebDriver driver, String screenshotName) {

        // Timestamp عشان كل screenshot يكون فريد
        String date = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String filePath = System.getProperty("user.dir") + "/screenshots/" + screenshotName + "_" + date + ".png";

        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File src = ts.getScreenshotAs(OutputType.FILE);
            File dest = new File(filePath);
            FileUtils.copyFile(src, dest);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return filePath; // نرجع path عشان نضيفه في Extent Report
    }
}
