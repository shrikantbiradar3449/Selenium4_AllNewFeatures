package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import com.aventstack.extentreports.*;
import utils.ExtentReportManager;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;

public class BaseTest {
    protected WebDriver driver;
    private static ExtentReports extent; // Make it static to ensure it's initialized once
    protected ExtentTest test;

    @BeforeSuite
    public void setupExtent() {
        extent = ExtentReportManager.getReportInstance();
    }

    @BeforeMethod
    public void setup(Method method) {
        if (extent == null) {
            setupExtent(); // Ensure ExtentReports is initialized
        }
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        test = extent.createTest(method.getName());
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            String screenshotPath = takeScreenshot(result.getName());
            test.fail("Test failed: " + result.getThrowable());
            test.addScreenCaptureFromPath("../" + screenshotPath);
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.pass("Test passed");
        }

        driver.quit();
    }

    @AfterSuite
    public void flushReport() {
        if (extent != null) {
            extent.flush();
        }
    }

    public String takeScreenshot(String testName) {
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String screenshotPath = "test-output/screenshots/" + testName + ".png";

        try {
            new File("test-output/screenshots").mkdirs(); // Ensure folder exists
            Files.copy(src.toPath(), new File(screenshotPath).toPath());
        } catch (IOException e) {
            System.err.println("Screenshot capture failed: " + e.getMessage());
        }

        // Return relative path for Extent Reports
        return screenshotPath;
    }

}
