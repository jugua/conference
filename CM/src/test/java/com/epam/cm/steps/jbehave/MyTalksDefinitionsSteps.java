package com.epam.cm.steps.jbehave;

import com.epam.cm.dto.CredentialsDTO;
import com.epam.cm.steps.serenity.LoginPageSteps;
import com.epam.cm.steps.serenity.MyTalksPageSteps;

import net.thucydides.core.annotations.Steps;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.model.ExamplesTable;
import org.junit.Assert;

import static org.hamcrest.Matchers.is;

/**
 * Created by Serhii_Kobzar on 12/26/2016.
 */
public class MyTalksDefinitionsSteps {

    @Steps
    LoginPageSteps loginPageSteps;
    @Steps
    MyTalksPageSteps myTalksPageSteps;

    @Given("user logged as speaker accessing 'My Talks' page: $loginTable")
    public void loginAsUser(ExamplesTable table) {
        CredentialsDTO user = table.getRowsAs(CredentialsDTO.class).get(0);
        loginPageSteps.unsignedUserInHomePage();
        loginPageSteps.clickOnAccountMenu();
        loginPageSteps.typeLoginAndPassword(user);
        loginPageSteps.clickSignInButton();
        loginPageSteps.clickMyTalks();
    }

    @When("user clicks on 'Submit New Talk' button")
    public void clickSubmitNewTalkBtn() {
        myTalksPageSteps.clickSubmitNewTalkBtn();
    }

    @When("clicks 'Submit' button")
    public void clickSubmitBtn() {
        myTalksPageSteps.clickBigPopUpSubmitBtn();
    }

    @When("user fiels data in 'Title','Description' and 'Additional Info'")
    public void fielInvalidDataInFields() {

    }

    @When("choose Topic, Type, Language, Level dropdown menu")
    public void chooseAllDopDownMenu() {

    }

    @Then("pop up is shown with text '$msg'")
    public void checkPopUpMsg(String msg) {
        Assert.assertThat(msg, is(myTalksPageSteps.getEmptyMyInfoPopUpText()));
    }

    @Then("clicks OK button")
    public void clickOKBtn() {
        myTalksPageSteps.clickOkBtn();
    }

    @Then("all fields are highlighted in red")
    public void areFieldsHighlighted() {
        Assert.assertTrue(myTalksPageSteps.isTitleHighL());
        Assert.assertTrue(myTalksPageSteps.isDescriptionHighL());
        Assert.assertTrue(myTalksPageSteps.isTopicHighL());
        Assert.assertTrue(myTalksPageSteps.isTypeHighL());
        Assert.assertTrue(myTalksPageSteps.isLanguageHighL());
        Assert.assertTrue(myTalksPageSteps.isLevelHighL());
    }

    @Then("pop-up window '$msg' is shown")
    public void checkPopUpMsgInSubmitNewTalk(String msg) {
        Assert.assertThat(msg,is(myTalksPageSteps.getEmptyFieldsPopUpText()));
    }

    @Then("data are not filled. System doesn't accept invalid data")
    public void checkInFieldDataLength() {

    }

    @Then("talk appears in grid and have 'New' status")
    public void checkNewTalk() {

    }

    @Then("info msg is shown saying '$firstMsg', '$secondMsg', '$thirdMsg'")
    public void checkInfoMsgs(String firstMsg, String secondMsg, String thirdMsg) {

    }

    @Then("click 'Yes' button")
    public void clickYesBtn() {

    }
}
