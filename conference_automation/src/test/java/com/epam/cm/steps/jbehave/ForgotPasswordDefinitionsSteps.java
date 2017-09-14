package com.epam.cm.steps.jbehave;

import com.epam.cm.steps.serenity.ForgotPaswordPageSteps;

import net.thucydides.core.annotations.Steps;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsString;

public class ForgotPasswordDefinitionsSteps {

    @Steps
    ForgotPaswordPageSteps forgotPwdPgSteps;

    @Given("the unsigned user accesses the conference management home page")
    public void givenUserIsOnHomePage() {
        forgotPwdPgSteps.gotoTheHomePage();
    }

    @When("uses forgot password function")
    public void fastForgotPasswordCheck(){
        forgotPwdPgSteps.clickOnAccountMenu();
        forgotPwdPgSteps.clickForgotPwLink();
        forgotPwdPgSteps.checkPopUpIsPresent();
        forgotPwdPgSteps.checkForgotPwLinkIsPresent();
    }

    @When("user fiels email textbox with valid: <email>")
    public void whenValidEmailFieldFilled(String email) {
        forgotPwdPgSteps.typeEmailForgotPwField(email);
    }



    @When("user clicks the login button")
    public void whenUserClicksLoginBtn() {
        forgotPwdPgSteps.clickOnAccountMenu();
    }

    @When("clicks the forgot password link")
    public void whenUserClicksForgotPWLink() {
        forgotPwdPgSteps.clickForgotPwLink();
    }

    @Then("new pop up will appears saying '$forgotLblMsg'")
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

    @Then("clicks on Continue button")
    public void thenClickComtomueBtn() {
        forgotPwdPgSteps.clickContinueBtn();
    }

    @Then("message is shown sayin $emailErrorMsg")
    public void thenMsgEmptyEmail(String emailErrorMsg) {
        Assert.assertEquals(forgotPwdPgSteps.getEmptyEmailMsgText(), emailErrorMsg);
    }

    @Then("user fiels email textbox with valid: <email>")
    public void thenValidEmailFieldFilled(String email) {
        forgotPwdPgSteps.typeEmailForgotPwField(email);
    }

    @Then("user fiels email textbox with invalid: <email>")
    public void thenInvalidEmailFieldFilled(String email) {
        forgotPwdPgSteps.typeEmailForgotPwField(email);
    }

    @Then("email field is highlighted")
    public void thenEmailFieldIsHighlighted() {
        Assert.assertTrue(forgotPwdPgSteps.isEmailForgotPwFieldHighlighted());
    }

    @Then("message apears saying:'$msg'")
    public void thenPopUpApears(String msg) {
        Assert.assertThat(forgotPwdPgSteps.getNotficationText(), containsString(msg));

    }

    @Then("'Close' button is shown")
    public void thenCloseBtnIsShown() {
        Assert.assertTrue(forgotPwdPgSteps.isCancelBtnPresent());
    }

    @Then("message apears saying $msg")
    public void thenMsgApears(String msg) {
        Assert.assertThat(msg, is(forgotPwdPgSteps.getInvalidEmailMsg()));
    }

    @Then("message appears saying for not existing email '$msg'")
    public void thenErrorMsgApears(String msg) {
        Assert.assertThat(msg, is(forgotPwdPgSteps.getNoExistAccErrorMsg()));
    }

    @Then("notification link was sent on email for Forgot Password")
    public void openConfirmationLink(){
        forgotPwdPgSteps.openLinkFromEmail();
    }

    @Then("fills new password '$pw'")
    public  void fillNewPassword(String password){
        forgotPwdPgSteps.typeNewPassword(password);
        forgotPwdPgSteps.typeConfirmNewPassword(password);
        forgotPwdPgSteps.clickOnSaveBtn();
    }

}
