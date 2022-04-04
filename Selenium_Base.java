package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import static utils.SystemProperties.isProduction;


public class Selenium_Base {
   public static WebDriver driver;
   public static WebDriverWait wait;
    SystemProperties systemProperties = new SystemProperties();

    public void Selenium_config() {
        systemProperties.variables(isProduction);
        WebDriverManager.chromedriver().setup();

//        System.setProperty("webdriver.chrome.driver",
//                System.getProperty("user.dir") + "/src/main/resources/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        driver = new ChromeDriver(options);

        wait = new WebDriverWait(driver, 15);
        driver.manage().window().maximize();
        driver.get(systemProperties.baseDashboardURL);
    }


    public WebDriver getChromeDriver() { return driver;}

    public WebDriverWait getWebDriverWait() {
        return wait;
    }

    public void browserNavigation (String URL, String expectedText, boolean... first) {
        systemProperties.variables(isProduction);
        getChromeDriver().get(systemProperties.baseDashboardURL +URL);
        getWebDriverWait().until(ExpectedConditions.urlContains(URL));
        By ref = By.xpath(String.format("(//*[contains(text(),'%s')])[2]", expectedText));
        if (first.length >=1 && first[0])
            ref = By.xpath(String.format("(//*[contains(text(),'%s')])[1]", expectedText));
        getWebDriverWait().until(ExpectedConditions.textToBe(ref, expectedText));

    }

    public void close() {
        driver.quit();
    }

    public void takeScreenShot(String testName) throws IOException {

        String fileWithPath = new File(System.getProperty("user.dir")).getAbsolutePath() + "/src/test/resources/Screenshots/";

        //Convert web driver object to TakeScreenshot
        File SrcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        //Move image file to new destination
        File DestFile = new File(fileWithPath + testName + ".png");

        //Copy file at destination
        FileUtils.copyFile(SrcFile, DestFile);

        //to appear in index.html / testng report
        Reporter.log("<a href='" + DestFile.getAbsolutePath() + "'> <img src='" + DestFile.getAbsolutePath() + "' height='100' width='100'/> </a>");

    }

    public void actionsOnElementByXpath(By element, boolean javaScript, boolean click, String... sendKeys) {
        getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(element));
        getWebDriverWait().until(ExpectedConditions.elementToBeClickable(element));
        WebElement Element = driver.findElement(element);
        if (click) {
            if(javaScript) {
                JavascriptExecutor executor = (JavascriptExecutor) driver;
                executor.executeScript(String.format("arguments[0].click();", Element));
            }
            else  Element.click();
        } else {
            if(javaScript) {
                JavascriptExecutor executor = (JavascriptExecutor) getChromeDriver();
                executor.executeScript("arguments[0].click();", Element);
            }
            else Element.sendKeys(sendKeys);
        }
    }

    public String getTextOfElementByXpath(By element){
        try {
            getWebDriverWait().until(ExpectedConditions.presenceOfElementLocated(element));
            getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(element));
        }catch (TimeoutException e){
            System.out.println("Failed to find element with xpath = "+element);
        }
        getWebDriverWait().until(ExpectedConditions.elementToBeClickable(element));
        WebElement Element = getChromeDriver().findElement(element);
        return Element.getText();
    }

    public void typeString(String string) throws InterruptedException, AWTException
    {
        //Instantiating robot
        Robot robot = new Robot();

        //Looping through every char
        for (int i = 0; i < string.length(); i++)
        {
            //Getting current char
            char c = string.charAt(i);

            //Pressing shift if it's uppercase
            if (Character.isUpperCase(c))
            {
                robot.keyPress(KeyEvent.VK_SHIFT);
            }

            //Actually pressing the key
            robot.keyPress(Character.toUpperCase(c));
            robot.keyRelease(Character.toUpperCase(c));

            //Releasing shift if it's uppercase
            if (Character.isUpperCase(c))
            {
                robot.keyRelease(KeyEvent.VK_SHIFT);
            }

            //Optional delay to make it look like it's a human typing
            Thread.sleep(100 + new Random().nextInt(150));
        }
    }




}