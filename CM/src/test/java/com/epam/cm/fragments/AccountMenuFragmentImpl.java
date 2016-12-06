package com.epam.cm.fragments;

import com.epam.cm.dto.AccountButtonDTO;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.core.pages.WidgetObjectImpl;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static java.util.concurrent.TimeUnit.SECONDS;

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

    @FindBy(xpath ="//*[@class='menu-list']")
    private WebElementFacade menuRootElement;

    @FindBy(xpath = "//*[@class='menu-list']/li[2]/a")
    private WebElementFacade myTalksBtn;

    @FindBy(xpath = "//*[@class='menu-list__title ng-binding'] [@href='#/my-info']")
    private WebElementFacade myInfoBtn;

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

    public void clickMyTalksBtn(){
        myTalksBtn.click();
    }

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
    public List<AccountButtonDTO> getAccountMenuItems() {
        List<WebElementFacade> resElem = menuRootElement.thenFindAll(By.xpath("./li/a"));

        return resElem.stream()
                .map(s -> new AccountButtonDTO(s.getText(),s.getAttribute("href"))).
                        collect(Collectors.toList());


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
        signInBtn.withTimeoutOf(5,SECONDS).waitUntilClickable().click();
    }

    public boolean checkSignOutBtnIsLastItem(){

        if(signOutBtn==findBy(By.xpath("(//*[@class='menu-list']//li[contains(@class,'menu-list__item')])[last()]/a"))){
            return true;
        }
        return false;

    }

    public void clickSignOutButton() {
        signOutBtn.withTimeoutOf(5,SECONDS).waitUntilVisible().click();
    }

    public void setLoginField(String login) {
        emailField.withTimeoutOf(5,SECONDS).waitUntilVisible().clear();
        emailField.type(login);
    }

    public void setPasswordField(String password) {
        passwordField.withTimeoutOf(5,SECONDS).waitUntilVisible().clear();
        passwordField.type(password);
    }

    public void setEmailForgotPwFieldField(String email){
        emailForgotPwField.clear();
        emailForgotPwField.type(email);
    }

    public String getAccountMenuTitle() {
        accountBtn.waitUntilVisible();
        return accountBtn.getText();
    }

    public void clickAccountMenuButton() {
        accountBtn.withTimeoutOf(5, SECONDS).waitUntilClickable().click();
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

    public void clickMyInfoButton() {
        myInfoBtn.withTimeoutOf(5, SECONDS).waitUntilClickable().click();
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
