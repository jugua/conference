package com.epam.cm.steps.jbehave;

import com.epam.cm.dto.CredentialsDTO;
import com.epam.cm.dto.MyTalksDTO;
import com.epam.cm.steps.serenity.LoginPageSteps;
import com.epam.cm.steps.serenity.MyTalksPageSteps;

import net.thucydides.core.annotations.Steps;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.model.ExamplesTable;
import org.junit.Assert;

import static org.hamcrest.Matchers.is;

public class MyTalksDefinitionsSteps {

    @Steps
    LoginPageSteps loginPageSteps;
    @Steps
    MyTalksPageSteps myTalksPageSteps;

    MyTalksDTO myGlobalTalksDTO;


    @Given("user logged as speaker accessing 'My Talks' page: $loginTable")
    public void loginAsUser(ExamplesTable table) {
        CredentialsDTO user = table.getRowsAs(CredentialsDTO.class).get(0);
        loginPageSteps.unsignedUserInHomePage();
        loginPageSteps.clickOnAccountMenu();
        loginPageSteps.typeLoginAndPassword(user);
        loginPageSteps.clickSignInButton();
        loginPageSteps.clickMyTalks();
    }

    @Given("user logged as organiser 'Talks' page: $loginTable")
    public void loginAsOrganiser(ExamplesTable table){
        CredentialsDTO user = table.getRowsAs(CredentialsDTO.class).get(0);
        loginPageSteps.unsignedUserInHomePage();
        loginPageSteps.clickOnAccountMenu();
        loginPageSteps.typeLoginAndPassword(user);
        loginPageSteps.clickSignInButton();
        loginPageSteps.clickTalksBtnAsOrg();
    }

    @Given("creates new Talk: $newTalk")
    public void userCreatesNewTalk(ExamplesTable table){
        myTalksPageSteps.clickSubmitNewTalkBtn();
        boolean replaceNamedParameters = true;
        String title = table.getRowAsParameters(0, replaceNamedParameters).valueAs("title", String.class);
        String description = table.getRowAsParameters(0, replaceNamedParameters).valueAs("description", String.class);
        String additionalInfo = table.getRowAsParameters(0, replaceNamedParameters).valueAs("additionalInfo",
                String.class);
        String topic = table.getRowAsParameters(0, replaceNamedParameters).valueAs("topic",
                String.class);
        String type = table.getRowAsParameters(0,replaceNamedParameters).valueAs("type",
                String.class);
        String language = table.getRowAsParameters(0,replaceNamedParameters).valueAs("language",
                String.class);
        String level = table.getRowAsParameters(0,replaceNamedParameters).valueAs("level",
                String.class);

        myGlobalTalksDTO = new MyTalksDTO() {
            {
                setTitle(title);
                setDescription(description);
                setAdditionalInfo(additionalInfo);
                setTopic(topic);
                setType(type);
                setLanguage(language);
                setLevel(level);
            }
        };

        myTalksPageSteps.typeTitle(myGlobalTalksDTO);
        myTalksPageSteps.typeDescription(myGlobalTalksDTO);
        myTalksPageSteps.typeAdditionalInfo(myGlobalTalksDTO);
        myTalksPageSteps.chooseTopic(myGlobalTalksDTO);
        myTalksPageSteps.chooseType(myGlobalTalksDTO);
        myTalksPageSteps.chooseLanguage(myGlobalTalksDTO);
        myTalksPageSteps.chooseLevel(myGlobalTalksDTO);
        myTalksPageSteps.clickBigPopUpSubmitBtn();
        System.out.println(myGlobalTalksDTO.getTitle());
        loginPageSteps.logout();

    }

    @When("organiser clicks new created Talk: $table")
    public void checkStatus(ExamplesTable table){
        boolean replaceNamedParameters = true;
        String title = table.getRowAsParameters(0, replaceNamedParameters).valueAs("title", String.class);
        String status = table.getRowAsParameters(0, replaceNamedParameters).valueAs("status", String.class);

        myGlobalTalksDTO = new MyTalksDTO() {
            {
                setStatus(status);
                setTitle(title);
            }
        };

        Assert.assertThat(
                myTalksPageSteps.findRowWithStatus(myGlobalTalksDTO.getTitle()), is(myGlobalTalksDTO.getStatus()));
        myTalksPageSteps.clickFoundedTitle(myGlobalTalksDTO.getTitle());
    }

    @When("clicks reject button after filling comment: $table")
    public void rejectTalk(ExamplesTable table){
        boolean replaceNamedParameters = true;
        String comment = table.getRowAsParameters(0, replaceNamedParameters).valueAs("comment", String.class);
        myGlobalTalksDTO = new MyTalksDTO() {
            {
                setComment(comment);
            }
        };

        myTalksPageSteps.typeOrgComments(myGlobalTalksDTO);
        myTalksPageSteps.clickRejectBtn();

    }

    @When("user clicks on 'Submit New Talk' button")
    public void clickSubmitNewTalkBtn() {
        myTalksPageSteps.clickSubmitNewTalkBtn();
    }

    @When("clicks 'Submit' button")
    public void clickSubmitBtn() {
        myTalksPageSteps.clickBigPopUpSubmitBtn();
    }

    @When("user fills data in 'Title','Description' and 'Additional Info': $exampleTable")
    public void fillInvalidDataInFields(ExamplesTable table) {
        boolean replaceNamedParameters = true;
        String title = table.getRowAsParameters(0, replaceNamedParameters).valueAs("title", String.class);
        String description = table.getRowAsParameters(0, replaceNamedParameters).valueAs("description", String.class);
        String additionalInfo = table.getRowAsParameters(0, replaceNamedParameters).valueAs("additionalInfo",
                String.class);

        MyTalksDTO myTalksDTO = new MyTalksDTO() {
            {
                setTitle(title);
                setDescription(description);
                setAdditionalInfo(additionalInfo);
            }
        };

        myTalksPageSteps.typeTitle(myTalksDTO);
        myTalksPageSteps.typeDescription(myTalksDTO);
        myTalksPageSteps.typeAdditionalInfo(myTalksDTO);

    }

    @When("choose Topic, Type, Language, Level dropdown menu: $exTable")
    public void chooseAllDropDownMenu(ExamplesTable table) {
        boolean replaceNamedParameters = true;
        String topic = table.getRowAsParameters(0, replaceNamedParameters).valueAs("topic",
                String.class);
        String type = table.getRowAsParameters(0,replaceNamedParameters).valueAs("type",
                String.class);
        String language = table.getRowAsParameters(0,replaceNamedParameters).valueAs("language",
                String.class);
        String level = table.getRowAsParameters(0,replaceNamedParameters).valueAs("level",
                String.class);

        MyTalksDTO myTalksDTO = new MyTalksDTO(){
            {
                setTopic(topic);
                setType(type);
                setLanguage(language);
                setLevel(level);
            }
        };

        myTalksPageSteps.chooseTopic(myTalksDTO);
        myTalksPageSteps.chooseType(myTalksDTO);
        myTalksPageSteps.chooseLanguage(myTalksDTO);
        myTalksPageSteps.chooseLevel(myTalksDTO);
    }

    @When("clicks 'Exit' button")
    public void clickBigPopUpCancel() {
        myTalksPageSteps.clickBigPopUpCancelBtn();
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
        Assert.assertThat(msg, is(myTalksPageSteps.getEmptyFieldsPopUpText()));
    }

    @Then("data are not filled. System doesn't accept invalid data: $exampleTable")
    public void checkInFieldDataLength(ExamplesTable table) {

        boolean replaceNamedParameters = true;
        String title = table.getRowAsParameters(0, replaceNamedParameters).valueAs("title", String.class);
        String description = table.getRowAsParameters(0, replaceNamedParameters).valueAs("description", String.class);
        String additionalInfo = table.getRowAsParameters(0, replaceNamedParameters).valueAs("additionalInfo",
                String.class);

        MyTalksDTO myTalksDTO = new MyTalksDTO() {
            {
                setTitle(title);
                setDescription(description);
                setAdditionalInfo(additionalInfo);
            }
        };

        Assert.assertTrue(myTalksPageSteps.userCantTypeMoreThenMaxTitle(myTalksDTO));
        Assert.assertTrue(myTalksPageSteps.userCantTypeMoreThenMaxDescription(myTalksDTO));
        Assert.assertTrue(myTalksPageSteps.userCantTypeMoreThenMaAdditionalInfo(myTalksDTO));
    }

    @Then("talk appears in grid and have 'New' status")
    public void checkNewTalk() {

    }

    @Then("info msg is shown saying '$firstMsg', '$secondMsg', '$thirdMsg'")
    public void checkInfoMsgs(String firstMsg, String secondMsg, String thirdMsg) {
        Assert.assertThat(firstMsg, is(myTalksPageSteps.getFirstInfoMsg()));
        Assert.assertThat(secondMsg, is(myTalksPageSteps.getSecondInfoMsg()));
        Assert.assertThat(thirdMsg, is(myTalksPageSteps.getThirdInfoMsg()));
    }

    @Then("click 'Yes' button")
    public void clickYesBtn() {
        myTalksPageSteps.clickYesInfoBtn();
    }

    @Then("reject status is shown: $table")
    public void checkRejectStatus(ExamplesTable table){
        boolean replaceNamedParameters = true;
        String title = table.getRowAsParameters(0, replaceNamedParameters).valueAs("title", String.class);
        String status = table.getRowAsParameters(0, replaceNamedParameters).valueAs("status", String.class);

        myGlobalTalksDTO = new MyTalksDTO() {
            {
                setStatus(status);
                setTitle(title);
            }
        };
        Assert.assertThat(
                myTalksPageSteps.findRowWithStatus(myGlobalTalksDTO.getTitle()), is(myGlobalTalksDTO.getStatus()));

    }
}
