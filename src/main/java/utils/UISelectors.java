package utils;

import org.openqa.selenium.By;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

public class UISelectors {

    //HomePage
    public By expectedHomepageElement = By.xpath(xpathGenerator("aria-label", "Airbnb Homepage"));
    public By locationField_Homepage = By.xpath(xpathGenerator("data-testid", "structured-search-input-field-query"));
    public By locationSearchFirstResult_Homepage = By.xpath(xpathGenerator("data-testid", "option-0"));
    public By checkInButton_Homepage = By.xpath(xpathGenerator("data-testid", "structured-search-input-field-split-dates-0"));
    public By calendarButton_CheckInHomepage = By.xpath(xpathGenerator("id", "tab--tabs--0"));
    public By nextButtonInCalendar_CheckInHomePage = By.xpath("(//*[@aria-label='Next'])[1]");
    public By exactDatesButton_CheckInHomepage = By.xpath(xpathGenerator("aria-label", "Exact dates"));
    public By guestsButton_Homepage = By.xpath(xpathGenerator("data-testid", "structured-search-input-field-guests-button"));
    public By adultsLabel_GuestsHomePage = By.xpath(xpathGenerator("id", "searchFlow-title-label-stepper-adults"));
    public By increaseAdultsButton_GuestsHomepage = By.xpath((xpathGenerator("data-testid", "stepper-adults-increase-button")));
    public By selectedAdultsValue_GuestsHomepage = By.xpath(xpathGenerator("data-testid", "stepper-adults-value"));
    public By increaseChildrenButton_GuestsHomepage = By.xpath((xpathGenerator("data-testid", "stepper-children-increase-button")));
    public By selectedChildrenValue_GuestsHomepage = By.xpath(xpathGenerator("data-testid", "stepper-children-value"));
    public By searchButton_Homepage = By.xpath("//*[text()='Search']/parent::div/parent::span/parent::button");

    //Search results page
    public By searchResultsBar_searchResultsPage = By.xpath(xpathGenerator("data-testid", "little-search", "/button/span"));
    public By searchResultsBarValues_searchResultsPage = By.xpath(xpathGenerator("data-testid", "little-search", "/button/div"));
    String searchResultsInformation = xpathGenerator("data-testid", "shimmer-legacy-listing-section-item", "/following::div//*[contains(text(), '%s')]");
    public By searchResultsGuests_searchResultsPage = By.xpath(String.format(searchResultsInformation, "guests"));
    public String firstSearchResultInProperties_searchResultsPage = "(//*[@itemprop='itemListElement'])[1]";
    public String searchResultsPricesInProperties_searchResultsPage = "//*[contains(text(),'$') and contains(text(),'%s')]";
    public String priceOfPropertyToHover_searchResultsPage = "(//*[text()='%s'])[1]/parent::div";

    //Map
    public String priceOfPropertyToHover_map = "(//*[text()='%s'])[2]/parent::div/parent::div";
    public String containerOfPriceOfPropertyToHover_map = "(//*[text()='%s'])[2]/parent::div/parent::div/parent::div";
    public String buttonsOfCards_map = "(//*[@data-veloute='map/markers/BasePillMarker'])[%s]";
    public By pricesOfCards_map = By.xpath(xpathGenerator("data-veloute", "map/markers/BasePillMarker", "/div/div/div/span"));
    public By anyWhereOnTheMap = By.xpath(xpathGenerator("aria-roledescription", "map", "/parent::div/parent::div"));

    //First result detailed page
    public By guestsCheck_firstSearchResultPage = By.xpath(xpathGenerator("id", "GuestPicker-book_it-trigger"));


    /**
     * Method to build and return By Value for date selection criteria
     *
     * @param weeks      number after N Weeks
     * @param isStart    boolean value to choose to return start or end date
     * @param isSelected boolean value to choose to return chosen or selected date
     * @return By value to locate the element
     */
    public By getDateAfterNWeeksFromNow(int weeks, boolean isStart, boolean isSelected) {
        String chooseStart = "Choose %s, %s %s, %s as your start date. It's available.",
                chooseEnd = "Choose %s, %s %s, %s as your end date. It's available.",
                selectedStart = "Selected start date. %s, %s %s, %s",
                selectedEnd = "Selected end date. %s, %s %s, %s";

        LocalDate currentDate = LocalDate.now().plusWeeks(weeks);
        String day = currentDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.US);
        String month = currentDate.getMonth().getDisplayName(TextStyle.FULL, Locale.US);
        int dayOfMonth = currentDate.getDayOfMonth();
        int year = currentDate.getYear();
        if (isStart) {
            if (!isSelected) {
                return By.xpath(xpathGenerator("aria-label",
                        String.format(chooseStart,
                                day, month, dayOfMonth, year)));
            } else {
                return By.xpath(xpathGenerator("aria-label",
                        String.format(selectedStart,
                                day, month, dayOfMonth, year)));
            }
        } else {
            if (!isSelected) {
                return By.xpath(xpathGenerator("aria-label",
                        String.format(chooseEnd,
                                day, month, dayOfMonth, year)));
            } else {
                return By.xpath(xpathGenerator("aria-label",
                        String.format(selectedEnd,
                                day, month, dayOfMonth, year)));
            }
        }
    }

    private String xpathGenerator(String attributeName, String attributeValue, String... concatenateText) {
        if (concatenateText.length == 0)
            return String.format("//*[@%s=\"%s\"]", attributeName, attributeValue);
        else
            return String.format("//*[@%s=\"%s\"]%s", attributeName, attributeValue, concatenateText[0]);
    }

    public String searchResultsDateValidator(int checkInAfterWeeks, int checkOutAfterWeeks) {
        int dayCheckIn = LocalDate.now().plusWeeks(checkInAfterWeeks).getDayOfMonth();
        String MonthCheckIn = LocalDate.now().plusWeeks(checkInAfterWeeks).getMonth().getDisplayName(TextStyle.SHORT, Locale.US);
        int dayCheckOut = LocalDate.now().plusWeeks(checkOutAfterWeeks).getDayOfMonth();
        String MonthCheckOut = LocalDate.now().plusWeeks(checkOutAfterWeeks).getMonth().getDisplayName(TextStyle.SHORT, Locale.US);

        if (MonthCheckIn.equals(MonthCheckOut)) {
            return String.format("%s %s – %s",
                    MonthCheckIn, dayCheckIn, dayCheckOut);
        } else {
            return String.format("%s %s – %s %s",
                    MonthCheckIn, dayCheckIn, MonthCheckOut, dayCheckOut);
        }
    }

}
