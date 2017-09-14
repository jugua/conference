package com.epam.cm.pages;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;

import org.openqa.selenium.WebDriver;

import static java.util.concurrent.TimeUnit.SECONDS;

public class SettingsPage extends AnyPage {

    // email

   // @FindBy(xpath = "//*[@class='settings__block']/div[2]")
    @FindBy(xpath = "//*[@class='settings__block']/div[2]/div/div[@class='settings__edit']")
    private WebElementFacade emailEditLink;
    @FindBy(xpath = "//*[@class='edit-email__fields-wrapper']/label[1]")
    private WebElementFacade currentEmailLabel;
    @FindBy(xpath = "//*[@class='edit-email__fields-wrapper']/input[@name='currentEmail']")
    private WebElementFacade currentEmailInput;
    @FindBy(xpath = "//*[@class='edit-email__fields-wrapper']/label[2]")
    private WebElementFacade newEmailLabel;
    @FindBy(xpath = "//*[@class='edit-email__fields-wrapper']/input[@name='newEmail']")
    private WebElementFacade newEmailInput;
    @FindBy(xpath = "//*[@class='edit-email__buttons-wrapper']/input[1]")
    private WebElementFacade emailSaveBtn;
    @FindBy(xpath = "//*[@class='edit-email__result edit-email__result_error ng-binding']")
    private WebElementFacade emailErrorMsg;
    @FindBy(xpath = "//*[@ng-click='$ctrl.closeEditEmail()']")
    private WebElementFacade emailCancelBtn;

    // name
    @FindBy(xpath = "//*[@class='settings__block']/div[1]/div/div[@class='settings__edit']")
    private WebElementFacade nameEditLink;
    @FindBy(xpath = "//*[@class='edit-password__fields-wrapper']/input[@name='fname']")
    private WebElementFacade firstNameInput;
    @FindBy(xpath = "//*[@class='edit-password__fields-wrapper']/input[@name='lname']")
    private WebElementFacade lastNameInput;
    @FindBy(xpath = "//*[@name='fname']/../descendant::label"/*"/*//*[@class='edit-password__fields-wrapper']/label[1]"*/)
    private WebElementFacade firstLbl;
    @FindBy(xpath = "//*[@class='edit-password__fields-wrapper']/label[2]")
    private WebElementFacade secondLbl;
    @FindBy(xpath = "//*[@class='edit-password__buttons-wrapper']/input[1]")
    private WebElementFacade nameSaveBtn;
    @FindBy(xpath = "//*[@class='edit-password__buttons-wrapper']/input[2]")
    private WebElementFacade cancelNameBtn;
    @FindBy(xpath = "//*[@class='edit-password']/span")
    private WebElementFacade namesErrorMsg;

    // password
    @FindBy(xpath = "//*[@class='settings__block']/div[3]/div/div[@class='settings__edit']")
    private WebElementFacade pwEditLink;
    @FindBy(xpath = "//*[@class='edit-password__fields-wrapper']/input[@name='currentPassword']")
    private WebElementFacade currentPwInput;
    @FindBy(xpath = "//*[@class='edit-password__fields-wrapper']/input[@name='newPassword']")
    private WebElementFacade newPwInput;
    @FindBy(xpath = "//*[@class='edit-password__fields-wrapper']/input[@name='confirmNewPassword']")
    private WebElementFacade confirmNewPwInput;
    @FindBy(xpath = "//*[@class='edit-password__buttons-wrapper']/input[1]")
    private WebElementFacade pwSaveBtn;
    @FindBy(xpath = "//*[@class='edit-password']/span")
    private WebElementFacade currentPwError;
    // email

    public SettingsPage(WebDriver driver) {
        super(driver);
    }

    public void clickEditLinkNextToEmail() {
        emailEditLink.withTimeoutOf(5, SECONDS).waitUntilClickable().click();
    }

    public boolean isCurrentEmailFieldVisible() {
        waitFor(currentEmailInput);
        if (currentEmailLabel.getText().equalsIgnoreCase("Current Email") && currentEmailInput.isCurrentlyVisible()) {
            return true;
        }
        return false;
    }

    public boolean isNewEmailFieldVisible() {
        waitFor(newEmailInput);
        if (newEmailLabel.getText().equalsIgnoreCase("New Email") && newEmailInput.isCurrentlyVisible()) {
            return true;
        }
        return false;
    }

    public void typeEmail(String email) {
        newEmailInput.withTimeoutOf(5, SECONDS).waitUntilVisible().clear();
        newEmailInput.type(email);
    }

    public void clickEmailSaveBtn() {
        waitABit(4000);
        emailSaveBtn.withTimeoutOf(5, SECONDS).waitUntilClickable().click();
        waitABit(4000);
    }

    public String getErrorMsg() {
        String errorMsg = emailErrorMsg.withTimeoutOf(5, SECONDS).waitUntilVisible().getText();
        return errorMsg;
    }

    // name

    public void clickEditLinkNextToName() {
        waitABit(2000);
        nameEditLink.click();
    }

    public void clickNamesCancelBtn() {
        cancelNameBtn.click();
    }

    public boolean checkSaveBtn() {
        waitFor(firstNameInput);
        if (nameSaveBtn.isVisible())
            return true;
        return false;
    }

    public boolean checkCancelBtn() {
        waitFor(lastNameInput);
        if (cancelNameBtn.isVisible())
            return true;
        return false;
    }

    public String getFirstLblText() {
        return firstLbl.getText();
    }

    public String getSecondLblText() {
        return secondLbl.getText();
    }

    public void setLastNameEmpty() {
        lastNameInput.clear();
    }

    public void setLastName(String lastName) {
        lastNameInput.clear();
        lastNameInput.type(lastName);
    }

    public String getLastNameInputText() {
        return lastNameInput.getValue();
    }

    public int getLastNameLength() {
        return lastNameInput.getValue().length();
    }

    public void setFirstNameEmpty() {
        firstNameInput.clear();
    }

    public void setFirstName(String firstName) {
        firstNameInput.clear();
        firstNameInput.type(firstName);
    }

    public String getFirstNameInputText() {
        return firstNameInput.getValue();
    }

    public int getFirstNameLength() {
        return firstNameInput.getValue().length();
    }

    public void clickNameSaveBtn() {
        nameSaveBtn.click();
    }

    public boolean isFirstNameInputHighlighted() {
        // return passwordField.getAttribute("class").contains("invalid");
        return firstNameInput.getAttribute("class").contains("invalid");
    }

    public boolean isLastNameInputHighlighted() {
        // return passwordField.getAttribute("class").contains("invalid");
        return lastNameInput.getAttribute("class").contains("invalid");
    }

    public String getNameErrorMsg() {
        return namesErrorMsg.getText();
    }

    public String getCurrentEmailText() {

        String currentEmailText = currentEmailInput.withTimeoutOf(5,SECONDS).waitUntilVisible().getValue();
        return currentEmailText;
    }

    public void clickEmailCancelBtn() {
        waitABit(4000);
        emailCancelBtn.withTimeoutOf(5, SECONDS).waitUntilClickable().click();
        waitABit(4000);
    }

    // password

    public void clickEditPwLink() {
        waitABit(2000);
        pwEditLink.click();
    }

    public void setCurrentPw(String currentPw) {
        currentPwInput.clear();
        currentPwInput.type(currentPw);
    }

    public void setNewPw(String newPw){
        newPwInput.clear();
        newPwInput.type(newPw);
    }

    public void setConfirmPw(String confirmPw){
        confirmNewPwInput.clear();
        confirmNewPwInput.type(confirmPw);
    }

    public void clickPwSaveBtn() {
        pwSaveBtn.click();
    }

    public String getCurrentPwErrorMsg(){
        return currentPwError.getText();
    }
}
