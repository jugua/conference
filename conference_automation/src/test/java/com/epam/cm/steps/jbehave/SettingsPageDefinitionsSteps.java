package com.epam.cm.steps.jbehave;

import com.epam.cm.core.mail.MailCatcherClient;
import com.epam.cm.dto.CredentialsDTO;
import com.epam.cm.dto.SettingsDTO;
import com.epam.cm.steps.serenity.CreateAccountPageSteps;
import com.epam.cm.steps.serenity.LoginPageSteps;
import com.epam.cm.steps.serenity.SettingsPageSteps;

import net.thucydides.core.annotations.Steps;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.model.ExamplesTable;
import org.junit.Assert;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SettingsPageDefinitionsSteps {
    @Steps
    SettingsPageSteps settingsSteps;
    @Steps
    LoginPageSteps loginPageSteps;
    @Steps
    CreateAccountPageSteps createAccountPageSteps;

    SettingsDTO settingsDTO;
    CredentialsDTO user;
    MailCatcherClient mailCatcherClient = new MailCatcherClient();

    @Given("user on the settings page logged as speaker: $examplesTable")

    public void goToSettingsPageAsSpeaker(ExamplesTable table) {
        user = table.getRowsAs(CredentialsDTO.class).get(0);
        loginPageSteps.loginInOneStep(user);
        loginPageSteps.clickSettingsOption();
    }

    @Given("logged as speaker user changes his password: $actTable")
    public void changePassword(ExamplesTable table) {

        user = table.getRowsAs(CredentialsDTO.class).get(0);

        loginPageSteps.loginInOneStep(user);
        loginPageSteps.clickSettingsOption();
        settingsSteps.clickEditLinkNextToPw();
        settingsDTO = table.getRowsAs(SettingsDTO.class).get(0);
        settingsSteps.typeCurrentPw(settingsDTO);
        settingsSteps.typeNewPw(settingsDTO);
        settingsSteps.typeConfirmPw(settingsDTO);
        settingsSteps.clickPwSaveBtn();
        loginPageSteps.logout();
    }

    @When("user click on the Edit link next to Email")

    public void clickEditLinkNextToEmail() {
        settingsSteps.clickEditLinkNextToEmail();
    }

    @When("type email in New email field: $examplesTable")
    public void typeEmail(ExamplesTable table) {
        boolean replaceNamedParameters = true;
        String email = table.getRowAsParameters(0, replaceNamedParameters).valueAs("newEmail", String.class);
        settingsDTO = new SettingsDTO() {

            {
                setOldEmail(user.getEmail());
                setNewEmail(email);
            }

            public void clickEdtLinkNextToEmail() {
                settingsSteps.clickEditLinkNextToEmail();
            }
        };
        settingsSteps.typeEmail(settingsDTO.getNewEmail());
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

    @When("clicks email cancel button")
    public void clickEmailCancelBtn() {
        settingsSteps.clickEmailCancelBtn();
    }

    @When("clicks Name's field Cancel button")
    public void clickNameCancelBtn() {
        settingsSteps.clickNameCancelBtn();
    }

    @When("user click on the Edit link next to Password")
    public void clickEditlinkNextToPw() {
        settingsSteps.clickEditLinkNextToPw();
    }

    @When("enter current password in 'Current password' field: $actTable")
    public void fillCurrentPwField(ExamplesTable table) {
        boolean replaceNamedParameters = true;
        String currentPw = table.getRowAsParameters(0, replaceNamedParameters).valueAs("currentPw", String.class);
        SettingsDTO settingsDTO = new SettingsDTO() {
            {
                setCurrentPw(currentPw);
            }
        };
        settingsSteps.typeCurrentPw(settingsDTO);
    }

    @When("enter new password in 'New password' field and confirms it: $actTable")
    public void fillNewPwField(ExamplesTable table) {
        boolean replaceNamedParameters = true;
        String newPw = table.getRowAsParameters(0, replaceNamedParameters).valueAs("newPw", String.class);
        String confirmPw = table.getRowAsParameters(0, replaceNamedParameters).valueAs("confirmNewPw", String.class);
        SettingsDTO settingsDTO = new SettingsDTO() {
            {
                setNewPw(newPw);
                setConfirmNewPw(confirmPw);
            }
        };
        settingsSteps.typeNewPw(settingsDTO);
        settingsSteps.typeConfirmPw(settingsDTO);
    }

    @When("clicks password's Save button")
    public void clickPwSaveBtn() {
        settingsSteps.clickPwSaveBtn();
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

        Assert.assertThat(firstName.trim().toLowerCase(), is(settingsSteps.getFirstNameLbl().trim().toLowerCase()));
        Assert.assertThat(lastName.trim().toLowerCase(), is(settingsSteps.getLastNameLbl().trim().toLowerCase()));
        Assert.assertTrue(settingsSteps.isSaveBtnVisible());
        Assert.assertTrue(settingsSteps.isCancelBtnVisble());
    }

    @Then("Empty field is highlighted in red and  message saying '$msg' is shown")
    public void lastNameIsHighlightedAndMsgAppears(String msg) {
        Assert.assertTrue(settingsSteps.isLastNameHighlighted());
        Assert.assertThat(msg.trim(), is(settingsSteps.getNameErrorMsg().trim()));
    }

    @Then("empty fields are highlighted in red and message saying '$msg' is shown")
    public void emptyFieldsAreHighlighted(String msg) {
        Assert.assertTrue(settingsSteps.isFirstNameHighlighted());
        Assert.assertTrue(settingsSteps.isLastNameHighlighted());
        Assert.assertThat(msg.trim(), is(settingsSteps.getNameErrorMsg().trim()));
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
    public void titleIsReplaced(ExamplesTable table) {
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
    public void thenLastNameIsNotChanged(ExamplesTable table) {
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
    public void thenFirstNameIsNotChanged(ExamplesTable table) {
        boolean replaceNamedParameters = true;
        String firstName = table.getRowAsParameters(0, replaceNamedParameters).valueAs("validData", String.class);
        SettingsDTO settingsDTO = new SettingsDTO() {
            {
                setFirstName(firstName);
            }
        };

        Assert.assertTrue(settingsSteps.userCantTypeMoreThenMaxFirstName(settingsDTO));
    }

    @Then("Changes are not saved and user can see his/her old name in the Name row: $actTbl")
    public void isDataUnsaved(ExamplesTable table) {
        boolean replaceNamedParameters = true;
        String lastName = table.getRowAsParameters(0, replaceNamedParameters).valueAs("validData", String.class);
        String firstName = table.getRowAsParameters(0, replaceNamedParameters).valueAs("validData", String.class);
        SettingsDTO settingsDTO = new SettingsDTO() {
            {
                setFirstName(firstName);
                setLastName(lastName);
            }
        };

        Assert.assertThat(settingsDTO.getLastName(), is(not(settingsSteps.getLastNameInput())));
        Assert.assertThat(settingsDTO.getFirstName(), is(not(settingsSteps.getFirstNameInput())));

    }

    @Then("email didnt change")
    public void emailDidntChange() {
        Assert.assertFalse(settingsSteps.isEmailChanged(settingsDTO.getNewEmail()));
    }

    @Then("user can log in with old credentials")
    public void userCanUseOldCredentials() {
        loginPageSteps.logout();
        loginPageSteps.unsignedUserInHomePage();
        loginPageSteps.clickOnAccountMenu();
        loginPageSteps.typeLoginAndPassword(user);
        loginPageSteps.clickSignInButton();
        loginPageSteps.clickSettingsOption();
        settingsSteps.clickEditLinkNextToEmail();
        Assert.assertFalse(settingsSteps.isEmailChanged(settingsDTO.getNewEmail()));
    }

    @Then("user is able to login with new password: $actTable")
    public void loginWithNewPw(ExamplesTable table) {
        loginPageSteps.logout();
        CredentialsDTO user = table.getRowsAs(CredentialsDTO.class).get(0);
        loginPageSteps.unsignedUserInHomePage();
        loginPageSteps.clickOnAccountMenu();
        loginPageSteps.typeLoginAndPassword(user);
        loginPageSteps.clickSignInButton();
        loginPageSteps.clickSettingsOption();
    }


    @Given("changes his password: $actTable")
    @Then("changes his password: $actTable")
    public void resetBackPw(ExamplesTable table) {
        settingsSteps.clickEditLinkNextToPw();
        settingsDTO = table.getRowsAs(SettingsDTO.class).get(0);
        settingsSteps.typeCurrentPw(settingsDTO);
        settingsSteps.typeNewPw(settingsDTO);
        settingsSteps.typeConfirmPw(settingsDTO);
        settingsSteps.clickPwSaveBtn();
        loginPageSteps.logout();
    }

    @Then("an email is send to users email adress: $actTable")
    public void receiveEmail(ExamplesTable table) {
        user = table.getRowsAs(CredentialsDTO.class).get(0);
        System.out.println(mailCatcherClient.getLastEmail());
        Assert.assertThat(mailCatcherClient.getLastEmail().getRecipients().get(0),
                containsString(user.getEmail().toLowerCase()));
    }

    @Then("subject's name is '$subject'")
    public void emailSubjectMatches(String subject) {
        Assert.assertThat(mailCatcherClient.getLastEmail().getSubject(), is(subject));
    }

    @Then("body contains:'$msg1 <name>', '$msg2', '$msg3'")
    public void checkEmailsBody(String msg1, String name, String msg2, String msg3) {
        Assert.assertThat(mailCatcherClient
                .getEmailById(mailCatcherClient.getLastEmail().getId(), MailCatcherClient.ResponseType.HTML).toString(),
                containsString(msg1));
        Assert.assertThat(mailCatcherClient
                .getEmailById(mailCatcherClient.getLastEmail().getId(), MailCatcherClient.ResponseType.HTML).toString(),
                containsString(name));
        Assert.assertThat(mailCatcherClient
                .getEmailById(mailCatcherClient.getLastEmail().getId(), MailCatcherClient.ResponseType.HTML).toString(),
                containsString(msg2));
        Assert.assertThat(mailCatcherClient
                .getEmailById(mailCatcherClient.getLastEmail().getId(), MailCatcherClient.ResponseType.HTML).toString(),
                containsString(msg3));
    }

    @Then("messages apears saying '$msg'")
    public void checkCurrentMsgError(String msg) {
        Assert.assertThat(msg, is(settingsSteps.getCurrentErrorPw()));
    }

    @Then("notification change email link was sent on email")
    public void changeEmailLinkWasSent() {
        createAccountPageSteps.openLinkFromEmail();
        Assert.assertTrue(createAccountPageSteps.isUserLoggedIn());
    }

    @Then("email is changed")
    public void isEmailchanged() {
        loginPageSteps.clickSettingsOption();
        settingsSteps.clickEditLinkNextToEmail();
        Assert.assertTrue(settingsSteps.isEmailChanged(settingsDTO.getNewEmail()));
        settingsSteps.typeEmail(settingsDTO.getOldEmail());
        clickEmailSaveBtn();
        createAccountPageSteps.openLinkFromEmail();
    }
}
