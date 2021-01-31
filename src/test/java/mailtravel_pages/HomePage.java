package mailtravel_pages;

import net.serenitybdd.core.pages.PageObject;
import net.thucydides.core.annotations.DefaultUrl;
import org.openqa.selenium.Keys;

import static mailtravel_pagelocators.HomePageLocators.ACCEPT_COOKIES_BUTTON;
import static mailtravel_pagelocators.HomePageLocators.HOLIDAY_SEARCHBOX;

@DefaultUrl("https://www.mailtravel.co.uk/")
public class HomePage extends PageObject {

    public void searchForHoliday(String itemName) {
        waitFor(ACCEPT_COOKIES_BUTTON);
        $(ACCEPT_COOKIES_BUTTON).click();
        $(HOLIDAY_SEARCHBOX).type(itemName)
                .then().sendKeys(Keys.ENTER);
    }

}
