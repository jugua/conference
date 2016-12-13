package com.epam.cm.pages;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.WebDriver;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by Lev_Serba on 12/12/2016.
 */
public class SettingsPage extends AnyPage {

    //email

    @FindBy(xpath = "//*[@class='settings__block']/div[2]")
    private WebElementFacade emailEditLink;
    @FindBy(xpath = "//*[@class='edit-email__fields-wrapper']/label[1]")
    private WebElementFacade currentEmailLabel;
    @FindBy(xpath = "//*[@class='edit-email__fields-wrapper']/input[@name='currentEmail']")
    private WebElementFacade currentEmailInput;
    @FindBy(xpath = "//*[@class='edit-email__fields-wrapper']/label[2]")
    private WebElementFacade newEmailLabel;
    @FindBy(xpath = "//*[@class='edit-email__fields-wrapper']/input[@name='newEmail']")
    private WebElementFacade newEmailInput;

    //name
    @FindBy(xpath = "//*[@class='settings__block']/div[1]")
    private WebElementFacade nameEditLink;
    @FindBy(xpath = "//*[@class='edit-password__fields-wrapper']/input[@name='fname']")
    private WebElementFacade firstNameInput;
    @FindBy(xpath = "//*[@class='edit-password__fields-wrapper']/input[@name='lname']")
    private WebElementFacade lastNameInput;
    @FindBy(xpath = "//*[@class='edit-password__fields-wrapper']/label[1]")
    private WebElementFacade firstLbl;
    @FindBy(xpath = "//*[@class='edit-password__fields-wrapper']/label[2]")
    private WebElementFacade secondLbl;
    @FindBy(xpath = "//*[@class='edit-password__buttons-wrapper']/input[1]")
    private WebElementFacade saveBtn;
    @FindBy(xpath = "//*[@class='edit-password__buttons-wrapper']/input[2]")
    private WebElementFacade cancelBtn;
    @FindBy(xpath = "//*[@class='edit-password']/span")
    private WebElementFacade namesErrorMsg;

    //email

    public SettingsPage(WebDriver driver) {
        super(driver);
    }

    public void clickEditLinkNextToEmail() {
        emailEditLink.withTimeoutOf(5, SECONDS).waitUntilClickable().click();
    }

    public boolean isCurrentEmailFieldVisible() {
        waitFor(currentEmailInput);
        if (currentEmailLabel.getText().equalsIgnoreCase("Current Email") &&
                currentEmailInput.isCurrentlyVisible()) {
            return true;
        }
        return false;
    }

    public boolean isNewEmailFieldVisible() {
        waitFor(newEmailInput);
        if (newEmailLabel.getText().equalsIgnoreCase("New Email") &&
                newEmailInput.isCurrentlyVisible()) {
            return true;
        }
        return false;
    }

    //name

    public void clickEditLinkNextToName() {
        nameEditLink.click();
    }

    public boolean checkSaveBtn() {
        waitFor(firstNameInput);
        if (saveBtn.isVisible())
            return true;
        return false;
    }

    public boolean checkCancelBtn() {
        waitFor(lastNameInput);
        if (cancelBtn.isVisible())
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

    public void clickNameSaveBtn(){
        saveBtn.click();
    }

    public boolean isLastNameInputHighlighted(){
        //return passwordField.getAttribute("class").contains("invalid");
        return lastNameInput.getAttribute("class").contains("invalid");
    }

    public String getLastNameErrorMsg(){
        return namesErrorMsg.getText();
    }

}
