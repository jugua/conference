package com.epam.cm.pages;

import com.epam.cm.dto.UserRegistrationInfoDTO;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.exceptions.SerenityManagedException;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.DefaultUrl;
import org.openqa.selenium.WebDriver;


@DefaultUrl("/#/sign-up")
public class SignUpPage extends AnyPage {

    public static final String SUCCESSFULLY_REGISTERED = "You've successfully registered.";

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
            waitFor(successRegistrationPopUp);
        }
        catch(Exception ex){
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

    public String getHighlightedTextIncorrectFields(String incorrectFieldsTemplate, String firstExpectedText, String secondExpectedText) {
        return getTextFromIncorrectFields(incorrectFieldsTemplate, firstExpectedText, secondExpectedText);
    }

    private String getTextFromIncorrectFields(String incorrectFieldsTemplate, String firstExpectedText, String secondExpectedText) {
        if(incorrectFieldsTemplate.equals("emailField")) {
            if (userEmailHighlightedSecondSpan.getText().equals(firstExpectedText)) {
                return firstExpectedText+secondExpectedText;
            } else {
                return "wrong text msg";
            }
        } else if(incorrectFieldsTemplate.equals("passFields")) {
            if (userPasswordHighlightedSecondSpan.getText().equals(firstExpectedText) &&
                userPasswordHighlightedThirdSpan.getText().equals(secondExpectedText)) {
                return firstExpectedText+secondExpectedText;
            } else {
                return "wrong text msg";
            }
        } else if(incorrectFieldsTemplate.equals("passField")) {
            if (userPasswordHighlightedThirdSpan.getText().equals(firstExpectedText)) {
                return firstExpectedText+secondExpectedText;
            } else {
                return "wrong text msg";
            }
        } else if(incorrectFieldsTemplate.equals("passField2")) {
            if (userPasswordHighlightedSecondSpan.getText().equals(firstExpectedText)) {
                return firstExpectedText+secondExpectedText;
            } else {
                return "wrong text msg";
            }
        } else if(incorrectFieldsTemplate.equals("confPassField")) {
            if (userConfPasswordHighlightedFourthSpan.getText().equals(firstExpectedText)) {
                return firstExpectedText+secondExpectedText;
            } else {
                return "wrong text msg";
            }
        }
        return "";
    }


    public String getTextFromEmptyFields(String emptyFieldsTemplate, String expectedText) {

        if (emptyFieldsTemplate.equals("allFields")) {
            if (userNameHighlightedText.getText().equals(expectedText) &&
                    userLastNameHighlightedText.getText().equals(expectedText) &&
                    userEmailHighlightedText.getText().equals(expectedText) &&
                    userPasswordHighlightedText.getText().equals(expectedText) &&
                    userConfPasswordHighlightedText.getText().equals(expectedText)) {
                return expectedText;
            } else {
                return "wrong text msg";
            }
        }else if(emptyFieldsTemplate.equals("nameField")) {
            if (userNameHighlightedText.getText().equals(expectedText)) {
                return expectedText;
            } else {
                return "wrong text msg";
            }
        }else if(emptyFieldsTemplate.equals("lastNameField")) {
            if (userLastNameHighlightedText.getText().equals(expectedText)) {
                return expectedText;
            } else {
                return "wrong text msg";
            }
        }else if(emptyFieldsTemplate.equals("emailField")) {
            if (userEmailHighlightedText.getText().equals(expectedText)) {
                return expectedText;
            } else {
                return "wrong text msg";
            }
        }else if(emptyFieldsTemplate.equals("passField")) {
            if (userPasswordHighlightedText.getText().equals(expectedText)) {
                return expectedText;
            } else {
                return "wrong text msg";
            }
        }else if(emptyFieldsTemplate.equals("confPassField")) {
            if (userConfPasswordHighlightedText.getText().equals(expectedText)) {
                return expectedText;
            } else {
                return "wrong text msg";
            }
        }
        return "wrong text msg";
    }

    public int getNameFieldLength() {
        int realLength = userNameFild.getValue().length();
        return realLength;
    }
    public int getLastNameFieldLength() {
        int realLength = userLastNameField.getValue().length();
        return realLength;
    }
    public int getEmailFieldLength() {
        int realLength = userEmailField.getValue().length();
        return realLength;
    }
    public int getPassFieldLength() {
        int realLength = userPasswordField.getValue().length();
        return realLength;
    }
    public int getConfPassFieldLength() {
        int realLength = userConfPasswordField.getValue().length();
        return realLength;
    }
}
