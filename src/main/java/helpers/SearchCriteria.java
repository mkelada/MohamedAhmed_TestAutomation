package helpers;

import org.junit.*;
import org.openqa.selenium.*;
import org.testng.annotations.AfterClass;
import utils.UISelectors;
import org.testng.asserts.SoftAssert;

import java.time.LocalDate;
import java.util.List;

public class SearchCriteria extends UIHelper {

    UISelectors selectors = new UISelectors();
    SoftAssert softAssert = new SoftAssert();

    public boolean stopTryingToSearch = false;

    @AfterClass
    public void generateSoftAssertions() {
        softAssert.assertAll();
    }

    public void verifyThatTheResultsMatchTheSearchCriteria(String location, int checkInAfterWeeks, int checkOutAfterWeeks, int adultGuests, int childrenGuests, int trails) {
        By pickChooseCheckInDate_Homepage = selectors.getDateAfterNWeeksFromNow(checkInAfterWeeks, true, false);
        By pickChooseCheckOutDate_Homepage = selectors.getDateAfterNWeeksFromNow(checkOutAfterWeeks, false, false);
        By pickSelectCheckInDate_Homepage = selectors.getDateAfterNWeeksFromNow(checkInAfterWeeks, true, true);
        By pickSelectCheckOutDate_Homepage = selectors.getDateAfterNWeeksFromNow(checkOutAfterWeeks, false, true);

        actionsOnElementByXpath(selectors.locationField_Homepage, elementsActions.SEND_KEYS, location);

        try {
            if (validateTextOnElement(selectors.locationSearchFirstResult_Homepage, location)) {
                actionsOnElementByXpath(selectors.locationSearchFirstResult_Homepage, elementsActions.CLICK);
            } else {
                Assert.fail(String.format("Failed to find search results, will try %s time(s)", trails));
            }
        } catch (AssertionError e) {
            for (int i = 0; i < trails; i++) {
                refreshPage();
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

        softAssert.assertTrue(selectedCheckInDate.contains(String.valueOf(LocalDate.now().plusWeeks(checkInAfterWeeks).getDayOfMonth())), "Selected CheckIn date is wrong");
        softAssert.assertTrue(selectedCheckOutDate.contains(String.valueOf(LocalDate.now().plusWeeks(checkOutAfterWeeks).getDayOfMonth())), "Selected CheckOut date is wrong");
        actionsOnElementByXpath(selectors.exactDatesButton_CheckInHomepage, elementsActions.CLICK);

        actionsOnElementByXpath(selectors.guestsButton_Homepage, elementsActions.CLICK);
        softAssert.assertTrue(validateElementAppearance(selectors.adultsLabel_GuestsHomePage), "Guests is not clicked");
        actionsOnElementByXpath(selectors.increaseAdultsButton_GuestsHomepage, elementsActions.CLICK, String.valueOf(adultGuests));
        String selectedAdultsValue = actionsOnElementByXpath(selectors.selectedAdultsValue_GuestsHomepage, elementsActions.GET_TEXT);
        softAssert.assertEquals(selectedAdultsValue, adultGuests, "Adults value is wrong");
        actionsOnElementByXpath(selectors.increaseChildrenButton_GuestsHomepage, elementsActions.CLICK, String.valueOf(childrenGuests));
        String selectedChildrenValue = actionsOnElementByXpath(selectors.selectedChildrenValue_GuestsHomepage, elementsActions.GET_TEXT);
        softAssert.assertEquals(selectedChildrenValue, childrenGuests, "Children value is wrong");

        actionsOnElementByXpath(selectors.searchButton_Homepage, elementsActions.CLICK);
        softAssert.assertTrue(validateURL(location.split(",")[0]), "Wrong URL");
        softAssert.assertTrue(validateURL(String.format("checkin=%s&checkout=%s&adults=%s&children=%s",
                LocalDate.now().plusWeeks(checkInAfterWeeks), LocalDate.now().plusWeeks(checkOutAfterWeeks), adultGuests, childrenGuests)), "Wrong URL");

        List<WebElement> elementsTextsInSearchResultsBar = getElementsTexts(selectors.searchResultsBar_searchResultsPage);
        List<WebElement> elementsTextsValuesInSearchResultsBar = getElementsTexts(selectors.searchResultsBarValues_searchResultsPage);
        for (int i = 0; i < elementsTextsInSearchResultsBar.size(); i++) {
            if (elementsTextsInSearchResultsBar.get(i).getText().equals("Location")) {
                softAssert.assertEquals(location.split(",")[0], elementsTextsValuesInSearchResultsBar.get(i), "Wrong Location");
            } else if (elementsTextsInSearchResultsBar.get(i).getText().equals("Check in / Check out")) {
                softAssert.assertEquals(selectors.searchResultsDateValidator(checkInAfterWeeks, checkOutAfterWeeks), elementsTextsValuesInSearchResultsBar.get(i), "Wrong Check in / Check out");
            } else if (elementsTextsInSearchResultsBar.get(i).getText().equals("Guests")) {
                softAssert.assertEquals(String.format("%s guests", adultGuests + childrenGuests), elementsTextsValuesInSearchResultsBar.get(i), "Wrong Location");
            }
        }

        if (!validateElementAppearance(selectors.anyWhereOnTheMap)) {
            if (!stopTryingToSearch) {
                stopTryingToSearch = true;
                backToPreviousPage();
                refreshPage();
                verifyThatTheResultsMatchTheSearchCriteria(location, checkInAfterWeeks, checkOutAfterWeeks, adultGuests, childrenGuests, trails);
            }
        }
        Assert.assertTrue(validateElementAppearance(selectors.anyWhereOnTheMap));

    }

}
