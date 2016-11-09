package com.epam.cm.pages;

import com.epam.cm.fragments.AccountMenuFragment;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.DefaultUrl;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.util.Collection;

/**
 *
 */

@DefaultUrl("http://localhost:5000/#/")
public class HomePage extends PageObject {


  //  @FindBy(xpath = "//*[@class='menu-container']/button")
 //   AccountMenuFragment accountMenu;

    @FindBy(xpath = "(//*[@class='menu-list'] | //sign-in)")
    WebElementFacade flag;

    public HomePage(final WebDriver driver) { super(driver); }

//    public AccountMenuFragment getAccountMenu(){
//        return accountMenu;
//    }

    public boolean isAccountMenuUnfolded(){

        if(flag.isCurrentlyVisible()) return true;
        return false;
    }

    public boolean isSignOutBtnExist(){
        return findAll("//*[@class='menu-list']//li[contains(@class,'sign-out')]").size()>0;
    }

    @FindBy(xpath = "//*[@class='menu-container']/button")
    private WebElementFacade accountBtn;

    @FindBy(xpath = "//input[@id='sign-in-email']")
    private WebElementFacade emailField;


    @FindBy(xpath = "//input[@id='sign-in-password']")
    private WebElementFacade passwordField;

    @FindBy(xpath = ".//*[@id='sign-in-password'][contains(@class,'ng-invalid-password_auth_err')]")
    private WebElementFacade highlitedPasswordField;


    @FindBy(xpath = "//sign-in//input[@class='btn sign-in__submit']")
    private WebElementFacade signInBtn;

    @FindBy(xpath = "//*[@class='menu-list']//li[contains(@class,'sign-out')]")
    private WebElementFacade signOutBtn;

//    @FindBy(xpath = "")
//    private WebElementFacade passwordErrorMsg;


    public String getAccountMenuTitle() {
        return accountBtn.getText();
    }


    public WebElementFacade getSignInButton() {
        return signInBtn;
    }


    public WebElementFacade getSignOutButton() {
        return signOutBtn;
    }


    public WebElementFacade getLoginField() {
        return emailField;
    }


    public WebElementFacade getPasswordField() {
        return passwordField;
    }


    public WebElementFacade getAccountMenuButton() {
        return accountBtn;
    }

    public boolean isPasswordFieldHighlited(){

        if(highlitedPasswordField == null) return false;
        return true;
    }
    public String  getPasswordErrorMsgTxt(){

        //return passwordField.findBy(By.xpath("/following-sibling::span))").getText());

        return find(By.xpath("//*[@id='sign-in-password']/following-sibling::span")).getText();
    }
}


