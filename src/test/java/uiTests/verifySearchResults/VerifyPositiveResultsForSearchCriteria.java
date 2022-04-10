package uiTests.verifySearchResults;

import helpers.SearchCriteria;
import helpers.UIHelper;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utils.UISelectors;

import java.util.List;

public class VerifyPositiveResultsForSearchCriteria extends UIHelper {

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
    public void verifyPositiveResultsForSearchCriteria() {
        System.out.println("1- Verify that the results match the search criteria TestCase has started");

        int checkInAfterWeeks = 1, checkOutAfterWeeks = 2;
        String location = "Rome, Italy";
        int adultsGuests = 2, childrenGuests = 1;
        int numberOfTrailsToSearch = 3;

        criteria.verifyThatTheResultsMatchTheSearchCriteria(location, checkInAfterWeeks, checkOutAfterWeeks, adultsGuests, childrenGuests, numberOfTrailsToSearch);
        List<WebElement> guestsResultsTexts = getElementsTexts(selectors.searchResultsGuests_searchResultsPage);
        for (WebElement elementsText : guestsResultsTexts) {
            softAssert.assertTrue(Integer.parseInt(elementsText.getText().split(" ")[0]) >= (adultsGuests + childrenGuests),
                    "property doesn't accommodate the selected number of guests");
        }

        System.out.println("1- Verify that the results match the search criteria TestCase has ended");
    }

    //@Test
    public void verifyNegativeResultsForSearchCriteria() {
        int checkInAfterWeeks = 2, checkOutAfterWeeks = 9;
        String location = "Rome, Italy";
        int adultsGuests = 2, childrenGuests = 1;
        int numberOfTrailsToSearch = 3;
        int negativeScenarioValue = 3;

        criteria.verifyThatTheResultsMatchTheSearchCriteria(location, checkInAfterWeeks, checkOutAfterWeeks, adultsGuests, childrenGuests, numberOfTrailsToSearch);
        List<WebElement> guestsResultsTexts = getElementsTexts(selectors.searchResultsGuests_searchResultsPage);
        for (WebElement elementsText : guestsResultsTexts) {
            softAssert.assertTrue(Integer.parseInt(elementsText.getText().split(" ")[0]) >= (adultsGuests * negativeScenarioValue + childrenGuests),
                    "property doesn't accommodate the selected number of guests");
        }
    }

}
