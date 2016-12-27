package com.epam.cm.pages;

import com.epam.cm.dto.UserRegistrationInfoDTO;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.DefaultUrl;

import org.openqa.selenium.WebDriver;

import static java.util.concurrent.TimeUnit.SECONDS;

@DefaultUrl("/#/sign-up")
public class SignUpPage extends AnyPage {

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
    public static final String EMAIL_FIELD1 = "emailField";

    @FindBy(xpath = "//*[@id='name']")
    WebElementFacade inputName;
    @FindBy(xpath = "//*[@id='surname']")
    WebElementFacade inputLastName;
    @FindBy(xpath = "//*[@id='mail']")
    WebElementFacade inputEmail;
    @FindBy(xpath = "//*[@id='password']")
    WebElementFacade inputPassword;
    @FindBy(xpath = "//*[@id='confirm']")
    WebElementFacade inputConfirmPassword;
    @FindBy(xpath = "//*[@class='sign-up__button btn']")
    WebElementFacade submitBtn;
    @FindBy(xpath = "//*[@class='pop-up__title']")
    WebElementFacade successRegistrationPopUp;
    @FindBy(xpath = "//*[@class='btn pop-up__button']")
    WebElementFacade successRegistrationBtn;

    @FindBy(xpath = "//input[@id='name']/following-sibling::span[1]")
    WebElementFacade userNameHighlightedText;
    @FindBy(xpath = "//input[@id='surname']/following-sibling::span[1]")
    WebElementFacade userLastNameHighlightedText;
    @FindBy(xpath = "//input[@id='mail']/following-sibling::span[1]")
    WebElementFacade userEmailHighlightedText;
    @FindBy(xpath = "//input[@id='password']/following-sibling::span[1]")
    WebElementFacade userPasswordHighlightedText;
    @FindBy(xpath = "//input[@id='confirm']/following-sibling::span[1]")
    WebElementFacade userConfPasswordHighlightedText;

    @FindBy(xpath = "//input[@id='mail']/following-sibling::span[2]")
    WebElementFacade userEmailHighlightedSecondSpan;
    @FindBy(xpath = "//input[@id='password']/following-sibling::span[2]")
    WebElementFacade userPasswordHighlightedSecondSpan;
    @FindBy(xpath = "//input[@id='password']/following-sibling::span[3]")
    WebElementFacade userPasswordHighlightedThirdSpan;
    @FindBy(xpath = "//input[@id='confirm']/following-sibling::span[2]")
    WebElementFacade userConfPasswordHighlightedSecondSpan;
    @FindBy(xpath = "//input[@id='confirm']/following-sibling::span[4]")
    WebElementFacade userConfPasswordHighlightedFourthSpan;

    @FindBy(xpath = "//input[@id='name']")
    WebElementFacade userNameFild;
    @FindBy(xpath = "//input[@id='surname']")
    WebElementFacade userLastNameField;
    @FindBy(xpath = "//input[@id='mail']")
    WebElementFacade userEmailField;
    @FindBy(xpath = "//input[@id='password']")
    WebElementFacade userPasswordField;
    @FindBy(xpath = "//input[@id='confirm']")
    WebElementFacade userConfPasswordField;

    public SignUpPage(final WebDriver driver) {
        super(driver);
    }

    public void fillRegistrationInfo(UserRegistrationInfoDTO user) {
        inputName.type(user.getFirstName());
        inputLastName.type(user.getLastName());
        inputEmail.type(user.getEmail());
        inputPassword.type(user.getPassword());
        inputConfirmPassword.type(user.getPassword());
    }

    public void fillRegistrationInfoNegativeTest(UserRegistrationInfoDTO user) {
        inputName.type(user.getFirstName());
        inputLastName.type(user.getLastName());
        inputEmail.type(user.getEmail());
        inputPassword.type(user.getPassword());
        inputConfirmPassword.type(user.getConfirmPasswordNegativeTest());
    }

    public void clickSubmit() {
        submitBtn.click();
    }

    public boolean isRegistered() {

        try {
            successRegistrationPopUp.withTimeoutOf(3, SECONDS).waitUntilVisible();
        } catch (Exception ex) {
            return false;
        }
        if (((successRegistrationPopUp.getText()).equalsIgnoreCase(SUCCESSFULLY_REGISTERED))
                && successRegistrationBtn.isEnabled()) {
            successRegistrationBtn.click();
            return true;
        } else {
            return false;
        }
    }

    public String getHighlightedTextEmptyFields(String emptyFieldsTemplate, String expectedText) {
        return getTextFromEmptyFields(emptyFieldsTemplate, expectedText);
    }

    public String getHighlightedTextIncorrectFields(String incorrectFieldsTemplate, String firstExpectedText,
            String secondExpectedText) {
        return getTextFromIncorrectFields(incorrectFieldsTemplate, firstExpectedText, secondExpectedText);
    }

    private String getTextFromIncorrectFields(String incorrectFieldsTemplate, String firstExpectedText,
            String secondExpectedText) {
        if (incorrectFieldsTemplate.equals(EMAIL_FIELD)) {
            if (userEmailHighlightedSecondSpan.getText().equals(firstExpectedText)) {
                return firstExpectedText + secondExpectedText;
            } else {
                return WRONG_TEXT_MSG;
            }
        } else if (incorrectFieldsTemplate.equals(PASSWORD_FIELDS)) {
            if (userPasswordHighlightedSecondSpan.getText().equals(firstExpectedText)
                    && userPasswordHighlightedThirdSpan.getText().equals(secondExpectedText)) {
                return firstExpectedText + secondExpectedText;
            } else {
                return WRONG_TEXT_MSG;
            }
        } else if (incorrectFieldsTemplate.equals(FIRST_PASSWORD_FIELD)) {
            if (userPasswordHighlightedThirdSpan.getText().equals(firstExpectedText)) {
                return firstExpectedText + secondExpectedText;
            } else {
                return WRONG_TEXT_MSG;
            }
        } else if (incorrectFieldsTemplate.equals(SECOND_PASSWORD_FIELD)) {
            if (userPasswordHighlightedSecondSpan.getText().equals(firstExpectedText)) {
                return firstExpectedText + secondExpectedText;
            } else {
                return WRONG_TEXT_MSG;
            }
        } else if (incorrectFieldsTemplate.equals(CONFIRM_PASSWORD_FIELD)) {
            if (userConfPasswordHighlightedFourthSpan.getText().equals(firstExpectedText)) {
                return firstExpectedText + secondExpectedText;
            } else {
                return WRONG_TEXT_MSG;
            }
        }
        return EMPTY_FIELD;
    }

    public String getTextFromEmptyFields(String emptyFieldsTemplate, String expectedText) {

        if (emptyFieldsTemplate.equals(ALL_FIELDS)) {
            if (userNameHighlightedText.getText().equals(expectedText)
                    && userLastNameHighlightedText.getText().equals(expectedText)
                    && userEmailHighlightedText.getText().equals(expectedText)
                    && userPasswordHighlightedText.getText().equals(expectedText)
                    && userConfPasswordHighlightedText.getText().equals(expectedText)) {
                return expectedText;
            } else {
                return WRONG_TEXT_MSG;
            }
        } else if (emptyFieldsTemplate.equals(NAME_FIELD)) {
            if (userNameHighlightedText.getText().equals(expectedText)) {
                return expectedText;
            } else {
                return WRONG_TEXT_MSG;
            }
        } else if (emptyFieldsTemplate.equals(LAST_NAME_FIELD)) {
            if (userLastNameHighlightedText.getText().equals(expectedText)) {
                return expectedText;
            } else {
                return WRONG_TEXT_MSG;
            }
        } else if (emptyFieldsTemplate.equals(EMAIL_FIELD)) {
            if (userEmailHighlightedText.getText().equals(expectedText)) {
                return expectedText;
            } else {
                return WRONG_TEXT_MSG;
            }
        } else if (emptyFieldsTemplate.equals(FIRST_PASSWORD_FIELD)) {
            if (userPasswordHighlightedText.getText().equals(expectedText)) {
                return expectedText;
            } else {
                return WRONG_TEXT_MSG;
            }
        } else if (emptyFieldsTemplate.equals(CONFIRM_PASSWORD_FIELD)) {
            if (userConfPasswordHighlightedText.getText().equals(expectedText)) {
                return expectedText;
            } else {
                return WRONG_TEXT_MSG;
            }
        }
        return WRONG_TEXT_MSG;
    }

    public int getNameFieldLength() {
        return userNameFild.getValue().length();
    }

    public int getLastNameFieldLength() {
        return userLastNameField.getValue().length();
    }

    public int getEmailFieldLength() {
        return userEmailField.getValue().length();
    }

    public int getPassFieldLength() {
        return userPasswordField.getValue().length();
    }

    public int getConfPassFieldLength() {
        return userConfPasswordField.getValue().length();
    }
}
