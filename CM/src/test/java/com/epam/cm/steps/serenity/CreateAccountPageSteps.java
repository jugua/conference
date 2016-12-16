package com.epam.cm.steps.serenity;

import com.epam.cm.core.mail.MailCatcherClient;
import com.epam.cm.core.utils.WebDriverSupport;
import com.epam.cm.dto.UserRegistrationInfoDTO;
import com.epam.cm.pages.SignUpPage;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;

import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Created by Lev_Serba on 11/10/2016.
 */
public class CreateAccountPageSteps extends ScenarioSteps {

    SignUpPage signUpPage;
    MailCatcherClient mailCatcherClient = new MailCatcherClient();

    @Step
    public void goToSignUpPage() {
        signUpPage.open();
        // waitABit(2000);
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
        String emailHtml = mailCatcherClient.getEmailById(mailCatcherClient.getLastEmail().getId(), MailCatcherClient.ResponseType.HTML).toString();
        Pattern pattern = Pattern.compile("href=\"([^\"]*)\"");
        java.util.regex.Matcher matcher = pattern.matcher(emailHtml);
        ArrayList<String> links = new ArrayList<String>();
        while(matcher.find()){
            links.add(matcher.group(1));
        }
        String link = links.get(links.size()-1);
        getDriver().get(link);
    }

    @Step
    public boolean isUserLoggedIn() {
        signUpPage.waitForPageToLoad();
        String accountMenuTitle = signUpPage.getMenu().getAccountMenuTitle();
        if(!accountMenuTitle.matches("Your Account")){
            return true;
        }
        return false;
    }
}
