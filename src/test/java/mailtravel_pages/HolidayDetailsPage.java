package mailtravel_pages;

import net.serenitybdd.core.pages.PageObject;

import java.util.ArrayList;

import static mailtravel_pagelocators.HolidayDetailsPageLocators.*;

public class HolidayDetailsPage extends PageObject {
//    private static final String MORE_INFO_BUTTON = "#iterator_1_product_custom_more-info-button";
//    private static final String ITINERARY_LINK = "//a[@href='#itinerary_title']";
//    private static final String ITINERARY_LIST = ".nbf_tpl_pagesection_vertical_norwd itinerary-month-wrap";
//    private static final String DAY1_ITINERARY = "#itin_iter_1_day-text";
//    private static final String DAY2_ITINERARY = "#itin_iter_2_day-text";
//    private static final String DAY3_ITINERARY = "#itin_iter_3_day-text";
//    private static final String DAY4_ITINERARY = "#itin_iter_4_day-text";
//    private static final String DAY5_ITINERARY = "#itin_iter_5_day-text";
//    private static final String DAY6_ITINERARY = "#itin_iter_6_day-text";
//    private static final String DAY7_ITINERARY = "#itin_iter_7_day-text";
//    private static final String DAY8_ITINERARY = "#itin_iter_8_day-text";
//    private static final String DAY9_ITINERARY = "#itin_iter_9_day-text";
//    private static final String DAY10_ITINERARY = "#itin_iter_10_day-text";
//    private static final String BOOK_ONLINE_BUTTON = "//*[@id='book-98f14691e2018479869775ac1879f0dd']/button/div[5]/div";
//    private static final String SELECT_DEPARTURE_BUTTON ="//*[@id='tour-bf']/div[2]/table/tbody/tr/td[3]/form/button/div[5]/div";


    public void clickOnMoreInfo() {
        waitFor(MORE_INFO_BUTTON);
        $(MORE_INFO_BUTTON).click();
    }

    public ArrayList<String> checkForDetailsInMoreInfoPage(){
        ArrayList<String> moreInfoDivValues = new ArrayList<>();
        String numberOfDays = findBy(NUMBER_OF_DAYS_TEXT).getText();
        String gbpPerPerson = findBy(GBP_PER_PERSON_TEXT).getText();
        String callUsPhoneNumber = String.valueOf(findBy(CALLUS_TEXT).getText());
        moreInfoDivValues.add(numberOfDays);
        moreInfoDivValues.add(gbpPerPerson);
        moreInfoDivValues.add(callUsPhoneNumber);
        return moreInfoDivValues;
    }

    public int clickOnItineraryAndCheckDayWiseContent() {
        if(findBy(ROW_DIV).isCurrentlyEnabled()){
            findBy(ROW_DIV).click();
        }
        int emptyItineraryCounter = 0;

        for (int i = 1; i < 11; i++) {
            String itineraryDayNumber = DAYWISE_ITINERARY_1.concat(String.valueOf(i)).concat(DAYWISE_ITINERARY_2);
            findBy(itineraryDayNumber).click();
            String itineraryDayText = DAYWISE_ITINERARY_1.concat(String.valueOf(i)).concat(DAYWISE_ITINERARY_TEXT);
            waitFor(itineraryDayText);
            if (findBy(itineraryDayText).getText().isEmpty()) {
                emptyItineraryCounter++;
            }
        }
        return emptyItineraryCounter;
    }


}
