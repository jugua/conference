package com.epam.cm.steps.jbehave;

import com.epam.cm.dto.CredentialsDTO;
import com.epam.cm.steps.serenity.LoginPageSteps;
import com.epam.cm.steps.serenity.SettingsPageSteps;
import net.thucydides.core.annotations.Steps;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.model.ExamplesTable;
import org.junit.Assert;

/**
 * Created by Lev_Serba on 12/12/2016.
 */
public class SettingsPageDefinitionsSteps {
    @Steps
    SettingsPageSteps settingsSteps;
    @Steps
    LoginPageSteps loginPageSteps;

    @Given("user on the settings page logged as speaker: $examplesTable")
        public void goToSettingsPageAsSpeaker(ExamplesTable table){
        CredentialsDTO user = table.getRowsAs(CredentialsDTO.class).get(0);
        loginPageSteps.unsignedUserInHomePage();
        loginPageSteps.clickOnAccountMenu();
        loginPageSteps.typeLoginAndPassword(user);
        loginPageSteps.clickSignInButton();
        loginPageSteps.clickSettingsOption();
    }

    @When("user click on the Edit link next to Email")
    public void clickEdtLinkNextToEmail(){
        settingsSteps.clickEditLinkNextToEmail();
    }

    @Then("Current Email and New Email fields are visible")
    public void areCurrentEmailAndNewEmailFieldsVisible(){
        Assert.assertTrue(settingsSteps.areCurrentEmailAndNewEmailFieldsVisible());
    }

}
