package com.epam.cm.steps.serenity;

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

}
