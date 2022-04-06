package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class UITestBase {
    public WebDriver driver;
    public WebDriverWait wait;

    Environment environment = new Environment();
    /**
     * Base Selenium Initializations method
     */
    public void seleniumConfig() {
        environment.variables(Environment.currentEnvironment);
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, 5);
        driver.manage().window().maximize();
    }



}