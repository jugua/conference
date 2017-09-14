package com.epam.cm.steps.serenity;

import com.epam.cm.core.utils.WebDriverSupport;
import com.epam.cm.dto.MyInfoFieldsDTO;
import com.epam.cm.pages.HomePage;
import com.epam.cm.pages.MyInfoPage;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;

public class MyInfoPageSteps extends ScenarioSteps {

    MyInfoPage myInfoPage;
    HomePage homePage;

    @Step
    public void clickMyInfoOption() {
        homePage.waitForPageToLoad();
        myInfoPage.getMenu().clickAccountMenuButton();
        myInfoPage.getMenu().clickMyInfoButton();
    }

    @Step
    public boolean isMyInfoTabActive() {
        if (myInfoPage.isMyInfoTabActive()) {
            return true;
        } else {
            return false;
        }
    }

    @Step
    public boolean areAllFieldsEmpty() {
        myInfoPage.waitForPageToLoad();
        return myInfoPage.areAllFieldsEmpty();
    }

    @Step
    public void userFillValidInfo(MyInfoFieldsDTO myInfoDTO) {
        myInfoPage.waitForPageToLoad();
        myInfoPage.fillMyInfoFields(myInfoDTO);
    }

    @Step
    public boolean isInformationSavedSuccessfullyPopUp() {
        if (myInfoPage.isInformationSavedSuccessfullyPopUp()) {
            myInfoPage.clickInformationSavedSuccessfullyPopUpOkButton();
            // WebDriverSupport.reloadPage();
            return true;
        }
        return false;
    }

    @Step
    public boolean isInformationSavedSuccessfully(MyInfoFieldsDTO myInfoTable) {
        myInfoPage.waitForPageToLoad();
        return myInfoPage.isInformationSavedSuccessfully(myInfoTable);
    }

    @Step
    public void userClickSaveButton() {
        myInfoPage.clickSaveButton();
    }

    @Step
    public void userGoAwayFromMyInfoTab() {
        myInfoPage.clickMyTalksTab();
    }

    @Step
    public boolean isAttentionPopUpShown() {
        if (myInfoPage.isAttentionPopUpShown()) {
            myInfoPage.clickAttentionPopUpYesButton();
            WebDriverSupport.reloadPage();
            return true;
        }
        return false;
    }

    @Step
    public boolean isErrorPopUpShown() {
        if (myInfoPage.isErrorPopUpShown()) {
            myInfoPage.clickErrorPopUpOkButton();
            WebDriverSupport.reloadPage();
            return true;
        }
        return false;
    }

    @Step
    public boolean userCantTypeMoreThanMaxAllowedCharacters(MyInfoFieldsDTO myInfoDTO) {
        if (myInfoPage.getShortBioActualLength() <= myInfoDTO.getShortBio().length()
                && myInfoPage.getJobTitleActualLength() <= myInfoDTO.getJobTitle().length()
                && myInfoPage.getPastConferencesActualLength() <= myInfoDTO.getPastConferences().length()
                && myInfoPage.getAdditionalInfoActualLength() <= myInfoDTO.getAdditionalInfo().length()) {
            WebDriverSupport.reloadPage();
            return true;
        }
        return false;
    }

    @Step
    public void switchToMyTalks() {
        myInfoPage.clickMyTalksTab();
    }

    @Step
    public boolean isMyTalksTabActive() {
        if (myInfoPage.myTalksTabIsActive()) {
            return true;
        } else {
            return false;
        }
    }

    @Step
    public void switchToMyInfo() {
        myInfoPage.clickMyInfoTab();
    }

    @Step
    public void userLogout() {
        myInfoPage.waitForPageToLoad();
        myInfoPage.scrollToTop();

        if (!myInfoPage.getMenu().isAccountMenuUnfolded()) {
            myInfoPage.getMenu().clickAccountMenuButton();
        }
        myInfoPage.getMenu().clickSignOutButton();

    }
}
