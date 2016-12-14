package com.epam.cm.steps.jbehave;

import com.epam.cm.dto.CredentialsDTO;
import com.epam.cm.dto.SettingsDTO;
import com.epam.cm.steps.serenity.LoginPageSteps;
import com.epam.cm.steps.serenity.SettingsPageSteps;
import net.thucydides.core.annotations.Steps;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.model.ExamplesTable;
import org.junit.Assert;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Created by Lev_Serba on 12/12/2016.
 */
public class SettingsPageDefinitionsSteps {
    @Steps
    SettingsPageSteps settingsSteps;
    @Steps
    LoginPageSteps loginPageSteps;

    @Given("user on the settings page logged as speaker: $examplesTable")
    public void goToSettingsPageAsSpeaker(ExamplesTable table) {
        CredentialsDTO user = table.getRowsAs(CredentialsDTO.class).get(0);
        loginPageSteps.unsignedUserInHomePage();
        loginPageSteps.clickOnAccountMenu();
        loginPageSteps.typeLoginAndPassword(user);
        loginPageSteps.clickSignInButton();
        loginPageSteps.clickSettingsOption();
    }

    @When("user click on the Edit link next to Email")
    public void clickEdtLinkNextToEmail() {
        settingsSteps.clickEditLinkNextToEmail();
    }


    @When("type incorrect values in New email field: $examplesTable")
    public void typeIncorrectEmail(ExamplesTable table) {
        boolean replaceNamedParameters = true;
        String email = table.getRowAsParameters(0, replaceNamedParameters).valueAs("wrongEmail", String.class);
        SettingsDTO settingsDTO = new SettingsDTO() {
            {
                setEmail(email);
            }
        };
        settingsSteps.typeEmail(settingsDTO);
    }


    @When("user click on the Edit link next to Name")
    public void clickEditLinkNextToName() {
        settingsSteps.clickEditLinkNextToName();
    }

    @When("leaves 'First name' field empty")
    public void leaveFirstNameEmpty() {
        settingsSteps.leaveFirstNameInputEmpty();
    }

    @When("puts data in First name: $examplesTable")
    public void putValidDataIntoFirstName(ExamplesTable table) {
        boolean replaceNamedParameters = true;
        String firstName = table.getRowAsParameters(0, replaceNamedParameters).valueAs("validData", String.class);
        SettingsDTO settingsDTO = new SettingsDTO() {
            {
                setFirstName(firstName);
            }
        };
        settingsSteps.typeFirstName(settingsDTO);
    }

    @When("leaves 'Last name' field empty")
    public void leaveLastNameEmpty() {
        settingsSteps.leaveLastNameInputEmpty();
    }

    @When("puts data in Last name: $examplesTable")
    public void putValidDataIntoLastName(ExamplesTable table) {
        boolean replaceNamedParameters = true;
        String lastName = table.getRowAsParameters(0, replaceNamedParameters).valueAs("validData", String.class);
        SettingsDTO settingsDTO = new SettingsDTO() {
            {
                setLastName(lastName);
            }
        };
        settingsSteps.typeLastName(settingsDTO);
    }

    @When("clicks name save button")
    public void clickNameSaveBtn() {
        settingsSteps.clickNameSaveBtn();
    }

    @When("clicks email save button")
    public void clickEmailSaveBtn() {
        settingsSteps.clickEmailSaveBtn();
    }

    @Then("Current Email and New Email fields are visible")
    public void areCurrentEmailAndNewEmailFieldsVisible() {
        Assert.assertTrue(settingsSteps.areCurrentEmailAndNewEmailFieldsVisible());
    }

    @Then("user see a warning message saying '$errorMsg'")
    public void userSeeWarningMassage(String errorMsg) {
        Assert.assertTrue(settingsSteps.isErrorMsgShown(errorMsg));
    }


    @Then("'Name' field has following elements: '$firstName', '$lastName' fields 'Save' and 'Cancel' buttons")
    public void checkNameFieldElements(String firstName, String lastName) {
        System.out.println();

        Assert.assertThat(firstName, is(settingsSteps.getFirstNameLbl()));
        Assert.assertThat(lastName, is(settingsSteps.getLastNameLbl()));
        Assert.assertTrue(settingsSteps.isSaveBtnVisible());
        Assert.assertTrue(settingsSteps.isCancelBtnVisble());
    }

    @Then("Empty field is highlighted in red and  message saying '$msg' is shown")
    public void lastNameIsHighlightedAndMsgAppears(String msg) {
        Assert.assertTrue(settingsSteps.isLastNameHighlighted());
        Assert.assertThat(msg, is(settingsSteps.getNameErrorMsg()));
    }

    @Then("empty fields are highlighted in red and message saying '$msg' is shown")
    public void emptyFieldsAreHighlighted(String msg) {
        Assert.assertTrue(settingsSteps.isFirstNameHighlighted());
        Assert.assertTrue(settingsSteps.isLastNameHighlighted());
        Assert.assertThat(msg, is(settingsSteps.getNameErrorMsg()));
    }
    @Then("user click on the Edit link next to Name")
    public void clickThenEditLinkNextToName() {
        settingsSteps.clickEditLinkNextToName();
    }

    @Then("the new last name is changed: $exampleTable")
    public void isLastNameSaved(ExamplesTable table) {
        boolean replaceNamedParameters = true;
        String lastName = table.getRowAsParameters(0, replaceNamedParameters).valueAs("validData", String.class);
        SettingsDTO settingsDTO = new SettingsDTO() {
            {
                setLastName(lastName);
            }
        };

        Assert.assertThat(settingsDTO.getLastName(), is(settingsSteps.getLastNameInput()));

    }

    @Then("the new first name is changed: $exampleTable")
    public void isFirstNameSaved(ExamplesTable table) {
        boolean replaceNamedParameters = true;
        String firstName = table.getRowAsParameters(0, replaceNamedParameters).valueAs("validData", String.class);
        SettingsDTO settingsDTO = new SettingsDTO() {
            {
                setFirstName(firstName);
            }
        };

        Assert.assertThat(settingsDTO.getFirstName(), is(settingsSteps.getFirstNameInput()));

    }

    @Then("menu Title is replaced by new name: $exampleTable")
    public void titleIsReplaced(ExamplesTable table){
        boolean replaceNamedParameters = true;
        String firstName = table.getRowAsParameters(0, replaceNamedParameters).valueAs("validData", String.class);
        SettingsDTO settingsDTO = new SettingsDTO() {
            {
                setFirstName(firstName);
            }
        };
        assertThat(settingsDTO.getFirstName() + "'s Account", is(loginPageSteps.getAccountMenuTitle()));
    }

    @Then("user can`t enter/type more than the maximum allowed characters in last name field: $actTable")
    public void thenLastNameIsNotChanged(ExamplesTable table){
        boolean replaceNamedParameters = true;
        String lastName = table.getRowAsParameters(0, replaceNamedParameters).valueAs("validData", String.class);
        SettingsDTO settingsDTO = new SettingsDTO() {
            {
                setLastName(lastName);
            }
        };

        Assert.assertTrue(settingsSteps.userCantTypeMoreThenMaxLastName(settingsDTO));
    }

    @Then("user can`t enter/type more than the maximum allowed characters in first name field: $actTable")
    public void thenFirstNameIsNotChanged(ExamplesTable table){
        boolean replaceNamedParameters = true;
        String firstName = table.getRowAsParameters(0, replaceNamedParameters).valueAs("validData", String.class);
        SettingsDTO settingsDTO = new SettingsDTO() {
            {
                setFirstName(firstName);
            }
        };

        Assert.assertTrue(settingsSteps.userCantTypeMoreThenMaxFirstName(settingsDTO));
    }
}
