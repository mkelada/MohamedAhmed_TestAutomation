package uiTests;

import helpers.UIHelper;
import org.junit.*;
import org.openqa.selenium.WebElement;
import utils.UISelectors;
import org.junit.rules.ErrorCollector;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;


public class verifySearchResults extends UIHelper {
    @Rule
    public ErrorCollector collector = new ErrorCollector();
    UISelectors selectors = new UISelectors();

    @Before
    public void testsInitializations() {
        seleniumConfig();

        browserNavigation("", selectors.expectedHomepageElement);
    }

    @After
    public void testsClosure() {
        System.out.println("Tests Done");
    }

    @Test
    public void verifyThatTheResultsMatchTheSearchCriteria() {
        System.out.println("Verify that the results match the search criteria TestCase has started");

        actionsOnElementByXpath(selectors.locationField_Homepage, elementsActions.SEND_KEYS, "Rome, Italy");

        try {
            if (validateTextOnElement(selectors.locationSearchFirstResult_Homepage, "Rome, Italy")) {
                actionsOnElementByXpath(selectors.locationSearchFirstResult_Homepage, elementsActions.CLICK);
            } else {
                Assert.fail("Failed to find search results, one more try");
            }
        } catch (AssertionError e) {
            collector.addError(e);
            actionsOnElementByXpath(selectors.locationField_Homepage, elementsActions.CLEAR_Text);
            actionsOnElementByXpath(selectors.locationField_Homepage, elementsActions.SEND_KEYS, "Rome, Italy");
            String locationSearchFirstResult = actionsOnElementByXpath(selectors.locationSearchFirstResult_Homepage, elementsActions.GET_TEXT);
            Assert.assertEquals("Chosen location is Wrong", locationSearchFirstResult, "Rome, Italy");
            actionsOnElementByXpath(selectors.locationSearchFirstResult_Homepage, elementsActions.CLICK);
        }

        if (!validateElementAppearance(selectors.calendarButton_CheckInHomepage)) {
            actionsOnElementByXpath(selectors.checkInButton_Homepage, elementsActions.CLICK);
        }
        actionsOnElementByXpath(selectors.pickChooseCheckInDate_Homepage, elementsActions.CLICK);
        actionsOnElementByXpath(selectors.pickChooseCheckOutDate_Homepage, elementsActions.CLICK);
        String selectedCheckInDate = actionsOnElementByXpath(selectors.pickSelectCheckInDate_Homepage, elementsActions.GET_TEXT);
        String selectedCheckOutDate = actionsOnElementByXpath(selectors.pickSelectCheckOutDate_Homepage, elementsActions.GET_TEXT);
        collector.checkThat("Selected CheckIn date is wrong", selectedCheckInDate, containsString(String.valueOf(LocalDate.now().plusWeeks(1).getDayOfMonth())));
        collector.checkThat("Selected CheckOut date is wrong", selectedCheckOutDate, containsString(String.valueOf(LocalDate.now().plusWeeks(2).getDayOfMonth())));
        actionsOnElementByXpath(selectors.exactDatesButton_CheckInHomepage, elementsActions.CLICK);

        actionsOnElementByXpath(selectors.guestsButton_Homepage, elementsActions.CLICK);
        collector.checkThat("Guests is not clicked", validateElementAppearance(selectors.adultsLabel_GuestsHomePage), equalTo(true));
        actionsOnElementByXpath(selectors.increaseAdultsButton_GuestsHomepage, elementsActions.CLICK, "2");
        String selectedAdultsValue = actionsOnElementByXpath(selectors.selectedAdultsValue_GuestsHomepage, elementsActions.GET_TEXT);
        collector.checkThat("Adults value is wrong", selectedAdultsValue, equalTo("2"));
        actionsOnElementByXpath(selectors.increaseChildrenButton_GuestsHomepage, elementsActions.CLICK);
        String selectedChildrenValue = actionsOnElementByXpath(selectors.selectedChildrenValue_GuestsHomepage, elementsActions.GET_TEXT);
        collector.checkThat("Children value is wrong", selectedChildrenValue, equalTo("1"));

        actionsOnElementByXpath(selectors.searchButton_Homepage, elementsActions.CLICK);
        collector.checkThat("Wrong URL", validateURL("Rome"), equalTo(true));
        collector.checkThat("Wrong URL", validateURL(String.format("checkin=%s&checkout=%s&adults=%s&children=%s",
                LocalDate.now().plusWeeks(1), LocalDate.now().plusWeeks(2), "2", "1")), equalTo(true));

        List<WebElement> elementsTextsInSearchResultsBar = getElementsTexts(selectors.searchResultsBar_searchResultsPage);
        List<WebElement> elementsTextsValuesInSearchResultsBar = getElementsTexts(selectors.searchResultsBarValues_searchResultsPage);
        for (int i = 0; i < elementsTextsInSearchResultsBar.size(); i++) {
            if (elementsTextsInSearchResultsBar.get(i).getText().equals("Location")) {
                collector.checkThat("Wrong Location", "Rome", equalTo(elementsTextsValuesInSearchResultsBar.get(i)));
            } else if (elementsTextsInSearchResultsBar.get(i).getText().equals("Check in / Check out")) {
                collector.checkThat("Wrong Check in / Check out",
                        selectors.searchResultsDateValidator(1, 2), equalTo(elementsTextsValuesInSearchResultsBar.get(i)));
            } else if (elementsTextsInSearchResultsBar.get(i).getText().equals("Guests")) {
                collector.checkThat("Wrong Location","3 guests", equalTo(elementsTextsValuesInSearchResultsBar.get(i)));
            }
        }

        System.out.println("Verify that the results match the search criteria TestCase has ended");
    }
}
