package pages.mailtravel_pages;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.Managed;
import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static pages.mailtravel_pagelocators.HolidayBookingPageLocators.*;

public class HolidayBookingPage extends PageObject {

    private String selectedDefaultBooking;
    private String selectedMonthYear;
    private String firstAvailableBookingDate;

    @Managed(driver = "chrome")
    WebDriver theBrowser;

    @FindBy(id="pax-a-dobm-2")
    WebElementFacade dobm2Dropdown;

    @FindBy(xpath="//*[contains(@class,'nbf_tpl_pms_calendar_day_available nbf_tpl_pms_calendar_box')]/div[@class='nbf_tpl_pms_calendar_box_dom']")
    List<WebElementFacade> availableBookingDates;

    @FindBy(xpath="//*[@id='transport-0-departure']/td[3]")
    List<WebElementFacade> departureAndLastDates;

    public void clickOnBookOnline() {
        waitFor(BOOK_ONLINE_BUTTON);
        JavascriptExecutor js = (JavascriptExecutor) theBrowser;
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", $(BOOK_ONLINE_BUTTON));
    }

    public String fetchSystemSelectedDay(){
        int attempts = 0;
        while(attempts < 2) {
            try {
                selectedDefaultBooking = $(SELECTED_BOOKING_DAY).getText();
                break;
            } catch(StaleElementReferenceException e) {
                e.getMessage();
            }
        }
        attempts++;
        return selectedDefaultBooking;
    }

    public String fetchSystemSelectedMonthAndYear(){
        int attempts = 0;
        while(attempts < 2) {
            try {
                selectedMonthYear = $(SELECTED_BOOKING_MONTH_AND_YEAR).getText();
                break;
            } catch(StaleElementReferenceException e) {
                e.getMessage();
            }
        }
        attempts++;
        return selectedMonthYear;
    }

    public void isSelectedDateTheFirstAvailableDate(){
        waitFor(AVAILABLE_BOOKING_DATES);

        //First available booking date out of the available dates

        //Sometimes the required web elements are not accessible due to "StaleElementReferenceException".
        // Suspecting this issue is happening due to DOM operation that is temporarily causing the element to be inaccessible.
        // To allow the case, I have tried to access the element several times in a loop before finally throwing an exception.
        int attempts = 0;
        while(attempts < 2) {
            try {
                firstAvailableBookingDate = availableBookingDates.get(0).getText().trim();
                break;
            } catch(StaleElementReferenceException e) {
                e.getMessage();
            }
            }
            attempts++;

        //verify that the selected booking date is same as first available booking date
        String[] selectedDefaultBookingArray = fetchSystemSelectedDay().split("£");
        String selectedDate = selectedDefaultBookingArray[0].trim();
        Assert.assertTrue("The selected booking date is not the first available booking date", selectedDate.contentEquals(firstAvailableBookingDate));

        //verify the selected date contains default values - If the array size is 2, that means it has date followed by price value
        Assert.assertTrue("The selected date does not have a default date and price values", selectedDefaultBookingArray.length == 2);
    }

    public void clickOnContinueAfterVerifyingTheSelectedBookingDate(){
            ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", $(CONTINUE_BUTTON_AFTER_SELECTING_DEPARTURE_DATE));
    }

    public void fetchDepartureAndLastDates() throws ParseException {
        String[] selectedDefaultBookingArray = fetchSystemSelectedDay().split("£");
        String selectedDate = selectedDefaultBookingArray[0].trim();

        //Fetch first 3 characters and last 4 characters from the displayed String. Example, "Apr 2021" from "April 2021"
        String selectedMonthYearFormatted = fetchSystemSelectedMonthAndYear().substring(0,3).concat(" ").concat(fetchSystemSelectedMonthAndYear().substring(fetchSystemSelectedMonthAndYear().length()-4));
        String selectedDateMonthYear = selectedDate.concat(" ").concat(selectedMonthYearFormatted);

        waitFor(TRANSPORT_ACCORDIAN);

        //Click on TRANSPORT accordian to fetch departure and last dates
        int attempts = 0;
        while(attempts < 2) {
            try {
                ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", $(TRANSPORT_ACCORDIAN));
                break;
            } catch(StaleElementReferenceException e) {
                e.getMessage();
            }
        }
        attempts++;

        //Verify that the selected date is set as departure date
        Assert.assertTrue(selectedDateMonthYear.contentEquals(departureAndLastDates.get(0).getText().substring(4)));

        //Verify that the last date date is (departure date + 9years)
        String departureDateFormatted = departureAndLastDates.get(0).getText().substring(4).replace(" ", "-");
        String lastDateFormatted = departureAndLastDates.get(1).getText().substring(4).replace(" ", "-");

        Date departureDateParsed=new SimpleDateFormat("dd-MMM-yyyy").parse(departureDateFormatted);
        Date lastDateParsed=new SimpleDateFormat("dd-MMM-yyyy").parse(lastDateFormatted);
        long differenceInTime = lastDateParsed.getTime() - departureDateParsed.getTime();
        long differenceInDays = TimeUnit.DAYS.convert(differenceInTime, TimeUnit.MILLISECONDS);
        Assert.assertTrue("Last date is not equal to (departure date + 9years)", differenceInDays == 9);
    }

    public void chooseNumberOfRoomsAndContinue() throws InterruptedException {
        Thread.sleep(3000);
        waitFor(ACCOMODATION_DROPDOWN);
        $(ACCOMODATION_DROPDOWN).selectByVisibleText("1");
        $(SELECT_ROOM_BUTTON).click();
    }

    public void clickOnContinueWithoutExtras() {
        $(EXTRAS_BUTTON).click();
    }

    public void fillPassengerDetailsAndClickOnContinue() {
        //Tried below looping mechanism as sometimes first and last name elements are not accessible due to "StaleElementReferenceException".
        int attempts = 0;
        while(attempts < 2) {
            try {
                $(ADULT1_TITLE_DROPDOWN).selectByValue("Mr");
                typeInto($(ADULT1_FIRST_NAME_TEXTBOX), "firstName1");
                typeInto($(ADULT1_LAST_NAME_TEXTBOX), "lastName1");
                break;
            } catch(StaleElementReferenceException e) {
                e.getMessage();
            }
        }
        attempts++;

        $(ADULT1_DOB_DATE_DROPDOWN).selectByVisibleText("1");
        $(ADULT1_DOB_MONTH_DROPDOWN).selectByVisibleText("January");
        $(ADULT1_DOB_YEAR_DROPDOWN).selectByVisibleText("2000");

        //Enter details of second passenger
        $(ADULT2_TITLE_DROPDOWN).selectByValue("Mr");
        typeInto($(ADULT2_FIRST_NAME_TEXTBOX),"firstName2");
        typeInto($(ADULT2_LAST_NAME_TEXTBOX),"lastName2");
        $(ADULT2_DOB_DATE_DROPDOWN).selectByVisibleText("1");
        dobm2Dropdown.selectByVisibleText("January");
        $(ADULT2_DOB_YEAR_DROPDOWN).selectByVisibleText("2000");

        //Enter contact details
        //typeInto($(CONTACT_NAME_TEXTBOX),"Contact Name"); //Not required as the first passenger name gets auto populated
        typeInto($(CONTACT_NUMBER_TEXTBOX),"1234567890");
        typeInto($(CONTACT_EMAIL_TEXTBOX),"contact@abc.net");
        typeInto($(CONTACT_ADDRESSLINE1_TEXTBOX),"LON");
        typeInto($(CONTACT_ADDRESSLINE2_TEXTBOX),"LON");
        typeInto($(CONTACT_CITY_TEXTBOX),"LON");
        typeInto($(CONTACT_POSTCODE_TEXTBOX),"N11 GHT");
        $(CONTACT_HEARABOUT_DROPDOWN).selectByVisibleText("In Paper");

        $(CONTINUE_BUTTON).click();
    }

    public boolean isPaymentPageDisplayed() {
        waitFor(PAYMENT_PAGE);
        return $(PAYMENT_PAGE).getText().contains("enter your payment card details");
    }
}
