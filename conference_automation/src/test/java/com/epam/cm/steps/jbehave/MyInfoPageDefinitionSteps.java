package com.epam.cm.steps.jbehave;

import com.epam.cm.dto.CredentialsDTO;
import com.epam.cm.dto.MyInfoFieldsDTO;
import com.epam.cm.steps.serenity.LoginPageSteps;
import com.epam.cm.steps.serenity.MyInfoPageSteps;

import net.thucydides.core.annotations.Steps;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.model.ExamplesTable;
import org.junit.Assert;

public class MyInfoPageDefinitionSteps {
    @Steps
    MyInfoPageSteps myInfoSteps;
    @Steps
    LoginPageSteps loginPageSteps;

    @Given("user logged as speaker: $loginTable")
    public void userLoggedAsSpeaker(ExamplesTable table) {

        CredentialsDTO user = table.getRowsAs(CredentialsDTO.class).get(0);
        loginPageSteps.loginInOneStep(user);
    }

    @When("user clicks My Info option in account menu")
    public void userClickMyInfoOption() throws InterruptedException {
        myInfoSteps.clickMyInfoOption();
    }

    @Then("my info page is opened with active My Info tab")
    public void myInfoTabIsActive() {
        Assert.assertTrue(myInfoSteps.isMyInfoTabActive());
    }

    @Then("all the following fields are empty Short Bio, Job Title, Company, Past Conferences, Linked In, Twitter, Facebook, Blog, Additional info")
    public void areAllFieldsEmpty() {
        Assert.assertTrue(myInfoSteps.areAllFieldsEmpty());
    }

    @When("user fill valid info in fields: $examplesTable")
    public void userFillValidInfo(final ExamplesTable myInfoTable) {
        MyInfoFieldsDTO myInfoDTO = parseExamplesTable(myInfoTable);
        myInfoSteps.userFillValidInfo(myInfoDTO);
    }

    @When("user go away from my info tab without clicking save button")
    public void userGoAwayFromMyInfoTab() {
        myInfoSteps.userGoAwayFromMyInfoTab();
    }

    @When("switch to MyTalks")
    public void userSwitchToMyTalks() {
        myInfoSteps.switchToMyTalks();
    }

    @When("user switch back to MyInfo tab")
    public void userSwitchToMyInfoTab() {
        myInfoSteps.switchToMyInfo();
    }

    @Then("successfully saved popup is shown")
    public void checkInformationSavedSuccessfullyPopUp() {
        myInfoSteps.userClickSaveButton();
        Assert.assertTrue(myInfoSteps.isInformationSavedSuccessfullyPopUp());
    }

    @Then("information saved successfully: $examplesTable")
    public void informationSavedSuccessfully(final ExamplesTable myInfoTable) {
        MyInfoFieldsDTO myInfoDTO = parseExamplesTable(myInfoTable);
        Assert.assertTrue(myInfoSteps.isInformationSavedSuccessfully(myInfoDTO));
    }

    @Then("user logout")
    public void userLogout() {
        myInfoSteps.userLogout();
    }

    @Then("attention pop up is shown")
    public void isAttentionPopUpShown() {
        Assert.assertTrue(myInfoSteps.isAttentionPopUpShown());
    }

    @Then("error popup is shown")
    public void isErrorPopUpShown() {
        myInfoSteps.userClickSaveButton();
        Assert.assertTrue(myInfoSteps.isErrorPopUpShown());
    }

    @Then("user can`t enter/type more than the maximum allowed characters: $examplesTable")
    public void userCantTypeMoreThanMaxAllowedCharacters(final ExamplesTable myInfoTable) {
        MyInfoFieldsDTO myInfoDTO = parseExamplesTable(myInfoTable);
        Assert.assertTrue(myInfoSteps.userCantTypeMoreThanMaxAllowedCharacters(myInfoDTO));
    }

    @Then("MyTalks tab is active")
    public void isMyTalksTabActive() {
        Assert.assertTrue(myInfoSteps.isMyTalksTabActive());
    }

    @Then("MyInfo tab is active")
    public void isMyInfoTalksActive() {
        Assert.assertTrue(myInfoSteps.isMyInfoTabActive());
    }

    private MyInfoFieldsDTO parseExamplesTable(ExamplesTable myInfoTable) {
        boolean replaceNamedParameters = true;
        String shortBio = myInfoTable.getRowAsParameters(0, replaceNamedParameters).valueAs("shortBio", String.class);
        String jobTitle = myInfoTable.getRowAsParameters(0, replaceNamedParameters).valueAs("jobTitle", String.class);
        String company = myInfoTable.getRowAsParameters(0, replaceNamedParameters).valueAs("company", String.class);
        String pastConferences = myInfoTable.getRowAsParameters(0, replaceNamedParameters).valueAs("pastConferences",
                String.class);
        String linkedIn = myInfoTable.getRowAsParameters(0, replaceNamedParameters).valueAs("linkedIn", String.class);
        String twitter = myInfoTable.getRowAsParameters(0, replaceNamedParameters).valueAs("twitter", String.class);
        String facebook = myInfoTable.getRowAsParameters(0, replaceNamedParameters).valueAs("facebook", String.class);
        String blog = myInfoTable.getRowAsParameters(0, replaceNamedParameters).valueAs("blog", String.class);
        String additionalInfo = myInfoTable.getRowAsParameters(0, replaceNamedParameters).valueAs("additionalInfo",
                String.class);

        return new MyInfoFieldsDTO() {
            {
                setShortBio(shortBio);
                setJobTitle(jobTitle);
                setCompany(company);
                setPastConferences(pastConferences);
                setLinkedIn(linkedIn);
                setTwitter(twitter);
                setFacebook(facebook);
                setBlog(blog);
                setAdditionalInfo(additionalInfo);
            }
        };
    }
}
