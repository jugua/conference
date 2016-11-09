package com.epam.cm.steps.jbehave;

import com.epam.cm.dto.CredentialsDTO;
import com.epam.cm.pages.HomePage;
import com.epam.cm.steps.serenity.LoginPageSteps;
import net.thucydides.core.annotations.Steps;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.model.ExamplesTable;
import org.jbehave.core.steps.Parameters;

import static org.junit.Assert.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


/**
 *
 */


public class LoginDefinitionsSteps {

    @Steps
    LoginPageSteps loginPageSteps;

    @Given("user is on Home page")
    public void givenUserIsOnHomePage() {
        loginPageSteps.gotoTheHomePage();
    }

    @When("user clicks 'Your Account' button in page header")
    public void whenUserClicksYourAccountButtonInPageHeader() {
        loginPageSteps.clickOnAccountMenu();
    }

    @Then("account menu is unfolding")
    public void thenAccountMenuIsUnfolding() {
        assertTrue(loginPageSteps.isAccountMenuUnfolded());
    }


    @Given("user filled in login form: $activityTable")
    public void givenUserFilledInLoginFormEmailPassword(final ExamplesTable credentialsTable) {
        final CredentialsDTO user = credentialsTable.getRowsAs(CredentialsDTO.class).get(0);

        loginPageSteps.typeLoginAndPassword(user);

    }

    @When("user clicks SignIn button on login form")
    public void whenUserClicksSignInButtonOnLoginForm() {
        loginPageSteps.clickSignInButton();
    }

    @Then("'Your Account' replaced by 'Speaker's Account'")
    public void thenYourAccountReplacedBySpeakerssAccount() {
        assertThat(loginPageSteps.getAccountMenuTitle(),is("Speaker's Account"));
    }

    @Then("there is 'Sign Out' button in account menu")
    public void thenThereIsSignOutButtonInAccountMenu() {
        assertTrue(loginPageSteps.isLoggedIn());
    }

    @When("user logs out")
    public void whenUserLogsOut() {
        loginPageSteps.logout();
    }

    @Then("user is redirected to Home page")
    public void thenUserIsRedirectedToHomePage() {
        assertTrue(loginPageSteps.isHomePageOpened());
    }

    @Then("there is 'Your Account' button in page header")
    public void thenThereIsYourAccountButtonInPageHeader() {
        assertThat(loginPageSteps.getAccountMenuTitle(),is("Your Account"));
    }

    @Then("user still in login form")
    public void thenUserStillInLoginForm(){
        assertTrue(loginPageSteps.isSignInFormOpened());
    }

    @Then("password field is highlighted")
    public void passwordFieldIsRed(){
        assertTrue(loginPageSteps.isPasswordFieldIsHighlited());
    }
    @Then("wrong password message is $msg")
    public void isWrongPassword(String msg){
        assertThat(loginPageSteps.getPasswordValidationMsg(),is(msg));
    }

}
