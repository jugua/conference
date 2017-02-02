package com.epam.cm.pages;

/**
 * Created by Serhii_Kobzar on 1/26/2017.
 */

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.WebDriver;

public class AdminPage extends AnyPage{

    /***
     * organiser accesses admin page
     */
    @FindBy(xpath = "//*[@class='btn my-talks__button']")
    WebElementFacade addNewUserBtn;
    @FindBy(xpath = "//div[@class = 'table-header__item table-header__item_role' and contains(.,'role')]")
    WebElementFacade roleColumn;
    @FindBy(xpath = "//div[@class = 'table-header__item table-header__item_name' and contains(.,'name')]")
    WebElementFacade nameColumn;
    @FindBy(xpath = "//div[@class = 'table-header__item table-header__item_email' and contains(.,'email')]")
    WebElementFacade emailColumn;
    /**
     * organiser adds new user
     */
    @FindBy(xpath = "")
    WebElementFacade roleSelect;
    @FindBy(xpath = "")
    WebElementFacade firstNameInput;
    @FindBy(xpath = "")
    WebElementFacade lastNameInput;
    @FindBy(xpath = "")
    WebElementFacade emailInput;
    @FindBy(xpath = "")
    WebElementFacade passwordInput;
    @FindBy(xpath = "")
    WebElementFacade confirmPasswordInput;
    @FindBy(xpath = "")
    WebElementFacade saveNewUserBtn;

    public AdminPage(WebDriver driver) {
        super(driver);
    }

    public void clickAddNewUserBtn() {
        addNewUserBtn.click();
    }

    public boolean addNewUserBtnIsVisible() {
        if (addNewUserBtn.isCurrentlyVisible())
            return true;
        else
            return false;
    }

    public boolean isRoleColumnDisplayed() {
        if (roleColumn.isCurrentlyVisible())
            return true;
        else
            return false;
    }

    public boolean isNameColumnDisplayed() {
        if (nameColumn.isCurrentlyVisible())
            return true;
        else
            return false;
    }

    public boolean isEmailColumnDisplayed() {
        if (emailColumn.isCurrentlyVisible())
            return true;
        else
            return false;
    }

    public void selectRole(String role) {
        roleSelect.selectByIndex(Integer.parseInt(role));
    }

    public void setFirstName(String firstName) {
        firstNameInput.clear();
        firstNameInput.type(firstName);
    }

    public void setLastName(String lastName) {
        lastNameInput.clear();
        lastNameInput.type(lastName);
    }

    public void setAddNewUserEmail(String email) {
        emailInput.clear();
        emailInput.type(email);
    }

    public void setAddNewUserPassword(String password) {
        passwordInput.clear();
        passwordInput.type(password);
    }

    public void setAddNewUserConfirmPassword(String confirmPassword) {
        confirmPasswordInput.clear();
        confirmPasswordInput.type(confirmPassword);
    }

    public void clickSaveNewUserBtn() {
        saveNewUserBtn.click();
    }

}
