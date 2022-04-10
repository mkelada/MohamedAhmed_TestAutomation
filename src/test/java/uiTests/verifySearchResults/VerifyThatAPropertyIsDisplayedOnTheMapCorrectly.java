package uiTests.verifySearchResults;

import helpers.SearchCriteria;
import helpers.UIHelper;
import org.openqa.selenium.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utils.UISelectors;

import java.util.List;


public class VerifyThatAPropertyIsDisplayedOnTheMapCorrectly extends UIHelper {

    UISelectors selectors = new UISelectors();
    SoftAssert softAssert = new SoftAssert();
    SearchCriteria criteria = new SearchCriteria();

    @BeforeClass
    public void testsInitializations() {
        seleniumConfig();
        browserNavigation("", selectors.expectedHomepageElement);
    }

    @AfterClass
    public void testsClosure() {
        softAssert.assertAll();
        System.out.println("Tests Done");
        close();
    }

    @Test
    public void verifyThatAPropertyIsDisplayedOnTheMapCorrectly() {
        //Background Changed to black
        String backgroundColorAfterHover = "background-color: rgb(34, 34, 34)";
        //Text Changed to white
        String textColorAfterHover = "color: rgb(255, 255, 255)";
        //Container got bigger
        String priceContainerAfterHover = "transform: scale(1.077)";

        System.out.println("3- Verify that a property is displayed on the map correctly TestCase has started");

        int checkInAfterWeeks = 1, checkOutAfterWeeks = 2;
        String location = "Rome, Italy";
        int adultsGuests = 2, childrenGuests = 1;
        int numberOfTrailsToSearch = 3;

        criteria.verifyThatTheResultsMatchTheSearchCriteria(location, checkInAfterWeeks, checkOutAfterWeeks, adultsGuests, childrenGuests, numberOfTrailsToSearch);

        //Getting elements that text contains price and Night or Month depends on the differences of weeks reserved
        WebElement propertyPrice = getElementsTexts(By.xpath(String.format(selectors.searchResultsPricesInProperties_searchResultsPage,
                (checkOutAfterWeeks - checkInAfterWeeks < 4) ? "night" : "month"))).get(0);
        WebElement priceTextInMapCard = getElementsTexts(selectors.pricesOfCards_map).get(0);

        String priceOfProperty = propertyPrice.getText().split(" ")[0];
        actionsOnElementByXpath(By.xpath(String.format(selectors.priceOfPropertyToHover_searchResultsPage, priceOfProperty)), elementsActions.HOVER);
        String elementStyleOnMap = actionsOnElementByXpath(By.xpath((String.format(selectors.priceOfPropertyToHover_map, priceOfProperty))), elementsActions.GET_STYLE);
        String containerStyleOnMap = actionsOnElementByXpath(By.xpath((String.format(selectors.containerOfPriceOfPropertyToHover_map, priceOfProperty))), elementsActions.GET_STYLE);
        softAssert.assertTrue(elementStyleOnMap.contains(backgroundColorAfterHover), "Background color didn't get black");
        softAssert.assertTrue(elementStyleOnMap.contains(textColorAfterHover), "Text color didn't get white");
        softAssert.assertTrue(containerStyleOnMap.contains(priceContainerAfterHover), "Price container didn't get bigger");

        actionsOnElementByXpath(By.xpath(String.format(selectors.buttonsOfCards_map, 1)), elementsActions.CLICK);
        softAssert.assertTrue(priceTextInMapCard.getText().contains(priceOfProperty), "Price of property is not equal to price in card in map");

        System.out.println("3- Verify that a property is displayed on the map correctly TestCase has ended");
    }

    //@Test
    public void verifyThatAPropertiesIsDisplayedOnTheMapCorrectly() {
        //Background Changed to black
        String backgroundColorAfterHover = "background-color: rgb(34, 34, 34)";
        //Text Changed to white
        String textColorAfterHover = "color: rgb(255, 255, 255)";
        //Container got bigger
        String priceContainerAfterHover = "transform: scale(1.077)";

        int checkInAfterWeeks = 1, checkOutAfterWeeks = 2;
        String location = "Rome, Italy";
        int adultsGuests = 2, childrenGuests = 1;
        int numberOfTrailsToSearch = 3;

        criteria.verifyThatTheResultsMatchTheSearchCriteria(location, checkInAfterWeeks, checkOutAfterWeeks, adultsGuests, childrenGuests, numberOfTrailsToSearch);

        //Getting elements that text contains price and Night or Month depends on the differences of weeks reserved
        List<WebElement> propertiesPrices = getElementsTexts(By.xpath(String.format(selectors.searchResultsPricesInProperties_searchResultsPage,
                (checkOutAfterWeeks - checkInAfterWeeks < 4) ? "night" : "month")));
        List<WebElement> pricesTextsInMapCards = getElementsTexts(selectors.pricesOfCards_map);
        //Loop After Hovering to get sure that the price of property got bigger (popup)
        for (int i = 0; i < propertiesPrices.size(); i++) {
            try {
                String priceOfProperty = propertiesPrices.get(i).getText().split(" ")[0];
                if (priceOfProperty.isEmpty()) continue;
                actionsOnElementByXpath(By.xpath(String.format(selectors.priceOfPropertyToHover_searchResultsPage, priceOfProperty)), elementsActions.HOVER);
                String elementStyleOnMap = actionsOnElementByXpath(By.xpath((String.format(selectors.priceOfPropertyToHover_map, priceOfProperty))), elementsActions.GET_STYLE);
                String containerStyleOnMap = actionsOnElementByXpath(By.xpath((String.format(selectors.containerOfPriceOfPropertyToHover_map, priceOfProperty))), elementsActions.GET_STYLE);
                softAssert.assertEquals(elementStyleOnMap.contains(backgroundColorAfterHover), "Background color didn't get black");
                softAssert.assertEquals(elementStyleOnMap.contains(textColorAfterHover), "Text color didn't get white");
                softAssert.assertEquals(containerStyleOnMap.contains(priceContainerAfterHover), "Price container didn't get bigger");

                actionsOnElementByXpath(By.xpath(String.format(selectors.buttonsOfCards_map, i)), elementsActions.CLICK);
                softAssert.assertEquals(pricesTextsInMapCards.get(i).getText().contains(priceOfProperty), "Price of property is not equal to price in card in map");
                actionsOnElementByXpath(selectors.anyWhereOnTheMap, elementsActions.CLICK);
            } catch (NoSuchElementException | TimeoutException | ElementClickInterceptedException e) {
                System.out.printf("Property with price %s can't be located or Click Interception occurred%n", propertiesPrices.get(i).getText().split(" ")[0]);
            }
        }
    }

}
