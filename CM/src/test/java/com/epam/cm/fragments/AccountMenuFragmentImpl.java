package com.epam.cm.fragments;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.core.pages.WidgetObjectImpl;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.util.concurrent.TimeUnit;

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

    @FindBy(xpath = "//*[@class='menu-list']//li[contains(@class,'sign-out')]/a | //a[@class='menu-list__title']")
    private WebElementFacade signOutBtn;

    // forgotPw

    @FindBy(xpath = "//*[@class='sign-in__password-cont']/a")
    private WebElementFacade forgotPasswordLink;

    @FindBy(xpath = "//*[@class='pop-up-wrapper']/div")
    private WebElementFacade popUp;

    @FindBy(xpath = "//*[@class='pop-up__notification']")
    private WebElementFacade forgotLbl;

    @FindBy(xpath = "//*[@class='pop-up-button-wrapper']/input[2]")
    private WebElementFacade cancelBtn;

    @FindBy(xpath = "//*[@class='pop-up-button-wrapper']/input[1]")
    private WebElementFacade continueBtn;

    @FindBy(xpath = "//*[@class='field-error error-title_pop-up']")
    private WebElementFacade emptyEmailMsg;

    @FindBy(xpath = "//forgot-password//form/input")
    private WebElementFacade emailForgotPwField;

    @FindBy(xpath = "//forgot-password/div/div/p")
    private WebElementFacade notificationPopUp;

    @FindBy(xpath = "//forgot-password/div/div/button")
    private WebElementFacade cancelNotifiPopUpBtn;

    @FindBy(xpath = "//forgot-password//form/span[2]")
    private WebElementFacade invalidEmailMsg;

    public void clickCancelBtn(){
        cancelNotifiPopUpBtn.click();
    }

    public void clickContinueButton(){
        continueBtn.click();
    }

    public void clickForgotPwLink() {

        forgotPasswordLink.click();
    }

    public boolean cancelNotifiPopUpBtnisPresent(){
        if(cancelNotifiPopUpBtn.isCurrentlyVisible())
            return true;
        return false;
    }

    public boolean popUpISPresent() {
        if (popUp.isCurrentlyVisible()) {
            return true;

        }
        return false;
    }

    public boolean forgotLblIsPresent() {
        if (forgotLbl.isCurrentlyVisible()) {
            return true;
        }
        return false;
    }

    public String forgotLblText() {
        return forgotLbl.getText();
    }

    @Override
    public String getEmtyEmailMsgTxt() {
        return emptyEmailMsg.getText();
    }

    public boolean cancelBtnIsPresent() {
        if (cancelBtn.isCurrentlyVisible()) {
            return true;
        }
        return false;
    }

    public boolean continiuBtnIsPresent() {
        if (continueBtn.isCurrentlyVisible()) {
            return true;
        }
        return false;
    }

    public boolean isAccountMenuUnfolded() {

        if (flag.isCurrentlyVisible()) return true;
        return false;
    }


    public boolean isSignOutBtnExist() {
        return signOutBtn.isPresent();
    }

    public void clickSignInButton() {
        signInBtn.click();
    }


    public void clickSignOutButton() {
        signOutBtn.click();
    }

    public void setLoginField(String login) {
        emailField.clear();
        emailField.type(login);
    }

    public void setPasswordField(String password) {
        passwordField.clear();
        passwordField.type(password);
    }

    public void setEmailForgotPwFieldField(String email){
        emailForgotPwField.clear();
        emailForgotPwField.type(email);
    }

    public String getAccountMenuTitle() {

        return accountBtn.getText();
    }

    public void clickAccountMenuButton() {
        accountBtn.click();
    }

    public boolean isEmailForgotPwHighlighted(){
        return emailForgotPwField.getAttribute("class").contains("invalid");
    }

    public boolean isLoginFieldHighlited() {

        return emailField.getAttribute("class").contains("invalid");

    }

    public boolean isPasswordFieldHighlited() {

        return passwordField.getAttribute("class").contains("invalid");
    }

    public String getPasswordErrorMsgTxt() {

        return find(By.xpath("//*[@id='sign-in-password']/following-sibling::span[1]")).getText();
    }

    public String getPopUpNotification(){
        return notificationPopUp.getText();
    }

    public String getInvalidEmailMsg(){
        return invalidEmailMsg.getText();
    }

    public String getLoginErrorMsgTxt() {
        return find(By.xpath("//*[@id='sign-in-email']/following-sibling::span[1]")).getText();
    }

}
