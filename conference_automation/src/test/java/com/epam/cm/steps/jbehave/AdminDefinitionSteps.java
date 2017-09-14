package com.epam.cm.steps.jbehave;

/**
 * Created by Serhii_Kobzar on 1/26/2017.
 */
import com.epam.cm.core.mail.Email;
import com.epam.cm.core.mail.MailCatcherClient;
import com.epam.cm.dto.AdminPageDTO;
import com.epam.cm.dto.CredentialsDTO;
import com.epam.cm.dto.UserRegistrationInfoDTO;
import com.epam.cm.steps.serenity.AdminPageSteps;
import com.epam.cm.steps.serenity.CreateAccountPageSteps;

import com.epam.cm.steps.serenity.LoginPageSteps;
import com.epam.cm.steps.serenity.MyTalksPageSteps;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.Steps;

import org.hamcrest.Matcher;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.model.ExamplesTable;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.regex.Pattern;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsString;
import static org.jsoup.nodes.Document.OutputSettings.Syntax.html;

public class AdminDefinitionSteps {

    @Steps
    LoginPageSteps loginPageSteps;
    @Steps
    AdminPageSteps adminPageSteps;

    AdminPageDTO user;

    @Given("user logged as admin: $table")
    public void userLoggedAsAdmin(ExamplesTable table){
        CredentialsDTO user = table.getRowsAs(CredentialsDTO.class).get(0);
        loginPageSteps.loginInOneStep(user);
    }

    @Given("admin accesses Add New User popup: $table")
    public void userLoggedAsAdminAccessesAddNewUser(ExamplesTable table){
        CredentialsDTO user = table.getRowsAs(CredentialsDTO.class).get(0);
        loginPageSteps.loginInOneStep(user);
        loginPageSteps.clickManageUser();
        adminPageSteps.clickAddNewUser();
    }

    @When("admin accesses the Admin page")
    public void clickManageUSerBtn(){
        loginPageSteps.clickManageUser();
    }

    @When("admin fills data in in the following fields: Role, First Name, Last Name, E-mail, Password, Confirm Password: $table")
    public void fillDataInAddNewUser(final ExamplesTable registrationTable){
        boolean replaceNamedParameters = true;
        String userRole = registrationTable.getRowAsParameters(0,replaceNamedParameters).valueAs("role",String.class);
        String userFirstName = registrationTable.getRowAsParameters(0, replaceNamedParameters).valueAs("firstName",
                String.class);
        String userLastName = registrationTable.getRowAsParameters(0, replaceNamedParameters).valueAs("lastName",
                String.class);
        String userEmail = registrationTable.getRowAsParameters(0, replaceNamedParameters).valueAs("email",
                String.class);
        String userPassword = registrationTable.getRowAsParameters(0, replaceNamedParameters).valueAs("password",
                String.class);
        String userConfPassword = registrationTable.getRowAsParameters(0, replaceNamedParameters).valueAs("confirmPassword", String.class);

         user = new AdminPageDTO() {
            {
                setRole(userRole);
                setFirstName(userFirstName);
                setLastName(userLastName);
                setEmail(userEmail);
                setPassword(userPassword);
                setConfirmPassword(userConfPassword);
            }
        };

        adminPageSteps.fillNewUserInfo(user);
        adminPageSteps.clickSaveBtn();
    }

    @When("admin fills invalid data in in the following fields: Role, First Name, Last Name, E-mail, Password, Confirm Password: $table")
    public void fillInvalidDataInAddNewUser(final ExamplesTable registrationTable){
        boolean replaceNamedParameters = true;
        String userRole = registrationTable.getRowAsParameters(0,replaceNamedParameters).valueAs("role",String.class);
        String userFirstName = registrationTable.getRowAsParameters(0, replaceNamedParameters).valueAs("firstName",
                String.class);
        String userLastName = registrationTable.getRowAsParameters(0, replaceNamedParameters).valueAs("lastName",
                String.class);
        String userEmail = registrationTable.getRowAsParameters(0, replaceNamedParameters).valueAs("email",
                String.class);
        String userPassword = registrationTable.getRowAsParameters(0, replaceNamedParameters).valueAs("password",
                String.class);
        String userConfirmPassword = registrationTable.getRowAsParameters(0, replaceNamedParameters)
                .valueAs("confirmPassword", String.class);

        user = new AdminPageDTO() {
            {
                setRole(userRole);
                setFirstName(userFirstName);
                setLastName(userLastName);
                setEmail(userEmail);
                setPassword(userPassword);
                setConfirmPassword(userConfirmPassword);
            }
        };

        adminPageSteps.fillNewUserInfo(user);
        adminPageSteps.clickSaveBtn();
    }

    @When("admin leaves empty data in all fields")
    public void leaveAllFieldsEmpty(){
        adminPageSteps.clickSaveBtn();
    }


    @Then("user can see all created user in the table")
    public void checkAdminPage(){
        Assert.assertTrue(adminPageSteps.areAllElementsDisplayed());
    }

    @Then("new user appears in the user table")
    public void userAppearsInTable(){
        String firstAndLastName = user.getLastName()+" "+ user.getFirstName();
        Assert.assertThat(adminPageSteps.findNewAddedUser(firstAndLastName).toLowerCase(),is(user.getLastName().toLowerCase()+" "+ user.getFirstName().toLowerCase()));
    }

    @Then("<expectedText> is highlighted in all empty fields: $table")
    public void checkEmptyFieldsMsg(final ExamplesTable registrationTable){
        boolean replaceNamedParameters = true;
        String expectedText = registrationTable.getRowAsParameters(0, replaceNamedParameters).valueAs("expectedText",
                String.class);
        String emptyFields = registrationTable.getRowAsParameters(0, replaceNamedParameters).valueAs("position",
                String.class);
        String actual = adminPageSteps.getTextFromEmptyFields(emptyFields, expectedText);
        Assert.assertThat(actual, is(expectedText));
    }

    @Then("text msg is highlighted in all incorrect fields: $table")
    public void checkWrongInputMsg(final ExamplesTable registrationTable){
        boolean replaceNamedParameters = true;
        String firstExpectedText = registrationTable.getRowAsParameters(0, replaceNamedParameters)
                .valueAs("firstExpectedText", String.class);
        String secondExpectedText = registrationTable.getRowAsParameters(0, replaceNamedParameters)
                .valueAs("secondExpectedText", String.class);
        String incorrectFields = registrationTable.getRowAsParameters(0, replaceNamedParameters).valueAs("position",
                String.class);
        String expectedText = firstExpectedText + secondExpectedText;
        String actual = adminPageSteps.getHighlightedTextInIncorrectFields(incorrectFields, firstExpectedText,
                secondExpectedText);
        Assert.assertThat(actual, is(expectedText));
    }

}
