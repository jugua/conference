package conferenceManagement.selenium.steps.jbehaveSteps;

import conferenceManagement.selenium.steps.serenitySteps.SignInSteps;
import net.thucydides.core.annotations.Steps;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;



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
    public void loginAsUser(String nickname){
       user.loginAsUser(nickname);
    }

    @Then("user signed in successfully")
    public void signedIn(){
        Assert.assertTrue(user.isSignedIn());
        user.logout();
    }

    @Then("user is unsigned")
    public void thenUserIsUnsigned() {
        Assert.assertFalse(user.isSignedIn());
        user.logout();
    }

}
