package com.epam.cm.steps.serenity;

import com.epam.cm.dto.CredentialsDTO;
import com.epam.cm.fragments.AccountMenuFragment;
import com.epam.cm.pages.HomePage;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.pages.Pages;
import net.thucydides.core.steps.ScenarioSteps;

/**
 *
 */
public class LoginPageSteps extends ScenarioSteps {

    public LoginPageSteps(final Pages pages) {
        super(pages);
    }

    HomePage homePage;


    @Step
    public void gotoTheHomePage() {
        getPages().get(HomePage.class).open();
    }

    @Step
    public void clickOnAccountMenu() {
        homePage.getAccountMenuButton().click();
    }

    @Step
    public boolean isAccountMenuUnfolded() {
        return  homePage.isAccountMenuUnfolded();
    }

    @Step
    public void typeLoginAndPassword(CredentialsDTO user) {
        homePage.getLoginField().type(user.getEmail());
        homePage.getPasswordField().type(user.getPassword());
    }

    @Step
    public void clickSignInButton() {
        homePage.getSignInButton().click();
    }

    @Step
    public String getAccountMenuTitle() {
        return  homePage.getAccountMenuTitle();
    }

    @Step
    public void logout() {
        if(!homePage.isAccountMenuUnfolded()){
            homePage.getAccountMenuButton().click();
        }
        homePage.getSignOutButton().click();
    }

    @Step
    public boolean isLoggedIn() {
        if(!homePage.isAccountMenuUnfolded()){
            homePage.getAccountMenuButton().click();
        }
        if(homePage.isSignOutBtnExist()) return true;
        return false;
    }


    @Step
    public boolean isHomePageOpened() {

        return pages().isCurrentPageAt(HomePage.class);
    }

    @Step
    public boolean isSignInFormOpened() {

        if(homePage.isAccountMenuUnfolded() && !homePage.isSignOutBtnExist()) return true;
        return false;
    }

    @Step
    public boolean isPasswordFieldIsHighlited() {
         return homePage.isPasswordFieldHighlited();
    }

    public String getPasswordValidationMsg() {
        return homePage.getPasswordErrorMsgTxt();
    }
}
