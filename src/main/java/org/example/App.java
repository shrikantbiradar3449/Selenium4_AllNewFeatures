package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

//Selenium  Manager in build driver download based on chrome version
        // System path for ChromeDriver needs to be set before running the code
//        System.setProperty("webdriver.chrome.driver", "src/main/resources/Drivers/chromedriver.exe");

        // Create a new WebDriver instance
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        // Open the desired URL
        driver.get("https://practicesoftwaretesting.com/");

        // Maximize the browser window (optional)
        driver.manage().window().maximize();

        // Pause the execution for a few seconds (optional)
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Close the browser window
        driver.quit();
    }
}
