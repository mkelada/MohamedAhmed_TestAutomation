package helpers;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import utils.Environment;
import utils.UITestBase;
import org.openqa.selenium.support.ui.ExpectedConditions;

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
        driver.get(environment.baseWebsiteURL + URL);
        wait.until(ExpectedConditions.urlContains(URL));
        wait.until(ExpectedConditions.visibilityOfElementLocated(expectedElement));
    }

    public void close() {
        driver.quit();
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
        wait.until(ExpectedConditions.visibilityOfElementLocated(element));
        WebElement Element = driver.findElement(element);
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        if (uiActions.equals(elementsActions.CLICK)) {
            wait.until(ExpectedConditions.elementToBeClickable(element));
            if (textOrClicks.length > 0) {
                for (int i = 0; i < Integer.parseInt(textOrClicks[0]); i++) {
                    Element.click();
                }
            } else {
                Element.click();
            }
        } else if (uiActions.equals(elementsActions.SEND_KEYS)) {
            Element.clear();
            driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            Element.sendKeys(textOrClicks);
        } else if (uiActions.equals(elementsActions.HOVER)) {
            Actions actions = new Actions(driver);
            actions.moveToElement(Element).perform();
        } else if (uiActions.equals(elementsActions.SCROLL_DOWN)) {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView();", Element);
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
        wait.until(ExpectedConditions.visibilityOfElementLocated(element));
        return driver.findElements(element);
    }

    public boolean validateTextOnElement(By element, String expectedText) {
        WebElement Element;
        try {
            wait.until(ExpectedConditions.textToBe(element, expectedText));
            Element = driver.findElement(element);
        } catch (Exception e) {
            return false;
        }
        return Element.getText().equals(expectedText);
    }

    public boolean validateElementAppearance(By element) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(element));
        } catch (Exception e) {
            return false;
        }
        return driver.findElement(element).isDisplayed();
    }

    public boolean validateURL(String expectedText) {
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        return driver.getCurrentUrl().contains(expectedText);
    }

    public enum elementsActions {
        CLICK,
        SEND_KEYS,
        HOVER,
        GET_TEXT,
        CLEAR_Text,
        GET_STYLE,
        SCROLL_DOWN
    }
}
