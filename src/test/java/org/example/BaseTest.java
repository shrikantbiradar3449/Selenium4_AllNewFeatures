package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import com.aventstack.extentreports.*;
import utils.ConfigReader;
import utils.ExtentReportManager;
import utils.Log;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;

public class BaseTest {
    protected WebDriver driver;
    private static ExtentReports extent;
    protected ExtentTest test;
    protected String appUrl;

    @BeforeSuite
    public void setupExtent() {
        Log.info("Initializing Extent Reports...");
        extent = ExtentReportManager.getReportInstance();
    }

    @BeforeMethod
    public void setup(Method method) {
        Log.info("Starting test: " + method.getName());
        driver = new ChromeDriver();
        appUrl = ConfigReader.getAppUrl();
//        driver.get(appUrl);
        driver.manage().window().maximize();
        test = extent.createTest(method.getName());
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            Log.error("Test failed: " + result.getName());
            String screenshotPath = takeScreenshot(result.getName());
            test.fail("Test failed: " + result.getThrowable());
            test.addScreenCaptureFromPath("../" + screenshotPath);
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            Log.info("Test passed: " + result.getName());
            test.pass("Test passed");
        }
        driver.quit();
    }

    @AfterSuite
    public void flushReport() {
        Log.info("Flushing Extent Reports...");
        extent.flush();
    }

    public String takeScreenshot(String testName) {
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String screenshotPath = "test-output/screenshots/" + testName + ".png";
        try {
            new File("test-output/screenshots").mkdirs();
            Files.copy(src.toPath(), new File(screenshotPath).toPath());
        } catch (IOException e) {
            Log.error("Failed to capture screenshot: " + e.getMessage());
        }
        return screenshotPath;
    }
}
