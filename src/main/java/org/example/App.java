package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.ui.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

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
//        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        // Open the desired URL
        driver.get("https://practicesoftwaretesting.com/");
        // Maximize the browser window (optional)
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);


        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(5))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class);

        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("id")));

        // Or, using a custom condition:
        WebElement myDynamicElement = wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return driver.findElement(By.xpath("//div[@id='dynamic_content']"));
            }
        });

        // Use the element
        element.click();
        myDynamicElement.getText();

//        WebElement button = driver.findElement(By.xpath("//table[contains(@class,'table')]//tr//td[contains(text(),'Chrome')]/following-sibling::td[4]"));
//        button.click();
//        System.out.println(driver.findElement(By.className("className")).getText());
//        driver.findElement(By.cssSelector(".className"));
//        driver.findElement(By.id("elementId"));
//        driver.findElement(By.linkText("linkText"));
//        driver.findElement(By.name("elementName"));
//        driver.findElement(By.partialLinkText("partialText"));
//        driver.findElement(By.tagName("elementTagName"));
//        driver.findElement(By.xpath("xPath"));

        // Pause the execution for a few seconds (optional)
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Close the browser window
        driver.close();
        driver.quit();
    }
}
