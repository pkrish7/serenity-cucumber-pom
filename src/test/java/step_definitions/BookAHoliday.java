package step_definitions;

import mailtravel_pages.HolidayBookingPage;
import mailtravel_pages.HolidayDetailsPage;
import mailtravel_pages.HomePage;
import mailtravel_pages.SignUpToNewsLetterPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import java.text.ParseException;
import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class BookAHoliday {

    @Managed(driver = "chrome")
    WebDriver theBrowser;

    @Steps
    HomePage homePage;
    HolidayDetailsPage holidayDetailsPage;
    HolidayBookingPage holidayBookingPage;
    SignUpToNewsLetterPage signUpToNewsLetterPage;

    @Given("the user is on the mailtravel website home page")
    public void the_user_is_on_the_mailtravel_website() {
        //Go to Mailtravel.co.uk
        theBrowser.get("https://www.mailtravel.co.uk/");

        //Verify website title
        assertThat("Website title not as expected", theBrowser.getTitle(), is("Home Page | Mail Travel"));
    }

    @When("he or she searches for the holiday place {string}")
    public void he_or_she_searches_for_the_holiday_place(String holidayPlace) {
        //Type India in the search box
        homePage.searchForHoliday(holidayPlace);
    }

    @Then("he or she should be able to see holiday details")
    public void he_she_should_be_able_to_see_holiday_details() {
        //Click on More Info
        holidayDetailsPage.clickOnMoreInfo();

        //More Info page should display days, price and telephone number in the div
        ArrayList<String> moreInfoDivDetails = holidayDetailsPage.checkForDetailsInMoreInfoPage();
        Assert.assertTrue("Number of days in Div is displayed as 0", moreInfoDivDetails.get(0).length() > 0);
        Assert.assertTrue("Price per person in Div does not contain £ or an invalid price is displayed", moreInfoDivDetails.get(1).contains("£") && moreInfoDivDetails.get(1).length() >= 2);
        Assert.assertTrue("Telephone number in Div is not having proper value", moreInfoDivDetails.get(2).length() >= 10);

        //Click on Itinerary and Make sure all the days in itinerary are displaying some information
        Assert.assertTrue("One of the days is having empty Itinerary", holidayDetailsPage.clickOnItineraryAndCheckDayWiseContent() == 0);
    }

    @Then("should be able to book a holiday trip on the first available date successfully on giving all required information")
    public void should_be_able_to_book_a_holiday_trip_on_the_first_available_date_successfully_on_giving_all_required_information() throws ParseException, InterruptedException {
        //Click on Book Online
        holidayBookingPage.clickOnBookOnline();

        //Verify the selected date is the first available date in the calendar and it has default values
        holidayBookingPage.isSelectedDateTheFirstAvailableDate();

        holidayBookingPage.clickOnContinueAfterVerifyingTheSelectedBookingDate();

        //Verify that the selected date is set as departure date and (departure date + 9) as the last date
        holidayBookingPage.fetchDepartureAndLastDates();

        //Enter values in accommodation
        holidayBookingPage.chooseNumberOfRoomsAndContinue();

        //Select default values in Extras
        holidayBookingPage.clickOnContinueWithoutExtras();

        //Fill out dummy information in Passenger details and make sure its proceeding to payment page
        holidayBookingPage.fillPassengerDetailsAndClickOnContinue();
        Assert.assertTrue("User is NOT navigated to Payment page successfully", holidayBookingPage.isPaymentPageDisplayed() == true);
    }

    @Then("should be able to sign up to newsletter")
    public void should_be_able_to_sign_up_to_newsletter() {
        //Signup to newsletter with dummy information
        theBrowser.get("https://www.mailtravel.co.uk/");
        signUpToNewsLetterPage.signUpToNewsLetterAndConfirm();

        //Verify Signup confirmation page
        signUpToNewsLetterPage.isSignUpSuccessful();
    }
}
