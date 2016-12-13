package com.epam.cm.pages;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.WebDriver;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by Lev_Serba on 12/12/2016.
 */
public class SettingsPage extends AnyPage {

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


    public SettingsPage(WebDriver driver) {
        super(driver);
    }

    public void clickEditLinkNextToEmail() {
        emailEditLink.withTimeoutOf(5, SECONDS).waitUntilClickable().click();
    }

    public boolean isCurrentEmailFieldVisible() {
        waitFor(currentEmailInput);
        if(currentEmailLabel.getText().equalsIgnoreCase("Current Email") &&
            currentEmailInput.isCurrentlyVisible()){
            return true;
        }
        return false;
    }

    public boolean isNewEmailFieldVisible() {
        waitFor(newEmailInput);
        if(newEmailLabel.getText().equalsIgnoreCase("New Email") &&
            newEmailInput.isCurrentlyVisible()){
            return true;
        }
        return false;
    }
}
