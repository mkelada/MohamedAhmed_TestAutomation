package uiTests;

import helpers.UIHelper;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import helpers.UISelectors;
import org.junit.rules.ErrorCollector;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.*;


public class VerifySearchResults extends UIHelper {

    UISelectors selectors = new UISelectors();
    @Rule
    public ErrorCollector collector = new ErrorCollector();

    @Before
    public void testsInitializations() {
        seleniumConfig();
        browserNavigation("", selectors.expectedHomepageElement);
    }

    @After
    public void testsClosure() {
        close();
    }

    @Test
    public void verifyPositiveResultsForSearchCriteria() {
        System.out.println("Verify that the results match the search criteria TestCase has started");
        int checkInAfterWeeks = 2, checkOutAfterWeeks = 3;
        String location = "Rome, Italy";
        int adultsGuests = 2, childrenGuests = 1;
        UISelectors selectors = new UISelectors();

        verifyThatTheResultsMatchTheSearchCriteria(location, checkInAfterWeeks, checkOutAfterWeeks, adultsGuests, childrenGuests);
        List<WebElement> guestsResultsTexts = getElementsTexts(selectors.searchResultsGuests_searchResultsPage);
        for (WebElement elementsText : guestsResultsTexts) {
            collector.checkThat("property doesn't accommodate the selected number of guests",
                    Integer.parseInt(elementsText.getText().split(" ")[0]), greaterThanOrEqualTo(adultsGuests + childrenGuests));
        }
    }

    @Test
    public void verifyNegativeResultsForSearchCriteria() {
        int checkInAfterWeeks = 2, checkOutAfterWeeks = 3;
        String location = "Rome, Italy";
        int adultsGuests = 2, childrenGuests = 1;
        UISelectors selectors = new UISelectors();

        verifyThatTheResultsMatchTheSearchCriteria(location, checkInAfterWeeks, checkOutAfterWeeks, adultsGuests, childrenGuests);
        List<WebElement> guestsResultsTexts = getElementsTexts(selectors.searchResultsGuests_searchResultsPage);
        for (WebElement elementsText : guestsResultsTexts) {
            collector.checkThat("property doesn't accommodate the selected number of guests",
                    Integer.parseInt(elementsText.getText().split(" ")[0]), greaterThanOrEqualTo(adultsGuests * 3 + childrenGuests));
        }
    }




    
    public void verifyThatTheResultsMatchTheSearchCriteria(String location, int checkInAfterWeeks, int checkOutAfterWeeks, int adultGuests, int childrenGuests) {
        By pickChooseCheckInDate_Homepage = selectors.getDateAfterNWeeksFromNow(checkInAfterWeeks, true, false);
        By pickChooseCheckOutDate_Homepage = selectors.getDateAfterNWeeksFromNow(checkOutAfterWeeks, false, false);
        By pickSelectCheckInDate_Homepage = selectors.getDateAfterNWeeksFromNow(checkInAfterWeeks, true, true);
        By pickSelectCheckOutDate_Homepage = selectors.getDateAfterNWeeksFromNow(checkOutAfterWeeks, false, true);

        actionsOnElementByXpath(selectors.locationField_Homepage, elementsActions.SEND_KEYS, location);

        try {
            if (validateTextOnElement(selectors.locationSearchFirstResult_Homepage, location)) {
                actionsOnElementByXpath(selectors.locationSearchFirstResult_Homepage, elementsActions.CLICK);
            } else {
                Assert.fail("Failed to find search results, one more try");
            }
        } catch (AssertionError e) {
            collector.addError(e);
            actionsOnElementByXpath(selectors.locationField_Homepage, elementsActions.CLEAR_Text);
            actionsOnElementByXpath(selectors.locationField_Homepage, elementsActions.SEND_KEYS, location);
            String locationSearchFirstResult = actionsOnElementByXpath(selectors.locationSearchFirstResult_Homepage, elementsActions.GET_TEXT);
            Assert.assertEquals("Chosen location is Wrong", locationSearchFirstResult, location);
            actionsOnElementByXpath(selectors.locationSearchFirstResult_Homepage, elementsActions.CLICK);
        }

        if (!validateElementAppearance(selectors.calendarButton_CheckInHomepage)) {
            actionsOnElementByXpath(selectors.checkInButton_Homepage, elementsActions.CLICK);
        }
        actionsOnElementByXpath(pickChooseCheckInDate_Homepage, elementsActions.CLICK);
        actionsOnElementByXpath(pickChooseCheckOutDate_Homepage, elementsActions.CLICK);
        String selectedCheckInDate = actionsOnElementByXpath(pickSelectCheckInDate_Homepage, elementsActions.GET_TEXT);
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

        System.out.println("Verify that the results match the search criteria TestCase has ended");
    }
}
