package com.epam.cm.steps.serenity;

import com.epam.cm.core.utils.WebDriverSupport;
import com.epam.cm.dto.SettingsDTO;
import com.epam.cm.pages.SettingsPage;
import net.thucydides.core.annotations.Step;

/**
 * Created by Lev_Serba on 12/12/2016.
 */
public class SettingsPageSteps {
    SettingsPage settingsPage;

    @Step
    public void clickEditLinkNextToEmail() {
        settingsPage.clickEditLinkNextToEmail();
    }

    @Step
    public void clickEditLinkNextToName() {

        settingsPage.clickEditLinkNextToName();
    }

    @Step
    public boolean areCurrentEmailAndNewEmailFieldsVisible() {
        boolean currentEmailFieldVisible = settingsPage.isCurrentEmailFieldVisible();
        boolean newEmailfieldVisible = settingsPage.isNewEmailFieldVisible();
        if (currentEmailFieldVisible && newEmailfieldVisible) {
            return true;
        }
        return false;
    }

    @Step
    public void typeEmail(String email){
        settingsPage.typeEmail(email);

    }

    @Step
    public void clickEmailSaveBtn() {
        settingsPage.clickEmailSaveBtn();
    }

    @Step
    public boolean isErrorMsgShown(String errorMsg) {
        if (settingsPage.getErrorMsg().equals(errorMsg)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isSaveBtnVisible() {
        if (settingsPage.checkSaveBtn())
            return true;
        return false;
    }

    @Step
    public boolean isCancelBtnVisble() {
        if (settingsPage.checkCancelBtn())
            return true;
        return false;
    }

    @Step
    public String getFirstNameLbl() {
        return settingsPage.getFirstLblText();
    }

    @Step
    public String getLastNameLbl() {
        return settingsPage.getSecondLblText();
    }

    @Step
    public void clickNameSaveBtn() {
        settingsPage.clickNameSaveBtn();
    }

    @Step
    public void clickNameCancelBtn(){
        settingsPage.clickNamesCancelBtn();
    }

    @Step
    public void leaveFirstNameInputEmpty() {
        settingsPage.setFirstNameEmpty();
    }

    @Step
    public void typeFirstName(SettingsDTO settingsDTO) {
        settingsPage.setFirstName(settingsDTO.getFirstName());
    }

    @Step
    public void leaveLastNameInputEmpty() {
        settingsPage.setLastNameEmpty();
    }

    @Step
    public void typeLastName(SettingsDTO settingsDTO) {
        settingsPage.setLastName(settingsDTO.getLastName());
    }

    @Step
    public boolean isFirstNameHighlighted() {
        return settingsPage.isFirstNameInputHighlighted();
    }

    @Step
    public boolean isLastNameHighlighted() {
        return settingsPage.isLastNameInputHighlighted();
    }

    @Step
    public String getNameErrorMsg() {
        return settingsPage.getNameErrorMsg();
    }

    @Step

    public boolean isEmailChanged(String newEmail) {
        if(settingsPage.getCurrentEmailText().equals(newEmail)){
            return true;
        }else{
            return false;
        }
    }
    public String getLastNameInput() {
        return settingsPage.getLastNameInputText();
    }

    @Step
    public String getFirstNameInput() {
        return settingsPage.getFirstNameInputText();
    }

    @Step
    public boolean userCantTypeMoreThenMaxLastName(SettingsDTO settingsDTO) {
        if (settingsPage.getLastNameLength() <= settingsDTO.getLastName().length())
            return true;
        WebDriverSupport.reloadPage();
        return false;
    }

    @Step
    public boolean userCantTypeMoreThenMaxFirstName(SettingsDTO settingsDTO) {
        if (settingsPage.getFirstNameLength() <= settingsDTO.getFirstName().length())
            return true;
        WebDriverSupport.reloadPage();
        return false;
    }

    @Step
    public void clickEmailCancelBtn() {
        settingsPage.clickEmailCancelBtn();
    }
}
