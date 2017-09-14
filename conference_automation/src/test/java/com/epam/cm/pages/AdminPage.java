package com.epam.cm.pages;

/**
 * Created by Serhii_Kobzar on 1/26/2017.
 */

import com.epam.cm.dto.AdminPageDTO;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AdminPage extends AnyPage{

    public static final String SUCCESSFULLY_REGISTERED = "You've successfully registered.";
    public static final String EMAIL_FIELD = "emailField";
    public static final String WRONG_TEXT_MSG = "wrong text msg";
    public static final String PASSWORD_FIELDS = "passFields";
    public static final String FIRST_PASSWORD_FIELD = "passField";
    public static final String SECOND_PASSWORD_FIELD = "passField2";
    public static final String CONFIRM_PASSWORD_FIELD = "confPassField";
    public static final String EMPTY_FIELD = "";
    public static final String ALL_FIELDS = "allFields";
    public static final String NAME_FIELD = "nameField";
    public static final String LAST_NAME_FIELD = "lastNameField";
    private final String USERNAME = "//*[@class='data-table__column data-table__column_name ng-binding'][contains(.,'%s')]";

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
    @FindBy(xpath = "//*[@name='role-name']")
    WebElementFacade roleSelect;
    @FindBy(xpath = "//*[@name='fname']")
    WebElementFacade firstNameInput;
    @FindBy(xpath = "//*[@name='lname']")
    WebElementFacade lastNameInput;
    @FindBy(xpath = "//*[@name='mail']")
    WebElementFacade emailInput;
    @FindBy(xpath = "//*[@name='password']")
    WebElementFacade passwordInput;
    @FindBy(xpath = "//*[@name='confirm']")
    WebElementFacade confirmPasswordInput;
    @FindBy(xpath = "//*[@value='save']")
    WebElementFacade saveNewUserBtn;

    /**
     * Error messages
     */

    @FindBy(xpath = "//*[@name='role-name']/")
    WebElementFacade roleSelectEmptyError;
    @FindBy(xpath = "//*[@name='fname']/following-sibling::span[1]")
    WebElementFacade firstNameInputEmptyError;
    @FindBy(xpath = "//*[@name='lname']/following-sibling::span[1]")
    WebElementFacade lastNameInputEmptyError;
    @FindBy(xpath = "//*[@name='mail']/following-sibling::span[1]")
    WebElementFacade emailInputEmptyError;
    @FindBy(xpath = "//*[@name='password']/following-sibling::span[1]")
    WebElementFacade passwordInputEmptyError;
    @FindBy(xpath = "//*[@name='confirm']/following-sibling::span[1]")
    WebElementFacade confirmPasswordInputEmptyError;


    @FindBy(xpath = "//*[@name='mail']/following-sibling::span[2]")
    WebElementFacade emailInputError;
    @FindBy(xpath = "//*[@name='password']/following-sibling::span[2]")
    WebElementFacade passwordInputError;
    @FindBy(xpath = "//*[@name='password']/following-sibling::span[3]")
    WebElementFacade passwordInputSecondError;
    @FindBy(xpath = "//*[@name='confirm']/following-sibling::span[4]")
    WebElementFacade confirmPasswordInputError;

    public AdminPage(WebDriver driver) {
        super(driver);
    }

    public void clickAddNewUserBtn() {
        addNewUserBtn.click();
    }

    public void fillNewUserInfo(AdminPageDTO admin){
        roleSelect.selectByIndex(Integer.parseInt(admin.getRole()));
        firstNameInput.clear();
        firstNameInput.type(admin.getFirstName());
        lastNameInput.clear();
        lastNameInput.type(admin.getLastName());
        emailInput.clear();
        emailInput.type(admin.getEmail());
        passwordInput.clear();
        passwordInput.type(admin.getPassword());
        confirmPasswordInput.clear();
        confirmPasswordInput.type(admin.getConfirmPassword());
    }
    public String getHighlightedTextIncorrectFields(String incorrectFieldsTemplate, String firstExpectedText,
                                                    String secondExpectedText) {
        return getTextFromIncorrectFields(incorrectFieldsTemplate, firstExpectedText, secondExpectedText);
    }

    public String getTextFromIncorrectFields(String incorrectFieldsTemplate, String firstExpectedText,
                                              String secondExpectedText) {
        if (incorrectFieldsTemplate.equals(EMAIL_FIELD)) {
            if (emailInputError.getText().equals(firstExpectedText)) {
                return firstExpectedText + secondExpectedText;
            } else {
                return WRONG_TEXT_MSG;
            }
        } else if (incorrectFieldsTemplate.equals(PASSWORD_FIELDS)) {
            if (passwordInputError.getText().equals(firstExpectedText)
                    && passwordInputSecondError.getText().equals(secondExpectedText)) {
                return firstExpectedText + secondExpectedText;
            } else {
                return WRONG_TEXT_MSG;
            }
        } else if (incorrectFieldsTemplate.equals(FIRST_PASSWORD_FIELD)) {
            if (passwordInputSecondError.getText().equals(firstExpectedText)) {
                return firstExpectedText + secondExpectedText;
            } else {
                return WRONG_TEXT_MSG;
            }
        } else if (incorrectFieldsTemplate.equals(SECOND_PASSWORD_FIELD)) {
            if (passwordInputError.getText().equals(firstExpectedText)) {
                return firstExpectedText + secondExpectedText;
            } else {
                return WRONG_TEXT_MSG;
            }
        } else if (incorrectFieldsTemplate.equals(CONFIRM_PASSWORD_FIELD)) {
            if (confirmPasswordInputError.getText().equals(firstExpectedText)) {
                return firstExpectedText + secondExpectedText;
            } else {
                return WRONG_TEXT_MSG;
            }
        }
        return EMPTY_FIELD;
    }

    public String getTextFromEmptyFields(String emptyFieldsTemplate, String expectedText) {

        if (emptyFieldsTemplate.equals(ALL_FIELDS)) {
            if (roleSelectEmptyError.getText().equals(expectedText)
                    && firstNameInputEmptyError.getText().equals(expectedText)
                    && lastNameInputEmptyError.getText().equals(expectedText)
                    && emailInputEmptyError.getText().equals(expectedText)
                    && passwordInputEmptyError.getText().equals(expectedText)
                    && confirmPasswordInputEmptyError.getText().equals(expectedText)) {
                return expectedText;
            } else {
                return WRONG_TEXT_MSG;
            }
        }
        return WRONG_TEXT_MSG;
    }

    public String checkUserInTheGridByName(String name){
        return find(By.xpath(String.format(USERNAME, name))).getText();
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
