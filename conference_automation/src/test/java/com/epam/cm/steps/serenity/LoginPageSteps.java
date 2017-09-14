package com.epam.cm.steps.serenity;

import java.util.List;

import com.epam.cm.core.utils.WebDriverSupport;
import com.epam.cm.dto.AccountButtonDTO;
import com.epam.cm.dto.CredentialsDTO;
import com.epam.cm.pages.HomePage;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.pages.Pages;
import net.thucydides.core.steps.ScenarioSteps;

public class LoginPageSteps extends ScenarioSteps {

    HomePage homePage;

    public LoginPageSteps(final Pages pages) {
        super(pages);
    }

    @Step
    public void unsignedUserInHomePage() {
        homePage.open();
        homePage.waitForPageToLoad();
        if (!homePage.getMenu().getAccountMenuTitle().matches("Your Account")) {
            logout();
        }
        WebDriverSupport.reloadPage();
    }

    @Step
    public void loginInOneStep(CredentialsDTO user){
        homePage.open();
        homePage.waitForPageToLoad();
        if (!homePage.getMenu().getAccountMenuTitle().matches("Your Account")) {
            logout();
        }
        WebDriverSupport.reloadPage();
        homePage.getMenu().clickAccountMenuButton();
        homePage.getMenu().setLoginField(user.getEmail());
        homePage.getMenu().setPasswordField(user.getPassword());
        homePage.getMenu().clickSignInButton();
    }

    @Step
    public void clickOnAccountMenu() {

        homePage.getMenu().clickAccountMenuButton();
    }

    @Step
    public boolean isAccountMenuUnfolded() {
        return homePage.getMenu().isAccountMenuUnfolded();
    }

    @Step
    public void typeLoginAndPassword(CredentialsDTO user) {
        homePage.getMenu().setLoginField(user.getEmail());
        homePage.getMenu().setPasswordField(user.getPassword());

    }

    @Step
    public void clickSignInButton() {
        homePage.getMenu().clickSignInButton();

    }

    @Step
    public String getAccountMenuTitle() {
        waitABit(1000);
        return homePage.getMenu().getAccountMenuTitle();
    }

    @Step
    public void logout() {

        homePage.waitForPageToLoad();

        if (!homePage.getMenu().isAccountMenuUnfolded()) {
            homePage.getMenu().clickAccountMenuButton();
        }
        homePage.getMenu().clickSignOutButton();

    }

    @Step
    public boolean isLoggedIn() {
        if (!homePage.getMenu().isAccountMenuUnfolded()) {
            homePage.getMenu().clickAccountMenuButton();
        }
        if (homePage.getMenu().isSignOutBtnExist())
            return true;
        return false;
    }

    @Step
    public boolean isLoggedInAsSpeaker() {
        if (!homePage.getMenu().isAccountMenuUnfolded()) {
            homePage.getMenu().clickAccountMenuButton();
        }
        if (homePage.getMenu().isSignOutBtnExist())
            return true;
        return false;
    }

    @Step
    public boolean isHomePageOpened() {
        homePage.waitForPageToLoad();
        return pages().isCurrentPageAt(HomePage.class);
    }

    @Step
    public boolean isSignInFormOpened() {

        if (homePage.getMenu().isAccountMenuUnfolded() && !homePage.getMenu().isSignOutBtnExist())
            return true;
        return false;
    }

    @Step
    public void clickMyTalks() {
        homePage.waitForPageToLoad();

        if (!homePage.getMenu().isAccountMenuUnfolded()) {
            homePage.getMenu().clickAccountMenuButton();
        }
        homePage.getMenu().clickMyTalksBtn();
    }

    @Step
    public void clickManageUser(){
        homePage.waitForPageToLoad();

        if (!homePage.getMenu().isAccountMenuUnfolded()) {
            homePage.getMenu().clickAccountMenuButton();
        }
        homePage.getMenu().clickManageUserBtn();
    }

    @Step
    public void clickTalksBtnAsOrg(){
        homePage.waitForPageToLoad();

        if (!homePage.getMenu().isAccountMenuUnfolded()) {
            homePage.getMenu().clickAccountMenuButton();
        }
        homePage.getMenu().clickMyTalksOrg();
    }

    @Step
    public boolean isPasswordFieldIsHighlighted() {
        return homePage.getMenu().isPasswordFieldHighlited();
    }

    @Step
    public List<AccountButtonDTO> accountMenuItems() {
        return homePage.getMenu().getAccountMenuItems();
    }

    public String getPasswordValidationMsg() {
        return homePage.getMenu().getPasswordErrorMsgTxt();
    }

    public boolean isLoginFieldIsHighlighted() {

        return homePage.getMenu().isLoginFieldHighlited();
    }

    public String getLoginValidationMsg() {
        return homePage.getMenu().getLoginErrorMsgTxt();
    }

    /**
     * update for Sign-out
     */

    public boolean checkPositionOfSignOut() {
        homePage.waitForPageToLoad();

        if (!homePage.getMenu().isAccountMenuUnfolded()) {
            homePage.getMenu().clickAccountMenuButton();
        }
        return homePage.getMenu().checkSignOutBtnIsLastItem();
    }

    @Step
    public void clickSettingsOption() {
        homePage.waitForPageToLoad();
        homePage.getMenu().clickAccountMenuButton();
        homePage.getMenu().clickSettingsOption();
    }

}
