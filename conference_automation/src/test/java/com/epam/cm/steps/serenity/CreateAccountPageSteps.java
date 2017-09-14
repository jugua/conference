package com.epam.cm.steps.serenity;

import java.util.ArrayList;
import java.util.regex.Pattern;

import com.epam.cm.core.mail.MailCatcherClient;
import com.epam.cm.dto.UserRegistrationInfoDTO;
import com.epam.cm.pages.HomePage;
import com.epam.cm.pages.SignUpPage;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;


public class CreateAccountPageSteps extends ScenarioSteps {

    public static final String HREF_REGEX = "href=\"([^\"]*)\"";
    public static final String UNSIGNED_ACC_TITLE = "Your Account";
    SignUpPage signUpPage;
    HomePage homePage;
    MailCatcherClient mailCatcherClient = new MailCatcherClient();

    @Step
    public void goToSignUpPage() {
        signUpPage.open();
    }

    @Step
    public void typeRegistrationInfo(UserRegistrationInfoDTO user) {
        signUpPage.fillRegistrationInfo(user);
    }

    @Step
    public void clickSubmitBtn() {
        signUpPage.clickSubmit();
    }

    @Step
    public boolean isRegisteredSuccessfully() {
        if (signUpPage.isRegistered()) {
            return true;
        } else {
            return false;
        }
    }

    public String getHighlightedTextInEmptyFields(String emptyFieldsTemplate, String expectedText) {
        return signUpPage.getHighlightedTextEmptyFields(emptyFieldsTemplate, expectedText);
    }

    public void fillRegistrationInfoNegativeTest(UserRegistrationInfoDTO user) {
        signUpPage.fillRegistrationInfoNegativeTest(user);
    }

    public String getHighlightedTextInIncorrectFields(String incorrectFieldsTemplate, String firstExpectedText,
            String secondExpectedText) {
        return signUpPage.getHighlightedTextIncorrectFields(incorrectFieldsTemplate, firstExpectedText,
                secondExpectedText);
    }

    public int getNameFieldLength() {
        return signUpPage.getNameFieldLength();
    }

    public int getLastNameFieldLength() {
        return signUpPage.getLastNameFieldLength();
    }

    public int getPassFieldLength() {
        return signUpPage.getPassFieldLength();
    }

    public int getConfPassFieldLength() {
        return signUpPage.getConfPassFieldLength();
    }

    @Step
    public void openLinkFromEmail() {
        String emailHtml = mailCatcherClient
                .getEmailById(mailCatcherClient.getLastEmail().getId(), MailCatcherClient.ResponseType.HTML).toString();
        Pattern pattern = Pattern.compile(HREF_REGEX);
        java.util.regex.Matcher matcher = pattern.matcher(emailHtml);
        ArrayList<String> links = new ArrayList<String>();
        while (matcher.find()) {
            links.add(matcher.group(1));
        }
        String link = links.get(links.size() - 1);
        getDriver().get(link);
    }

    @Step
    public boolean isUserLoggedIn() {
        homePage.waitForPageToLoad();
        String accountMenuTitle = homePage.getMenu().getAccountMenuTitle();
        if (!accountMenuTitle.matches(UNSIGNED_ACC_TITLE)) {
            return true;
        }
        return false;
    }
}
