package com.epam.cm.steps.serenity;

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
    public boolean areCurrentEmailAndNewEmailFieldsVisible() {
        boolean currentEmailFieldVisible = settingsPage.isCurrentEmailFieldVisible();
        boolean newEmailfieldVisible = settingsPage.isNewEmailFieldVisible();
        if(currentEmailFieldVisible && newEmailfieldVisible){
            return true;
        }
        return false;
    }

    @Step
    public void typeEmail(SettingsDTO settingsDTO){
        settingsPage.typeEmail(settingsDTO.getEmail());
    }

    @Step
    public void clickSaveBtn() {
        settingsPage.clickSaveBtn();
    }

    @Step
    public boolean isErrorMsgShown(String errorMsg) {
        if(settingsPage.getErrorMsg().equals(errorMsg)){
            return true;
        }else{
            return false;
        }
    }
}
