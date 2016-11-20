package com.epam.cm.steps.serenity;

import com.epam.cm.dto.UserRegistrationInfoDTO;
import com.epam.cm.pages.SignUpPage;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;

/**
 * Created by Lev_Serba on 11/10/2016.
 */
public class CreateAccountPageSteps extends ScenarioSteps {

    SignUpPage signUpPage;

    @Step
    public void goToSignUpPage() {
        signUpPage.open();
    }

    @Step
    public void typeRegistrationInfo(UserRegistrationInfoDTO user){
        signUpPage.fillRegistrationInfo(user);
    }

    @Step
    public void clickSubmitBtn() {
        signUpPage.clickSubmit();
    }

    @Step
    public boolean isRegisteredSuccessfully() {

        if(signUpPage.isRegistered()){
            return true;
        }else{
            return false;
        }

    }

    public String getHighlightedTextInEmptyFields(String emptyFieldsTemplate, String expectedText) {
        return signUpPage.getHighlightedTextEmptyFields(emptyFieldsTemplate, expectedText);
    }

    public void fillRegistrationInfoNegativeTest(UserRegistrationInfoDTO user) {
        signUpPage.fillRegistrationInfoNegativeTest(user);
    }

    public String getHighlightedTextInIncorrectFields(String incorrectFieldsTemplate, String firstExpectedText, String secondExpectedText) {
        return signUpPage.getHighlightedTextIncorrectFields(incorrectFieldsTemplate, firstExpectedText, secondExpectedText);
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
}
