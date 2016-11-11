package com.epam.cm.steps.serenity;

import com.epam.cm.core.utils.WebDriverSupport;
import com.epam.cm.pages.HomePage;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.pages.Pages;
import net.thucydides.core.steps.ScenarioSteps;

/**
 * Created by Serhii_Kobzar on 11/10/2016.
 */
public class ForgotPaswordPageSteps extends ScenarioSteps{

    public ForgotPaswordPageSteps(final Pages pages){ super(pages);}

    HomePage homePage;

    @Step
    public void gotoTheHomePage() {

        getPages().get(HomePage.class).open();
        WebDriverSupport.clearLocalStorage();
        WebDriverSupport.reloadPage();
    }

    @Step
    public void clickOnAccountMenu(){
        homePage.getMenu().clickAccountMenuButton();
    }

    @Step
    public void clickForgotPwLink(){
        homePage.getMenu().clickForgotPwLink();
    }

    @Step
    public void checkPopUpIsPresent(){
        homePage.getMenu().popUpISPresent();
    }

    @Step
    public void checkForgotPwLinkIsPresent(){
        homePage.getMenu().forgotLblIsPresent();
    }

    @Step
    public String getForgotPwLblText(){
        return  homePage.getMenu().forgotLblText();
    }

    @Step
    public void checkContinueBtn(){
        homePage.getMenu().continiuBtnIsPresent();
    }

    @Step
    public void checkCancelBtn(){
        homePage.getMenu().cancelBtnIsPresent();
    }
}
