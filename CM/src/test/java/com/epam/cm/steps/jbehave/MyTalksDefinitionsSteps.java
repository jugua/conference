package com.epam.cm.steps.jbehave;

import com.epam.cm.dto.CredentialsDTO;
import com.epam.cm.steps.serenity.LoginPageSteps;
import com.epam.cm.steps.serenity.MyTalksPageSteps;

import net.thucydides.core.annotations.Steps;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.model.ExamplesTable;

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

    }

    @When("clicks 'Submit' button")
    public void clickSubmitBtn(){

    }

    @When("user fiels data in 'Title','Description' and 'Additional Info'")
    public void fielInvalidDataInFields(){

    }

    @When("choose Topic, Type, Language, Level dropdown menu")
    public void chooseAllDopDownMenu(){

    }

    @Then("pop up is shown with text '$msg'")
    public void checkPopUpMsg(String msg) {

    }

    @Then("clicks OK button")
    public void clickOKBtn() {

    }

    @Then("all fields are highlighted in red")
    public void areFieldsHighlighted(){

    }

    @Then("pop-up window '$msg' is shown")
    public void checkPopUpMsgInSubmitNewTalk(String msg){

    }

    @Then("data are not filled. System doesn't accept invalid data")
    public void checkInFieldDataLength(){

    }

    @Then("talk apears in grid and have 'New' status")
    public void checkNewTalk(){

    }

}
