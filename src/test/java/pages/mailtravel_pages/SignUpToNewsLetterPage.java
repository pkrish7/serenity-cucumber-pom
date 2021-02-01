package pages.mailtravel_pages;

import net.serenitybdd.core.pages.PageObject;

import static pages.mailtravel_pagelocators.SignUpToNewsLetterPageLocators.*;

public class SignUpToNewsLetterPage extends PageObject {

    public void signUpToNewsLetterAndConfirm() {
        waitFor(SIGNUP_NEWSLETTER_LINK);
        findBy(SIGNUP_NEWSLETTER_LINK).click();
        findBy(SIGNUP_TITLE).selectByVisibleText("Miss");
        findBy(SIGNUP_EMAIL).type("test@abc.abc");
        findBy(SIGNUP_FIRST_NAME).type("tester");
        findBy(SIGNUP_LAST_NAME).type("tester");
        findBy(SIGNUP_POSTCODE).type("N11 GHT");
        $(SIGNUP_CHECKBOX).click();
        $(SIGNUP_BUTTON).click();

    }

    public void isSignUpSuccessful() {
        findBy(SIGNUP_CONFIRMATION).isDisplayed();
    }

}
