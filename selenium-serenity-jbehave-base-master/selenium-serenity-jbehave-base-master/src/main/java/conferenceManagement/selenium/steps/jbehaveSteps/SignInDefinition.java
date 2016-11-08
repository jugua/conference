package conferenceManagement.selenium.steps.jbehaveSteps;

import conferenceManagement.selenium.steps.serenitySteps.SignInSteps;
import net.thucydides.core.annotations.Steps;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

/**
 * Created by Lev_Serba on 11/7/2016.
 */
public class SignInDefinition {
    @Steps
    SignInSteps user;

    @Given("the unsigned user accesses the conference management home page")
    public void openConferenceManagementHomePage(){
        user.openHomePage();
        user.logout();
    }

    @When("when he login as $user")
    public void login_as_user(String nickname){

       user.loginAsUser(nickname);

    }

    @Then("user signed in successfully")
    public void signedIn(){
        Assert.assertTrue(user.isSignedIn());
    }

    /*@When("when he login as $user")
    public void whenWhenHeLoginAsInvalideUser() {
        // PENDING
    }
*/
    @Then("user is unsigned")
    public void thenUserIsUnsigned() {
        Assert.assertFalse(user.isSignedIn());
    }

}
