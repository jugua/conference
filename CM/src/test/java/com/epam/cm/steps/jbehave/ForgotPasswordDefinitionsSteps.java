package com.epam.cm.steps.jbehave;

import com.epam.cm.steps.serenity.ForgotPaswordPageSteps;
import net.thucydides.core.annotations.Steps;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;

/**
 * Created by Serhii_Kobzar on 11/10/2016.
 */
public class ForgotPasswordDefinitionsSteps {

    @Steps
    ForgotPaswordPageSteps forgotPwdPgSteps;

    @Given("the unsigned user accesses the conference management home page")
    public void givenUserIsOnHomePage() {
        forgotPwdPgSteps.gotoTheHomePage();
    }

    @When("user clicks the login button")
    public void whenUserClicksLoginBtn() { forgotPwdPgSteps.clickOnAccountMenu();
    }

    @When("clicks the forgot password link")
    public void whenUserClicksForgotPWLink() { forgotPwdPgSteps.clickForgotPwLink();
    }

    @Then("new pop up will appears saying $forgotLblMsg")
    public void thenPopUpAppearsMsg(String forgotLblMsg) {
        forgotPwdPgSteps.checkPopUpIsPresent();
        forgotPwdPgSteps.checkForgotPwLinkIsPresent();
        Assert.assertEquals(forgotPwdPgSteps.getForgotPwLblText(), forgotLblMsg);
    }

    @Then("Cancel button appears")
    public void thenCancelBtnAppears() {
        forgotPwdPgSteps.checkCancelBtn();
    }

    @Then("Continue button appears")
    public void thenContinieBtnAppears() {
        forgotPwdPgSteps.checkContinueBtn();
    }
}
