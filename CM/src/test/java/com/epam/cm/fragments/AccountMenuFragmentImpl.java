package com.epam.cm.fragments;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.core.pages.WidgetObjectImpl;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.pagefactory.ElementLocator;

/**
 *
 */

public class AccountMenuFragmentImpl extends WidgetObjectImpl implements AccountMenuFragment {

    public AccountMenuFragmentImpl(PageObject page, ElementLocator locator, WebElement webElement, long timeoutInMilliseconds) {
        super(page, locator, webElement, timeoutInMilliseconds);
    }

    public AccountMenuFragmentImpl(PageObject page, ElementLocator locator, long timeoutInMilliseconds) {
        super(page, locator, timeoutInMilliseconds);
    }


    @FindBy(xpath = "(//*[@class='menu-list'] | //sign-in)")
    WebElementFacade flag;

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


    public boolean isAccountMenuUnfolded(){

        if(flag.isCurrentlyVisible()) return true;
        return false;
    }

    public boolean isSignOutBtnExist(){
        boolean b = findElements(By.xpath("//*[@class='menu-list']//li[contains(@class,'sign-out')]")).size() > 0;
        return b;
    }

    public void clickSignInButton()
    {
        signInBtn.click();
    }


    public void clickSignOutButton() {
        signOutBtn.click();
    }

    public void setLoginField(String login) {
        emailField.clear();
        emailField.type(login);
    }

    public void  setPasswordField(String password) {
        passwordField.clear();
        passwordField.type(password);
    }



    public String getAccountMenuTitle() {
        return accountBtn.getText();
    }

    public void clickAccountMenuButton() {
         accountBtn.click();
    }


    public boolean isLoginFieldHighlited(){

        return emailField.getAttribute("class").contains("invalid");

    }
    public boolean isPasswordFieldHighlited(){

         return passwordField.getAttribute("class").contains("invalid");

//        if(highlitedPasswordField == null) return false;
//        return true;
    }
    public String  getPasswordErrorMsgTxt(){

        //return passwordField.findBy(By.xpath("/following-sibling::span))").getText());

        return find(By.xpath("//*[@id='sign-in-password']/following-sibling::span")).getText();
    }
}
