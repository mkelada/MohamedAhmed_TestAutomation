package uiTests.verifySearchResults;

import helpers.SearchCriteria;
import helpers.UIHelper;
import org.openqa.selenium.By;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utils.UISelectors;

public class VerifyThatTheResultsAndDetailsPageMatchTheExtraFilters extends UIHelper {

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
    public void verifyThatTheResultsAndDetailsPageMatchTheExtraFilters() {
        System.out.println("2- Verify that the results and details page match the extra filters TestCase has started");

        int checkInAfterWeeks = 1, checkOutAfterWeeks = 2;
        String location = "Rome, Italy";
        int adultsGuests = 2, childrenGuests = 1;
        int numberOfTrailsToSearch = 3;
        criteria.verifyThatTheResultsMatchTheSearchCriteria(location, checkInAfterWeeks, checkOutAfterWeeks, adultsGuests, childrenGuests, numberOfTrailsToSearch);

        By pickSelectCheckInDate_Homepage = selectors.getDateAfterNWeeksFromNow(checkInAfterWeeks, true, true);
        By pickSelectCheckOutDate_Homepage = selectors.getDateAfterNWeeksFromNow(checkOutAfterWeeks, false, true);

        actionsOnElementByXpath(By.xpath(selectors.firstSearchResultInProperties_searchResultsPage), elementsActions.CLICK);
        controlCurrentTab();
        scrollDown();
        softAssert.assertTrue(actionsOnElementByXpath(selectors.guestsCheck_firstSearchResultPage, elementsActions.GET_TEXT).contains(String.valueOf(childrenGuests + adultsGuests)),
                "Property doesn't fit the search criteria for guests");
        softAssert.assertTrue(validateElementAppearance(pickSelectCheckInDate_Homepage), "Property doesn't fit the search criteria for checkin date");
        softAssert.assertTrue(validateElementAppearance(pickSelectCheckOutDate_Homepage), "Property doesn't fit the search criteria for checkout date");

        System.out.println("2- Verify that the results and details page match the extra filtersTestCase has ended");
    }

}
