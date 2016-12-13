package com.epam.cm.steps.serenity;

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
    public void clickEditLinkNextToName(){
        settingsPage.clickEditLinkNextToName();
    }

    @Step
    public boolean areCurrentEmailAndNewEmailFieldsVisible() {
        boolean currentEmailFieldVisible = settingsPage.isCurrentEmailFieldVisible();
        boolean newEmailfieldVisible = settingsPage.isNewEmailFieldVisible();
        if(currentEmailFieldVisible && newEmailfieldVisible){
            return true;
        }
        return false;
    }

    @Step
    public boolean isSaveBtnVisible(){
        if(settingsPage.checkSaveBtn())
            return true;
        return false;
    }

    @Step
    public boolean isCancelBtnVisble(){
        if (settingsPage.checkCancelBtn())
            return true;
        return false;
    }

    @Step
    public String getFirstNameLbl(){
        return settingsPage.getFirstLblText();
    }

    @Step
    public String getLastNameLbl(){
        return settingsPage.getSecondLblText();
    }

    @Step
    public void clickSaveBtn(){
        settingsPage.clickNameSaveBtn();
    }
    @Step
    public void leaveLastNameInputEmpty(){
        settingsPage.setLastNameEmpty();
    }

    @Step
    public boolean isLastNameHighlighted(){
        return settingsPage.isLastNameInputHighlighted();
    }

    @Step
    public String getLastNameErrorMsg(){
        return settingsPage.getLastNameErrorMsg();
    }

}
