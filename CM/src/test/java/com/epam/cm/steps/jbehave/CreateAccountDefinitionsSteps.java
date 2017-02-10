package com.epam.cm.steps.jbehave;

import com.epam.cm.core.mail.MailCatcherClient;
import com.epam.cm.dto.UserRegistrationInfoDTO;
import com.epam.cm.steps.serenity.CreateAccountPageSteps;

import net.thucydides.core.annotations.Steps;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.model.ExamplesTable;
import org.junit.Assert;

import static org.hamcrest.CoreMatchers.is;

public class CreateAccountDefinitionsSteps {

    @Steps
    CreateAccountPageSteps createAccountSteps;

    MailCatcherClient mailCatcherClient = new MailCatcherClient();

    @Given("user is on the sign up page")
    public void userOnSignUpPage() {
        createAccountSteps.goToSignUpPage();
    }

    @When("user  fill in the following fields: First Name, Last Name, E-mail, Password, Confirm Password: $activityTable")
    public void userfillFields(final ExamplesTable registrationTable) {

        boolean replaceNamedParameters = true;
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

        UserRegistrationInfoDTO user = new UserRegistrationInfoDTO() {
            {
                setFirstName(userFirstName);
                setLastName(userLastName);
                setEmail(userEmail);
                setPassword(userPassword);
                setConfirmPassword(getPassword());
            }
        };

        createAccountSteps.typeRegistrationInfo(user);

    }

    @When("click submit button")
    public void userClickSubmitButton() {
        createAccountSteps.clickSubmitBtn();
    }

    @Then("new user is registered")
    public void userIsRegistered() {
        Assert.assertTrue(createAccountSteps.isRegisteredSuccessfully());
    }

    @Then("notification link was sent on email")
    public void notificationLinkWasSent() {
        createAccountSteps.openLinkFromEmail();
        Assert.assertTrue(createAccountSteps.isUserLoggedIn());
    }

    @When("user  leaves some of the following fields empty: First Name, Last Name, E-mail, Password, Confirm Password: $activityTable")
    public void userLeavesEmptyFields(final ExamplesTable registrationTable) {

        boolean replaceNamedParameters = true;
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

        UserRegistrationInfoDTO user = new UserRegistrationInfoDTO() {
            {
                setFirstName(userFirstName);
                setLastName(userLastName);
                setEmail(userEmail);
                setPassword(userPassword);
                setConfirmPassword(userConfirmPassword);
            }
        };

        createAccountSteps.fillRegistrationInfoNegativeTest(user);
    }

    @Then("$expectedText is highlighted in all the empty fields: $activityTable")
    public void emptyFieldsHighlighted(String abc, final ExamplesTable registrationTable) {

        boolean replaceNamedParameters = true;
        String expectedText = registrationTable.getRowAsParameters(0, replaceNamedParameters).valueAs("expectedText",
                String.class);
        String emptyFields = registrationTable.getRowAsParameters(0, replaceNamedParameters).valueAs("position",
                String.class);
        String actual = createAccountSteps.getHighlightedTextInEmptyFields(emptyFields, expectedText);
        Assert.assertThat(actual, is(expectedText));

    }

    @Then("new user is not registered")
    public void userIsNotregistered() {
        Assert.assertFalse(createAccountSteps.isRegisteredSuccessfully());
    }

    @When("user types wrong values in some of the following fields: First Name, Last Name, E-mail, Password, Confirm Password: $activityTable")
    public void userTypesWrongValues(final ExamplesTable registrationTable) {

        boolean replaceNamedParameters = true;
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

        UserRegistrationInfoDTO user = new UserRegistrationInfoDTO() {
            {
                setFirstName(userFirstName);
                setLastName(userLastName);
                setEmail(userEmail);
                setPassword(userPassword);
                setConfirmPassword(userConfirmPassword);
            }
        };

        createAccountSteps.fillRegistrationInfoNegativeTest(user);

    }

    @Then("text msg is highlighted in all the incorrect fields: $activityTable")
    public void incorrectFieldsHighlighted(final ExamplesTable registrationTable) {

        boolean replaceNamedParameters = true;
        String firstExpectedText = registrationTable.getRowAsParameters(0, replaceNamedParameters)
                .valueAs("firstExpectedText", String.class);
        String secondExpectedText = registrationTable.getRowAsParameters(0, replaceNamedParameters)
                .valueAs("secondExpectedText", String.class);
        String incorrectFields = registrationTable.getRowAsParameters(0, replaceNamedParameters).valueAs("position",
                String.class);
        String expectedText = firstExpectedText + secondExpectedText;
        String actual = createAccountSteps.getHighlightedTextInIncorrectFields(incorrectFields, firstExpectedText,
                secondExpectedText);
        Assert.assertThat(actual, is(expectedText));

    }

    @Then("user cant enter/type more than the maximum allowed characters: $activityTable")
    public void userCantEnterMoreThanMaxAllChar(final ExamplesTable registrationTable) {
        boolean replaceNamedParameters = true;
        String incorrectFields = registrationTable.getRowAsParameters(0, replaceNamedParameters).valueAs("position",
                String.class);

        if (incorrectFields.equals("nameField")) {
            int actualLength = createAccountSteps.getNameFieldLength();
            int expectedLength = 56;
            Assert.assertThat(actualLength, is(expectedLength));
        } else if (incorrectFields.equals("lastNameField")) {
            int actualLength = createAccountSteps.getLastNameFieldLength();
            int expectedLength = 56;
            Assert.assertThat(actualLength, is(expectedLength));
        } else if (incorrectFields.equals("passFieldMax")) {
            int actualLength = createAccountSteps.getPassFieldLength();
            int expectedLength = 30;
            Assert.assertThat(actualLength, is(expectedLength));
        } else if (incorrectFields.equals("confPassFieldMax")) {
            int actualLength = createAccountSteps.getConfPassFieldLength();
            int expectedLength = 30;
            Assert.assertThat(actualLength, is(expectedLength));
        }
    }

}
