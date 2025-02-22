package org.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertEquals;

public class Seli4Test  extends BaseTest {


//    @Test
//    public void testMethod() {
//        System.out.println("This is a test method");
//        WebDriver driver = new ChromeDriver();
//        // Open the desired URL
//        driver.get("https://practicesoftwaretesting.com/");
//        // Maximize the browser window (optional)
//
//        assertEquals(driver.getTitle(), "Practice Software Testing - Toolshop - v5.0");
//        driver.manage().window().maximize();
//        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//        driver.quit();
//
//    }


    @Test
    public void googleHomePageTest() {
        driver.get(appUrl);
        String title = driver.getTitle();
        assert title.contains("Google");
    }

    @Test
    public void failingTest() {
        driver.get(appUrl);
        String title = driver.getTitle();
        assert title.contains("Bing"); // Intentional failure for demo
    }
}
