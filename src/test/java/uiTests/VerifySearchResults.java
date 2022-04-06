package uiTests;

import helpers.UIHelper;
import org.junit.*;
import org.openqa.selenium.*;
import helpers.UISelectors;
import org.junit.rules.ErrorCollector;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.*;


public class VerifySearchResults extends UIHelper {

    UISelectors selectors = new UISelectors();
    @Rule
    public ErrorCollector collector = new ErrorCollector();

    int checkInAfterWeeks = 1, checkOutAfterWeeks = 2;
    String location = "Rome, Italy";
    int adultsGuests = 2, childrenGuests = 1;
    int numberOfTrailsToSearch = 3;

    @Before
    public void testsInitializations() {
        seleniumConfig();
        browserNavigation("", selectors.expectedHomepageElement);

        verifyThatTheResultsMatchTheSearchCriteria(location, checkInAfterWeeks, checkOutAfterWeeks, adultsGuests, childrenGuests, numberOfTrailsToSearch);
    }

    @After
    public void testsClosure() {
        close();
    }

    @Test
    public void verifyPositiveResultsForSearchCriteria() {
        System.out.println("1- Verify that the results match the search criteria TestCase has started");


        List<WebElement> guestsResultsTexts = getElementsTexts(selectors.searchResultsGuests_searchResultsPage);
        for (WebElement elementsText : guestsResultsTexts) {
            collector.checkThat("property doesn't accommodate the selected number of guests",
                    Integer.parseInt(elementsText.getText().split(" ")[0]), greaterThanOrEqualTo(adultsGuests + childrenGuests));
        }
        System.out.println("1- Verify that the results match the search criteria TestCase has ended");
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

        //Getting elements that text contains price and Night or Month depends on the differences of weeks reserved
        WebElement propertyPrice = getElementsTexts(By.xpath(String.format(selectors.searchResultsPricesInProperties_searchResultsPage,
                (checkOutAfterWeeks - checkInAfterWeeks < 4) ? "night" : "month"))).get(0);
        WebElement priceTextInMapCard = getElementsTexts(selectors.pricesOfCards_map).get(0);
        //Loop After Hovering to get sure that the price of property got bigger (popup)

        String priceOfProperty = propertyPrice.getText().split(" ")[0];
        actionsOnElementByXpath(By.xpath(String.format(selectors.priceOfPropertyToHover_searchResultsPage, priceOfProperty)), elementsActions.HOVER);
        String elementStyleOnMap = actionsOnElementByXpath(By.xpath((String.format(selectors.priceOfPropertyToHover_map, priceOfProperty))), elementsActions.GET_STYLE);
        String containerStyleOnMap = actionsOnElementByXpath(By.xpath((String.format(selectors.containerOfPriceOfPropertyToHover_map, priceOfProperty))), elementsActions.GET_STYLE);
        collector.checkThat("Background color didn't get black", elementStyleOnMap, containsString(backgroundColorAfterHover));
        collector.checkThat("Text color didn't get white", elementStyleOnMap, containsString(textColorAfterHover));
        collector.checkThat("Price container didn't get bigger", containerStyleOnMap, containsString(priceContainerAfterHover));

        actionsOnElementByXpath(By.xpath(String.format(selectors.buttonsOfCards_map, 1)), elementsActions.CLICK);
        collector.checkThat("Price of property is not equal to price in card in map", priceTextInMapCard.getText(), containsString(priceOfProperty));

        System.out.println("3- Verify that a property is displayed on the map correctly TestCase has ended");
    }

    @Test
    public void verifyThatAPropertiesIsDisplayedOnTheMapCorrectly() {
        //Background Changed to black
        String backgroundColorAfterHover = "background-color: rgb(34, 34, 34)";
        //Text Changed to white
        String textColorAfterHover = "color: rgb(255, 255, 255)";
        //Container got bigger
        String priceContainerAfterHover = "transform: scale(1.077)";

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
                collector.checkThat("Background color didn't get black", elementStyleOnMap, containsString(backgroundColorAfterHover));
                collector.checkThat("Text color didn't get white", elementStyleOnMap, containsString(textColorAfterHover));
                collector.checkThat("Price container didn't get bigger", containerStyleOnMap, containsString(priceContainerAfterHover));

                actionsOnElementByXpath(By.xpath(String.format(selectors.buttonsOfCards_map, i)), elementsActions.CLICK);
                collector.checkThat("Price of property is not equal to price in card in map", pricesTextsInMapCards.get(i).getText(), containsString(priceOfProperty));
                actionsOnElementByXpath(selectors.anyWhereOnTheMap, elementsActions.CLICK);
            } catch (NoSuchElementException | TimeoutException | ElementClickInterceptedException e) {
                System.out.printf("Property with price %s can't be located or Click Interception occurred%n", propertiesPrices.get(i).getText().split(" ")[0]);
            }
        }
    }

    @Test
    public void verifyNegativeResultsForSearchCriteria() {
        int checkInAfterWeeks = 2, checkOutAfterWeeks = 9;
        String location = "Rome, Italy";
        int adultsGuests = 2, childrenGuests = 1;
        UISelectors selectors = new UISelectors();

        int numberOfTrailsToSearch = 3;
        int negativeScenarioValue = 3;

        verifyThatTheResultsMatchTheSearchCriteria(location, checkInAfterWeeks, checkOutAfterWeeks, adultsGuests, childrenGuests, numberOfTrailsToSearch);
        List<WebElement> guestsResultsTexts = getElementsTexts(selectors.searchResultsGuests_searchResultsPage);
        for (WebElement elementsText : guestsResultsTexts) {
            collector.checkThat("property doesn't accommodate the selected number of guests",
                    Integer.parseInt(elementsText.getText().split(" ")[0]), greaterThanOrEqualTo(adultsGuests * negativeScenarioValue + childrenGuests));
        }
    }


    private void verifyThatTheResultsMatchTheSearchCriteria(String location, int checkInAfterWeeks, int checkOutAfterWeeks, int adultGuests, int childrenGuests, int trails) {
        By pickChooseCheckInDate_Homepage = selectors.getDateAfterNWeeksFromNow(checkInAfterWeeks, true, false);
        By pickChooseCheckOutDate_Homepage = selectors.getDateAfterNWeeksFromNow(checkOutAfterWeeks, false, false);
        By pickSelectCheckInDate_Homepage = selectors.getDateAfterNWeeksFromNow(checkInAfterWeeks, true, true);
        By pickSelectCheckOutDate_Homepage = selectors.getDateAfterNWeeksFromNow(checkOutAfterWeeks, false, true);

        actionsOnElementByXpath(selectors.locationField_Homepage, elementsActions.SEND_KEYS, location);

        try {
            if (validateTextOnElement(selectors.locationSearchFirstResult_Homepage, location)) {
                actionsOnElementByXpath(selectors.locationSearchFirstResult_Homepage, elementsActions.GET_TEXT);
                actionsOnElementByXpath(selectors.locationSearchFirstResult_Homepage, elementsActions.CLICK);
            } else {
                Assert.fail(String.format("Failed to find search results, will try %s time(s)", trails));
            }
        } catch (AssertionError e) {
            for (int i = 0; i < trails; i++) {
                actionsOnElementByXpath(selectors.locationField_Homepage, elementsActions.CLEAR_Text);
                actionsOnElementByXpath(selectors.locationField_Homepage, elementsActions.SEND_KEYS, location);
                String locationSearchFirstResult = actionsOnElementByXpath(selectors.locationSearchFirstResult_Homepage, elementsActions.GET_TEXT);
                if (locationSearchFirstResult.equals(location)) {
                    Assert.assertEquals("Chosen location is Wrong", locationSearchFirstResult, location);
                    actionsOnElementByXpath(selectors.locationSearchFirstResult_Homepage, elementsActions.CLICK);
                    break;
                }
            }
        }


        if (!validateElementAppearance(selectors.calendarButton_CheckInHomepage)) {
            actionsOnElementByXpath(selectors.checkInButton_Homepage, elementsActions.CLICK);
        }

        if (checkInAfterWeeks > 4) {
            actionsOnElementByXpath(selectors.nextButtonInCalendar_CheckInHomePage, elementsActions.CLICK);
        }
        actionsOnElementByXpath(pickChooseCheckInDate_Homepage, elementsActions.CLICK);
        String selectedCheckInDate = actionsOnElementByXpath(pickSelectCheckInDate_Homepage, elementsActions.GET_TEXT);

        if (checkOutAfterWeeks > 4) {
            actionsOnElementByXpath(selectors.nextButtonInCalendar_CheckInHomePage, elementsActions.CLICK);
        }
        actionsOnElementByXpath(pickChooseCheckOutDate_Homepage, elementsActions.CLICK);
        String selectedCheckOutDate = actionsOnElementByXpath(pickSelectCheckOutDate_Homepage, elementsActions.GET_TEXT);

        collector.checkThat("Selected CheckIn date is wrong", selectedCheckInDate, containsString(String.valueOf(LocalDate.now().plusWeeks(checkInAfterWeeks).getDayOfMonth())));
        collector.checkThat("Selected CheckOut date is wrong", selectedCheckOutDate, containsString(String.valueOf(LocalDate.now().plusWeeks(checkOutAfterWeeks).getDayOfMonth())));
        actionsOnElementByXpath(selectors.exactDatesButton_CheckInHomepage, elementsActions.CLICK);

        actionsOnElementByXpath(selectors.guestsButton_Homepage, elementsActions.CLICK);
        collector.checkThat("Guests is not clicked", validateElementAppearance(selectors.adultsLabel_GuestsHomePage), equalTo(true));
        actionsOnElementByXpath(selectors.increaseAdultsButton_GuestsHomepage, elementsActions.CLICK, String.valueOf(adultGuests));
        String selectedAdultsValue = actionsOnElementByXpath(selectors.selectedAdultsValue_GuestsHomepage, elementsActions.GET_TEXT);
        collector.checkThat("Adults value is wrong", selectedAdultsValue, equalTo("2"));
        actionsOnElementByXpath(selectors.increaseChildrenButton_GuestsHomepage, elementsActions.CLICK, String.valueOf(childrenGuests));
        String selectedChildrenValue = actionsOnElementByXpath(selectors.selectedChildrenValue_GuestsHomepage, elementsActions.GET_TEXT);
        collector.checkThat("Children value is wrong", selectedChildrenValue, equalTo("1"));

        actionsOnElementByXpath(selectors.searchButton_Homepage, elementsActions.CLICK);
        collector.checkThat("Wrong URL", validateURL(location.split(",")[0]), equalTo(true));
        collector.checkThat("Wrong URL", validateURL(String.format("checkin=%s&checkout=%s&adults=%s&children=%s",
                LocalDate.now().plusWeeks(checkInAfterWeeks), LocalDate.now().plusWeeks(checkOutAfterWeeks), "2", "1")), equalTo(true));

        List<WebElement> elementsTextsInSearchResultsBar = getElementsTexts(selectors.searchResultsBar_searchResultsPage);
        List<WebElement> elementsTextsValuesInSearchResultsBar = getElementsTexts(selectors.searchResultsBarValues_searchResultsPage);
        for (int i = 0; i < elementsTextsInSearchResultsBar.size(); i++) {
            if (elementsTextsInSearchResultsBar.get(i).getText().equals("Location")) {
                collector.checkThat("Wrong Location", location.split(",")[0], equalTo(elementsTextsValuesInSearchResultsBar.get(i)));
            } else if (elementsTextsInSearchResultsBar.get(i).getText().equals("Check in / Check out")) {
                collector.checkThat("Wrong Check in / Check out",
                        selectors.searchResultsDateValidator(checkInAfterWeeks, checkOutAfterWeeks), equalTo(elementsTextsValuesInSearchResultsBar.get(i)));
            } else if (elementsTextsInSearchResultsBar.get(i).getText().equals("Guests")) {
                collector.checkThat("Wrong Location", String.format("%s guests", adultGuests + childrenGuests), equalTo(elementsTextsValuesInSearchResultsBar.get(i)));
            }
        }
        Assert.assertTrue(validateElementAppearance(selectors.anyWhereOnTheMap));
    }
}
