package helpers;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Reporter;
import utils.Environment;
import utils.UITestBase;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class UIHelper extends UITestBase {

    Environment environment = new Environment();

    /**
     * Method is to navigate to a specific URL and check for a word (an element) in the needed page
     *
     * @param URL             URL that needs to reach
     * @param expectedElement Expected element that needs to be validated
     */
    public void browserNavigation(String URL, By expectedElement) {
        environment.variables(Environment.currentEnvironment);
        getWebDriver().get(environment.baseWebsiteURL + URL);
        getWebDriverWait().until(ExpectedConditions.urlContains(URL));
        getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(expectedElement));
    }

    public void takeScreenShot(String testName){

        String fileWithPath = new File(System.getProperty("user.dir")).getAbsolutePath() + "/src/test/resources/Screenshots/";

        //Convert web driver object to TakeScreenshot
        File SrcFile = ((TakesScreenshot) getWebDriver()).getScreenshotAs(OutputType.FILE);

        //Move image file to new destination
        File DestFile = new File((String) fileWithPath + testName + ".png");

        //Copy file at destination
        try {
            FileUtils.copyFile(SrcFile, DestFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //to appear in index.html / testng report
        Reporter.log("<a href='" + DestFile.getAbsolutePath() + "'> <img src='" + DestFile.getAbsolutePath() + "' height='100' width='100'/> </a>");

    }

    public void close() {
        getWebDriver().quit();
    }

    public void controlCurrentTab() {
        ArrayList<String> tabs2 = new ArrayList<>(getWebDriver().getWindowHandles());
        getWebDriver().switchTo().window(tabs2.get(1));
    }

    public void backToPreviousPage() {
        getWebDriver().navigate().back();
    }

    public void refreshPage() {
        getWebDriver().navigate().refresh();
    }

    public void scrollDown() {
        JavascriptExecutor js = (JavascriptExecutor) getWebDriver();
        js.executeScript("window.scrollBy(0,2000)");
    }

    /**
     * Method to perform actions on element
     *
     * @param element      By selector to locate the element
     * @param uiActions    Enum values for the needed action
     * @param textOrClicks optional parameter for the String need to be written (if choose SendKeys action)
     *                     or the number of clicks on the same element (if choose Click action)
     */
    public String actionsOnElementByXpath(By element, elementsActions uiActions, String... textOrClicks) {
        getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(element));
        WebElement Element = getWebDriver().findElement(element);
        getWebDriver().manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        if (uiActions.equals(elementsActions.CLICK)) {
            try {
                getWebDriverWait().until(ExpectedConditions.elementToBeClickable(element));
                if (textOrClicks.length > 0) {
                    for (int i = 0; i < Integer.parseInt(textOrClicks[0]); i++) {
                        Element.click();
                    }
                } else {
                    Element.click();
                }

            } catch (StaleElementReferenceException e) {
                Element.click();
            }
        } else if (uiActions.equals(elementsActions.SEND_KEYS)) {
            Element.clear();
            getWebDriver().manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            Element.sendKeys(textOrClicks);
        } else if (uiActions.equals(elementsActions.HOVER)) {
            Actions actions = new Actions(getWebDriver());
            actions.moveToElement(Element).perform();
        } else if (uiActions.equals(elementsActions.GET_TEXT)) {
            return Element.getText();
        } else if (uiActions.equals(elementsActions.CLEAR_Text)) {
            Element.clear();
        } else if (uiActions.equals(elementsActions.GET_STYLE)) {
            return Element.getAttribute("style");
        }
        return null;
    }

    public List<WebElement> getElementsTexts(By element) {
        getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(element));
        return getWebDriver().findElements(element);
    }

    public boolean validateTextOnElement(By element, String expectedText) {
        WebElement Element;
        try {
            getWebDriverWait().until(ExpectedConditions.textToBe(element, expectedText));
            Element = getWebDriver().findElement(element);
        } catch (Exception e) {
            return false;
        }
        return Element.getText().equals(expectedText);
    }

    public boolean validateElementAppearance(By element) {
        try {
            getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(element));
        } catch (Exception e) {
            return false;
        }
        return getWebDriver().findElement(element).isDisplayed();
    }

    public boolean validateURL(String expectedText) {
        getWebDriver().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        return getWebDriver().getCurrentUrl().contains(expectedText);
    }

    public enum elementsActions {
        CLICK,
        SEND_KEYS,
        HOVER,
        GET_TEXT,
        CLEAR_Text,
        GET_STYLE,
    }
}
