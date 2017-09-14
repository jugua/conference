package com.epam.cm.steps.jbehave;

import com.epam.cm.core.mail.MailCatcherClient;
import com.epam.cm.core.utils.WebDriverSupport;
import com.epam.cm.dto.AttachFileDTO;
import com.epam.cm.dto.CredentialsDTO;
import com.epam.cm.dto.MyTalksDTO;
import com.epam.cm.steps.serenity.LoginPageSteps;
import com.epam.cm.steps.serenity.MyTalksPageSteps;

import net.thucydides.core.annotations.Steps;

import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.model.ExamplesTable;
import org.junit.Assert;

import java.awt.*;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

public class MyTalksDefinitionsSteps {

    @Steps
    LoginPageSteps loginPageSteps;
    @Steps
    MyTalksPageSteps myTalksPageSteps;

    MyTalksDTO myGlobalTalksDTO;
    CredentialsDTO user;
    AttachFileDTO attachFileDTO;
    MailCatcherClient mailCatcherClient = new MailCatcherClient();


    @Given("user logged as speaker accessing 'My Talks' page: $loginTable")
    public void loginAsUser(ExamplesTable table) {
        CredentialsDTO user = table.getRowsAs(CredentialsDTO.class).get(0);
        loginPageSteps.loginInOneStep(user);
        loginPageSteps.clickMyTalks();
    }

    @Given("user logged as organiser 'Talks' page: $loginTable")
    public void loginAsOrganiser(ExamplesTable table){
        CredentialsDTO user = table.getRowsAs(CredentialsDTO.class).get(0);
        loginPageSteps.loginInOneStep(user);
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
    public void checkStatus(ExamplesTable table) {
        WebDriverSupport.reloadPage();
        boolean replaceNamedParameters = true;
        String status = table.getRowAsParameters(0, replaceNamedParameters).valueAs("status", String.class);
        myGlobalTalksDTO.setStatus(status);
        Assert.assertThat(
                myTalksPageSteps.findRowWithStatus(myGlobalTalksDTO.getTitle()), is(myGlobalTalksDTO.getStatus()));
        myTalksPageSteps.clickFoundedTitle(myGlobalTalksDTO.getTitle());
    }

    @When("user log in as speaker accessing 'My Talks' page: $loginTable")
    public void loginAsSpeaker(ExamplesTable table) {
        loginAsUser(table);
    }

    @When("speaker clicks new created Talk: $table")
    public void speakerCheckStatus(ExamplesTable table){
        WebDriverSupport.reloadPage();
        boolean replaceNamedParameters = true;
        String status = table.getRowAsParameters(0, replaceNamedParameters).valueAs("status", String.class);
        myGlobalTalksDTO.setStatus(status);
        myTalksPageSteps.clickFoundedTitle(myGlobalTalksDTO.getTitle());
    }

    @When("organiser clicks speaker's name")
    public void clickSpeakersName(){
        myTalksPageSteps.clickFoundedSpeaker();
        myTalksPageSteps.justWait();
    }

    @When("fill 'Organiser`s comments' field: $ExampleForComment")
    public void fillCommentsField(ExamplesTable table) {
        boolean replaceNamedParameters = true;
        String comment = table.getRowAsParameters(0, replaceNamedParameters).valueAs("Organizer`s Comments", String.class);
        myGlobalTalksDTO.setComment(comment);
        myTalksPageSteps.typeOrgComments(myGlobalTalksDTO);
    }


    @Then("organiser cant type more than maximum allowed number of symbols in 'Organizer`s comments' field")
    public void organiserCantTypeMoreThanMaxAllowedSymbols() {
        Assert.assertTrue(myTalksPageSteps.organiserCantTypeMoreThanMaxAllowedSymbol());
    }


    @When("clicks reject button after filling comment: $table")
    public void rejectTalk(ExamplesTable table){
        boolean replaceNamedParameters = true;
        String comment = table.getRowAsParameters(0, replaceNamedParameters).valueAs("comment", String.class);
        myGlobalTalksDTO.setComment(comment);
        myTalksPageSteps.typeOrgComments(myGlobalTalksDTO);
        myTalksPageSteps.clickRejectBtn();
    }

    @When("clicks in progress button after filling comment: $table")
    public void inProgressTalk(ExamplesTable table){
        boolean replaceNamedParameters = true;
        String comment = table.getRowAsParameters(0, replaceNamedParameters).valueAs("comment", String.class);
        myGlobalTalksDTO.setComment(comment);
        myTalksPageSteps.typeOrgComments(myGlobalTalksDTO);
        myTalksPageSteps.clickInProgressBtn();
    }

    @When("clicks reject button without filling comment")
    public void rejectTalkWithoutcomment(){
        myTalksPageSteps.clickRejectBtn();
    }

    @When("clicks approve button after filling comment: $table")
    public void approveTalk(ExamplesTable table){
        boolean replaceNamedParameters = true;
        String comment = table.getRowAsParameters(0, replaceNamedParameters).valueAs("comment", String.class);
        myGlobalTalksDTO.setComment(comment);
        myTalksPageSteps.typeOrgComments(myGlobalTalksDTO);
        myTalksPageSteps.clickApproveBtn();
    }

    @When("user clicks on 'Submit New Talk' button")
    public void clickSubmitNewTalkBtn() {
        myTalksPageSteps.clickSubmitNewTalkBtn();
    }

    @When("clicks on the pencil icon and choose file: $table")
    public void userChooseFileForAttachment(ExamplesTable table) throws AWTException {
        boolean replaceNamedParameters = true;
        String pathToFile = table.getRowAsParameters(0, replaceNamedParameters).valueAs("filePath", String.class);
        attachFileDTO = new AttachFileDTO();
        attachFileDTO.setFilePath(pathToFile);
        attachFileDTO.setFileNameFromPath(pathToFile);
        myTalksPageSteps.typeFullPathIntoAttachField(pathToFile);

    }

    @When("user hovers attachment icon over")
    public void userHoverAttachmentIconOver(){
        myTalksPageSteps.hoverAttachIcon();
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

    @Then("hint is displayed: \"$msg\"")
    public void areAttachHintDisplayed(String msg){
        Assert.assertThat(myTalksPageSteps.getAttachHintText().replaceAll("(\r\n|\n\r|\r|\n\\s+)", " "), is(msg.replaceAll("(\r\n|\n\r|\r|\n\\s+)", " ")));
        Assert.assertTrue(myTalksPageSteps.isFullTextVisible());
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

    @Then("all fields are read-only for speaker")
    public void areFieldsReadOnlyForSpeaker() {
        Assert.assertTrue(myTalksPageSteps.areFieldsReadOnlyForSpeaker());
    }

    @Then("all fields are read-only for organiser")
    public void areFieldsReadOnlyForOrganiser() {
        Assert.assertTrue(myTalksPageSteps.areFieldsReadOnlyForOrganiser());
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
        String status = table.getRowAsParameters(0, replaceNamedParameters).valueAs("status", String.class);
        myGlobalTalksDTO.setStatus(status);
        WebDriverSupport.reloadPage();
        Assert.assertThat(
                myTalksPageSteps.findRowWithStatus(myGlobalTalksDTO.getTitle()), is(myGlobalTalksDTO.getStatus()));
    }

    @Then("in progress status is shown: $table")
    public void checkInProgressStatus(ExamplesTable table){
        boolean replaceNamedParameters = true;
        String status = table.getRowAsParameters(0, replaceNamedParameters).valueAs("status", String.class);
        myGlobalTalksDTO.setStatus(status);
        WebDriverSupport.reloadPage();
        Assert.assertThat(
                myTalksPageSteps.findRowWithStatus(myGlobalTalksDTO.getTitle()), is(myGlobalTalksDTO.getStatus()));
    }

    @Then("approve status is shown: $table")
    public void checkApproveStatus(ExamplesTable table){
        boolean replaceNamedParameters = true;
        String status = table.getRowAsParameters(0, replaceNamedParameters).valueAs("status", String.class);
        myGlobalTalksDTO.setStatus(status);
        WebDriverSupport.reloadPage();
        Assert.assertThat(
                myTalksPageSteps.findRowWithStatus(myGlobalTalksDTO.getTitle()), is(myGlobalTalksDTO.getStatus()));
    }

    @Then("message asking me to specify rejection reason: $table")
    public void isRejectionReasonMessagePresented(ExamplesTable table){
        boolean replaceNamedParameters = true;
        String message = table.getRowAsParameters(0, replaceNamedParameters).valueAs("message", String.class);
        myGlobalTalksDTO.setNoRejectionReasonErrMessage(message);
        Assert.assertThat(myTalksPageSteps.getNoRejectionReasonErrMessage(),is(myGlobalTalksDTO.getNoRejectionReasonErrMessage()));
    }

    @Then("all fields except Organizer's Comments box are read-only")
    public void areFieldsReadOnly() {
        Assert.assertTrue(myTalksPageSteps.areFieldsExceptCommentReadOnly());
    }

    @Then("email was sent to users email : $table")
    public void checkEmailForTalk(ExamplesTable table){
        myTalksPageSteps.justWait();
        user = table.getRowsAs(CredentialsDTO.class).get(0);
        System.out.println(mailCatcherClient.getLastEmail());
        Assert.assertThat(mailCatcherClient.getLastEmail().getRecipients().get(0),
                containsString(user.getEmail().toLowerCase()));

    }

    @Then("with subject '$sbj'")
    public void checkSbj(String subject){
        Assert.assertThat(mailCatcherClient.getLastEmail().getSubject(), is(subject));

    }

    @Then("body contains '$body'")
    public void checkNewTalkEmailBody(String msg1){
        Assert.assertThat(mailCatcherClient
                        .getEmailById(mailCatcherClient.getLastEmail().getId(), MailCatcherClient.ResponseType.HTML).toString(),
                containsString(msg1));
    }

    @Then("speakers info is shown with read-only fields")
    public void checkSpeakersViewReadOnlyFields(){
        Assert.assertTrue(myTalksPageSteps.areViewFieldReadOnly());
    }

    @Then("file is attached")
    public void isFileAttached(){
       Assert.assertTrue(myTalksPageSteps.isFileAttached(attachFileDTO));
    }

    @Then("max size error message is displayed: \"$msg\"")
    public void isMaxSizeErrorMsgDisplayed(String msg){
        Assert.assertTrue(myTalksPageSteps.isMaxSizeErrorMsgDisplayed(msg));
    }

    @Then("format error message is displayed: \"$msg\"")
    public void isFormatErrorMsgDisplayed(String msg){
        Assert.assertTrue(myTalksPageSteps.isFormatErrorMsgDisplayed(msg));
    }
}
