package com.epam.cm.pages;

/**
 * Created by Serhii_Kobzar on 1/26/2017.
 */

import java.util.List;

import com.epam.cm.dto.MyInfoFieldsDTO;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;

import org.openqa.selenium.WebDriver;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.openqa.selenium.By.xpath;

public class AdminPage {

    /***
     * organiser accesses admin page
     */
    @FindBy(xpath = "//*[@class='btn my-talks__button']")
    WebElementFacade addNewUserBtn;
    @FindBy(xpath = "")
    WebElementFacade roleColumn;
    @FindBy(xpath = "")
    WebElementFacade nameColumn;
    @FindBy(xpath = "")
    WebElementFacade emailColumn;

    public void clickAddNewUserBtn(){
        addNewUserBtn.click();
    }


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

    public void selectRole(String role){
        roleSelect.selectByIndex(Integer.parseInt(role));
    }

    public void setFirstName(String firstName){
        firstNameInput.clear();
        firstNameInput.type(firstName);
    }

    public void setLastName(String lastName){
        lastNameInput.clear();
        lastNameInput.type(lastName);
    }

    public void setAddNewUserEmail(String email){
        emailInput.clear();
        emailInput.type(email);
    }

    public void setAddNewUserPassword(String password){
        passwordInput.clear();
        passwordInput.type(password);
    }

    public void setAddNewUserConfirmPassword(String confirmPassword){
        confirmPasswordInput.clear();
        confirmPasswordInput.type(confirmPassword);
    }

    public void clickSaveNewUserBtn(){
        saveNewUserBtn.click();
    }


}
