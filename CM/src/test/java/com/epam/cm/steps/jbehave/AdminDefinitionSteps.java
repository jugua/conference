package com.epam.cm.steps.jbehave;

/**
 * Created by Serhii_Kobzar on 1/26/2017.
 */
import com.epam.cm.core.mail.Email;
import com.epam.cm.core.mail.MailCatcherClient;
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

    @Given("user logged as admin: $table")
    public void userLoggedAsAdmin(ExamplesTable table){
        CredentialsDTO user = table.getRowsAs(CredentialsDTO.class).get(0);
        loginPageSteps.loginInOneStep(user);
    }

    @When("admin accesses the Admin page")
    public void clickManageUSerBtn(){
        loginPageSteps.clickManageUser();
    }

    @Then("user can see all created user in the table")
    public void checkAdminPage(){
        Assert.assertTrue(adminPageSteps.areAllElementsDisplayed());
    }
}
