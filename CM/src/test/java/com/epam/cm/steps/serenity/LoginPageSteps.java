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

        homePage.getMenu().clickAccountMenuButton();
    }

    @Step
    public boolean isAccountMenuUnfolded() {
        return  homePage.getMenu().isAccountMenuUnfolded();
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
        return  homePage.getMenu().getAccountMenuTitle();
    }

    @Step
    public void logout() {
        if(!homePage.getMenu().isAccountMenuUnfolded()){
            homePage.getMenu().clickAccountMenuButton();
        }
        homePage.getMenu().clickSignOutButton(); ;
    }

    @Step
    public boolean isLoggedIn() {
        if(!homePage.getMenu().isAccountMenuUnfolded()){
            homePage.getMenu().clickAccountMenuButton();
        }
        if(homePage.getMenu().isSignOutBtnExist()) return true;
        return false;
    }


    @Step
    public boolean isHomePageOpened() {

        return pages().isCurrentPageAt(HomePage.class);
    }

    @Step
    public boolean isSignInFormOpened() {

        if(homePage.getMenu().isAccountMenuUnfolded() && !homePage.getMenu().isSignOutBtnExist()) return true;
        return false;
    }

    @Step
    public boolean isPasswordFieldIsHighlited() {
         return homePage.getMenu().isPasswordFieldHighlited();
    }

    public String getPasswordValidationMsg() {
        return homePage.getMenu().getPasswordErrorMsgTxt();
    }
}
