package com.epam.cm.steps.serenity;

import com.epam.cm.dto.AdminPageDTO;
import com.epam.cm.pages.AdminPage;
import com.epam.cm.core.utils.WebDriverSupport;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;
/**
 * Created by Serhii_Kobzar on 1/26/2017.
 */
public class AdminPageSteps {

    AdminPage adminPage;

    @Step
    public void fillNewUserInfo(AdminPageDTO adminPageDTO){
        adminPage.fillNewUserInfo(adminPageDTO);
    }

    @Step
    public String findNewAddedUser(String name){
        return adminPage.checkUserInTheGridByName(name);
    }

    @Step
    public String getTextFromEmptyFields(String emptyFieldsTemplate, String expectedText){
        return adminPage.getTextFromEmptyFields(emptyFieldsTemplate,expectedText);
    }

    @Step
    public void clickAddNewUser(){
        adminPage.clickAddNewUserBtn();
    }

    @Step
    public void selectRole(String role){
        adminPage.selectRole(role);
    }

    @Step
    public void typeFirstName(String name){
        adminPage.setFirstName(name);
    }

    @Step
    public void typeLastName(String name){
        adminPage.setLastName(name);
    }

    @Step
    public void typeEmail(String email){
        adminPage.setAddNewUserEmail(email);
    }

    @Step
    public void typePassword(String password){
        adminPage.setAddNewUserPassword(password);
    }

    @Step
    public void typeConfirmPassword(String confirmPassword){
        adminPage.setAddNewUserConfirmPassword(confirmPassword);
    }

    @Step
    public void clickSaveBtn(){
        adminPage.clickSaveNewUserBtn();
    }

    @Step
    public boolean areAllElementsDisplayed(){
        if(adminPage.addNewUserBtnIsVisible() &&
                adminPage.isEmailColumnDisplayed() &&
                adminPage.isNameColumnDisplayed() &&
                adminPage.isRoleColumnDisplayed()) {
            return true;
        }
        else {
            return false;
        }
    }
    @Step
    public String getHighlightedTextInIncorrectFields(String incorrectFields, String firstExpectedText,
                                                      String secondExpectedText) {
        return adminPage.getHighlightedTextIncorrectFields(incorrectFields,firstExpectedText,secondExpectedText);
    }
}
