package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UITestBase extends Environment {
    static WebDriver driver;
    static WebDriverWait wait;


    /**
     * Base Selenium Initializations method
     */
    public void seleniumConfig() {
        variables(Environment.currentEnvironment);
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, 10);
        driver.manage().window().maximize();
    }

    public WebDriverWait getWebDriverWait() {
        return wait;
    }

    public WebDriver getWebDriver() {
        return driver;
    }

}