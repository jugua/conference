package com.epam.cm.pages;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.datatransfer.StringSelection;
import java.sql.Driver;

import com.epam.cm.dto.AttachFileDTO;
import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;

import org.openqa.selenium.WebDriver;

import static java.util.concurrent.TimeUnit.SECONDS;

public class MyTalksPage extends AnyPage {

    private final String RowByName = "//*[@class='data-table__row ng-scope']/../div[contains(.,'%s')]";
    private final String STATUS = "//*[@class='data-table__row ng-scope']/../div[contains(.,'%s')]/div[@class='data-table__column data-table__column_status-talk ng-binding']";
    private final String TITLE = "//*[@class='data-table__row ng-scope']/div[contains(.,'%s')]/a";
    private final String SPEAKER = "//*[@class='data-table__row ng-scope']/div/a[contains(.,'Tester Tester')]";

    @FindBy(xpath = "//*[@class='my-talks__header']/a")
    WebElementFacade submitNewTalkBtn;
    // no my info
    @FindBy(xpath = "//control-popup/div/div")
    WebElementFacade popUpMyInfoNotField;
    @FindBy(xpath = "//*[@class='pop-up']/p")
    WebElementFacade errorMsgMyInfoNotFieldText;
    @FindBy(xpath = "//*[@class='btn pop-up__button']")
    WebElementFacade errorMsgMyInfoSubmitBtn;
    @FindBy(xpath = "//*[@class='btn pop-up__button']")
    WebElementFacade okMyInfoNotFieldBtn;
    @FindBy(xpath = "//*[@class='pop-up-wrapper']/div/button[@class='pop-up__close']")
    WebElementFacade cancelMyInfoBtn;

    // big pop up invalid
    @FindBy(xpath = "//*[@class='btn pop-up__button']")
    WebElementFacade newTalkBigPopUpSubmitBtn;
    @FindBy(xpath = "//*[@class='btn btn_right btn_small']")
    WebElementFacade okBtnAfterInvalidData;
    @FindBy(xpath = "//*[@class='pop-up-wrapper']/div/p")
    WebElementFacade alertMsg;
    @FindBy(xpath = "//*[@class='pop-up-wrapper']/div/p[1]")
    WebElementFacade firstNotSavingMsg;
    @FindBy(xpath = "//*[@class='pop-up-wrapper']/div/p[2]")
    WebElementFacade secondNotSavingMsg;
    @FindBy(xpath = "//*[@class='pop-up-wrapper']/div/p[3]")
    WebElementFacade thirdNotSavingMsg;
    @FindBy(xpath = "//*[@class='pop-up-button-wrapper']/button[@class='btn pop-up__button']")
    WebElementFacade yesNotSavingBtn;
    @FindBy(xpath = "//*[@class='pop-up-button-wrapper']/button[@class='btn btn__cancel pop-up__button']")
    WebElementFacade noNotSavingBtn;
    @FindBy(xpath = "//newtalk/div[3]/div/button")
    WebElementFacade cancelInvalidDataBtn;
    @FindBy(xpath = "//*[@class='pop-up-wrapper']/div/button")
    WebElementFacade cancelBigPopUpBtn;

    // big pop-up fields
    @FindBy(xpath = "//*[@id='new-talk-title']")
    WebElementFacade titleField;
    @FindBy(xpath = "//*[@id='new-talk-desc']")
    WebElementFacade descriptionField;
    @FindBy(xpath = "//*[@class='pop-up pop-up_big']/form/select[1]")
    WebElementFacade topicDropDown;
    @FindBy(xpath = "//*[@class='pop-up pop-up_big']/form/select[2]")
    WebElementFacade typeDropDown;
    @FindBy(xpath = "//*[@class='pop-up pop-up_big']/form/select[3]")
    WebElementFacade languageDropDown;
    @FindBy(xpath = "//*[@class='pop-up pop-up_big']/form/select[4]")
    WebElementFacade levelDropDown;
    @FindBy(xpath = "//*[@id='new-talk-add-inf']")
    WebElementFacade additionalInfoField;
    @FindBy(xpath = "//md-icon[@class = 'icon icon_info material-icons']")
    WebElementFacade attachIcon;
    @FindBy(xpath = "//label[@ng-class = '$ctrl.fileLabelClass']")
    WebElementFacade pencilIcon;

    // filters
    @FindBy(xpath = "//*[@id='my-talk-status']")
    WebElementFacade filterByStatus;
    @FindBy(xpath = "//*[@id='my-talk-topic']")
    WebElementFacade filterByTopic;
    @FindBy(xpath = "//*[@ng-model='$ctrl.myDate_start']")
    WebElementFacade filterByStartedDate;
    @FindBy(xpath = "//*[@ng-model='$ctrl.myDate_finish']")
    WebElementFacade filterByFinishDate;
    @FindBy(xpath = "//*[@class='my-talk-settings__button-wrapper']/input[@value='apply']")
    WebElementFacade myTalksApplyBtn;
    @FindBy(xpath = "//*[@class='my-talk-settings__button-wrapper']/input[@value='reset']")
    WebElementFacade myTalksResetBtn;

    // speaker
    @FindBy(xpath = "//input[@ng-model='$ctrl.obj.title']")
    WebElementFacade speakerTitleField;
    @FindBy(xpath = "//textarea[@ng-model='$ctrl.obj.description']")
    WebElementFacade speakerDescriptionField;
    @FindBy(xpath = "//input[@ng-model='$ctrl.obj.topic']")
    WebElementFacade speakerTopicField;
    @FindBy(xpath = "//input[@ng-model='$ctrl.obj.type']")
    WebElementFacade speakerTypeField;
    @FindBy(xpath = "//input[@ng-model='$ctrl.obj.lang']")
    WebElementFacade speakerLanguageField;
    @FindBy(xpath = "//input[@ng-model='$ctrl.obj.level']")
    WebElementFacade speakerLevelField;
    @FindBy(xpath = "//textarea[@ng-model='$ctrl.obj.addon']")
    WebElementFacade speakerAdditionalInfoField;
    @FindBy(xpath = "//input[@type = 'file']")
    WebElementFacade attachFileField;
    @FindBy(xpath = "//span[@class = 'file-upload__filename ng-binding']")
    WebElementFacade filePathInAttachInput;
    @FindBy(xpath = "//span[@ng-show='$ctrl.talkForm.$error.maxSize']")
    WebElementFacade sizeErrorField;
    @FindBy(xpath = "//span[@ng-show='$ctrl.talkForm.$error.pattern']")
    WebElementFacade formatErrorField;

    // organiser
    @FindBy(xpath = "//*[@class='data-table__row ng-scope']/descendant::div[contains(.,'New')]")
    WebElementFacade tableRow;
    @FindBy(xpath = "//*[@name='comment']")
    WebElementFacade orgComments;
    @FindBy(xpath = "//input[@id='new-talk-title']")
    WebElementFacade organiserTitleField;
    @FindBy(xpath = "//textarea[@ng-model='$ctrl.talk.description']")
    WebElementFacade organiserDescriptionField;
    @FindBy(xpath = "//input[@ng-model='$ctrl.talk.topic']")
    WebElementFacade organiserTopicField;
    @FindBy(xpath = "//input[@ng-model='$ctrl.talk.type']")
    WebElementFacade organiserTypeField;
    @FindBy(xpath = "//input[@ng-model='$ctrl.talk.lang']")
    WebElementFacade organiserLanguageField;
    @FindBy(xpath = "//input[@ng-model='$ctrl.talk.level']")
    WebElementFacade organiserLevelField;
    @FindBy(xpath = "//textarea[@ng-model='$ctrl.talk.addon']")
    WebElementFacade organiserAdditionalInfoField;

    // view
    @FindBy(xpath = "//*[@id='my-info-bio']")
    WebElementFacade shortBio;
    @FindBy(xpath = "//*[@ng-model='$ctrl.user.job']")
    WebElementFacade jobTitle;
    @FindBy(xpath = "//*[@ng-model='$ctrl.user.company']")
    WebElementFacade company;
    @FindBy(xpath = "//*[@id='my-info-linkedin-past-conferences']")
    WebElementFacade pastConferences;
    @FindBy(xpath = "//*[@ng-model='$ctrl.user.mail']")
    WebElementFacade speakersEmail;
    @FindBy(xpath = "//*[@ng-model='$ctrl.user.linkedin']")
    WebElementFacade speakersLinkedIn;
    @FindBy(xpath = "//*[@ng-model='$ctrl.user.twitter']")
    WebElementFacade speakersTwitter;
    @FindBy(xpath = "//*[@ng-model='$ctrl.user.facebook']")
    WebElementFacade speakersFacebook;
    @FindBy(xpath = "//*[@ng-model='$ctrl.user.blog']")
    WebElementFacade speakersBlog;
    @FindBy(xpath = "//*[@ng-model='$ctrl.user.info']")
    WebElementFacade speakersAdditionalInfo;
    @FindBy(xpath = "//*[@class='btn talks-user-info-popup__button']")
    WebElementFacade viewCloseBtn;

    // approve
    @FindBy(xpath = "//button[@ng-click='$ctrl.approve()']")
    WebElementFacade approveBtn;

    // reject
    @FindBy(xpath = "//button[@ng-click='$ctrl.reject()']")
    WebElementFacade rejectBtn;
    @FindBy(xpath = "//span[@class='field-error']")
    WebElementFacade reviewTalk;

    // inProgress
    @FindBy(xpath = "//button[@ng-click='$ctrl.progress()']")
    WebElementFacade inProgressBtn;

    public MyTalksPage(WebDriver driver) {
        super(driver);
    }

    public WebElementFacade findRow(String name) {
        WebElementFacade row = find(By.xpath(String.format(RowByName, name)));
        return row;
    }

    public String getStatus(String name) {
        return find(By.xpath(String.format(STATUS, name))).getText();
    }

    public void clickSpeaker() {
        WebElementFacade speaker = find(By.xpath(String.format(SPEAKER)));
        speaker.click();
    }

    public void clickTitle(String name) {
        WebElementFacade titleTable = find(By.xpath(String.format(TITLE, name)));
        titleTable.click();
    }

    public void clickRejectBtn() {
        rejectBtn.withTimeoutOf(5, SECONDS).waitUntilClickable().click();
    }

    public void clickSubmitNewTalk() {
        submitNewTalkBtn.click();
    }

    public String getErrorNoMyInfoMsgText() {
        return errorMsgMyInfoNotFieldText.getText();
    }

    public void clickCancelNoMyInfoPopUpBtn() {
        cancelMyInfoBtn.click();
    }

    public void clickBigPopUpSbmBtn() {
        //waitABit(5000);
       // newTalkBigPopUpSubmitBtn.waitUntilClickable();
        newTalkBigPopUpSubmitBtn.withTimeoutOf(5, SECONDS).waitUntilClickable().click();
    }

    public void clickErrorMyInfoOkBtn() {
        okMyInfoNotFieldBtn.click();
    }

    public String getEmptyFieldsAlertMsg() {
        return alertMsg.getText();
    }

    public String getFirstInfoMsgText() {
        return firstNotSavingMsg.getText();
    }

    public String getSecondInfoMsgText() {
        return secondNotSavingMsg.getText();
    }

    public String getThirdInfoMsgText() {
        return thirdNotSavingMsg.getText();
    }

    public void clickYesBtn() {
        yesNotSavingBtn.click();
    }

    public void clickNoBtn() {
        noNotSavingBtn.click();
    }

    public void clickCancelBigPopUpBtn() {
        cancelBigPopUpBtn.click();
    }

    public void clickCancelInvalidData() {
        cancelInvalidDataBtn.click();
    }

    public void setOrgComments(String comments) {
        orgComments.clear();
        orgComments.type(comments);
    }

    public void setTitleField(String title) {
        titleField.clear();
        titleField.type(title);
    }

    public int getTitleLength() {
        return titleField.getValue().length();
    }

    public boolean isTitleHighlighted() {
        return titleField.getAttribute("class").contains("invalid");
    }

    public void setDescriptionField(String description) {
        descriptionField.clear();
        descriptionField.type(description);
    }

    public int getDescriptionLength() {
        return descriptionField.getValue().length();
    }

    public boolean isDescriptionHighlighted() {
        return descriptionField.getAttribute("class").contains("invalid");
    }

    public void setAdditionalInfoField(String additionalInfo) {
        additionalInfoField.clear();
        additionalInfoField.type(additionalInfo);
    }

    public int getAdditionalInfoLength() {
        return additionalInfoField.getValue().length();
    }

    public boolean isAdditionalInfoHighlighted() {
        return additionalInfoField.getAttribute("class").contains("invalid");
    }

    public void selectTopic(String topic) {
        topicDropDown.selectByIndex(Integer.parseInt(topic));
    }

    public boolean isTopicHighLighted() {
        return topicDropDown.getAttribute("class").contains("invalid");
    }

    public void selectType(String type) {
        typeDropDown.selectByIndex(Integer.parseInt(type));
    }

    public boolean isTypeHighlighted() {
        return typeDropDown.getAttribute("class").contains("invalid");
    }

    public void selectLanguage(String language) {
        languageDropDown.selectByIndex(Integer.parseInt(language));
    }

    public boolean isLanguageHighlighted() {
        return languageDropDown.getAttribute("class").contains("invalid");
    }

    public void selectLevel(String level) {
        levelDropDown.selectByIndex(Integer.parseInt(level));
    }

    public boolean isLevelHighlighted() {
        return levelDropDown.getAttribute("class").contains("invalid");
    }

    public void clickInProgressBtn() {
        inProgressBtn.withTimeoutOf(5, SECONDS).waitUntilClickable().click();
    }

    public void clickApproveBtn() {
        approveBtn.withTimeoutOf(5, SECONDS).waitUntilClickable().click();
    }

    public String getNoRejectionReasonErrMessage() {
        String noRejectionReasonErrMessage = reviewTalk.getText();
        return noRejectionReasonErrMessage;
    }

    public int getOrgCommentsActualLength() {
        return orgComments.getValue().length();
    }

    public void setOrganiserTitleField(String string) {
        organiserTitleField.sendKeys(string);
    }

    public void setOrganiserDescriptionField(String string) {
        organiserDescriptionField.sendKeys(string);
    }

    public void setOrganiserTopic(String string) {
        organiserTopicField.sendKeys(string);
    }

    public void setOrganiserType(String string) {
        organiserTypeField.sendKeys(string);
    }

    public void setOrganiserLanguage(String string) {
        organiserLanguageField.sendKeys(string);
    }

    public void setOrganiserLevel(String string) {
        organiserLevelField.sendKeys(string);
    }

    public void setOrganiserAdditionalInfoField(String string) {
        organiserAdditionalInfoField.sendKeys(string);
    }

    public String getOrganiserTitleValue() {
        return organiserTitleField.getValue();
    }

    public String getOrganiserDescriptionValue() {
        return organiserDescriptionField.getValue();
    }

    public String getOrganiserTopicValue() {
        return organiserTopicField.getValue();
    }

    public String getOrganiserTypeValue() {
        return organiserTypeField.getValue();
    }

    public String getOrganiserLanguageValue() {
        return organiserLanguageField.getValue();
    }

    public String getOrganiserLevelValue() {
        return organiserLevelField.getValue();
    }

    public String getOrganiserAdditionalInfoValue() {
        return organiserAdditionalInfoField.getValue();
    }

    public boolean areFieldsExceptCommentReadOnly() {
        if (!organiserTitleField.isEnabled() && !organiserDescriptionField.isEnabled()
                && !organiserTopicField.isEnabled() && !organiserTypeField.isEnabled()
                && !organiserLanguageField.isEnabled() && !organiserLevelField.isEnabled()
                && !organiserAdditionalInfoField.isEnabled()) {
            return true;
        }
        return false;
    }

    public boolean areFieldInViewReadOnly() {
        if (!shortBio.isEnabled() && !jobTitle.isEnabled() && !company.isEnabled() && !pastConferences.isEnabled()
                && !speakersEmail.isEnabled() && !speakersLinkedIn.isEnabled() && !speakersTwitter.isEnabled()
                && !speakersFacebook.isEnabled() && !speakersBlog.isEnabled() && !speakersAdditionalInfo.isEnabled())
            return true;
        return false;
    }

    public void waitABitLol() {
        waitABit(3000);
    }

    public boolean areFieldsReadOnlyForOrganiser() {
        if(areFieldsExceptCommentReadOnly() &&
                !orgComments.isEnabled()){
            return true;
        }
        return false;
    }

    public boolean areFieldsReadOnlyForSpeaker() {
        if (!speakerTitleField.isEnabled() && !speakerDescriptionField.isEnabled()
                && !speakerTopicField.isEnabled() && !speakerTypeField.isEnabled()
                && !speakerLanguageField.isEnabled() && !speakerLevelField.isEnabled()
                && !speakerAdditionalInfoField.isEnabled() && !orgComments.isEnabled()) {
            return true;
        }
        return false;
    }

    public void hoverAttachIcon() {
        moveToElementAction(attachIcon);
    }

    public String getAttachHintText() {
        return attachIcon.getAttribute("aria-label");

    }

    public boolean isFullTextVisible() {
        return attachIcon.isCurrentlyVisible();
    }

    public void typeFullPathIntoAttachField(String pathToFile) throws AWTException {
        pencilIcon.click();
        setClipboardData(pathToFile);
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.delay(1000);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.delay(1000);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        robot.delay(1000);
       // waitForFileUpload(pathToFile);
       // filePathInAttachInput.waitForCondition((pathToFile.contains(filePathInAttachInput.getValue()));
       // waitFor(pathToFile.contains(filePathInAttachInput.getValue()));
    }

    public boolean isFileAttached(AttachFileDTO attachFileDTO) {
        String fileName = attachFileDTO.getFileName();
            if(filePathInAttachInput.withTimeoutOf(5,SECONDS).getText().equals(fileName)){
                return true;
            }
        return false;
    }

    public static void setClipboardData(String string) {
        StringSelection stringSelection = new StringSelection(string);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
    }

    public boolean isMaxSizeErrorMsgDisplayed(String msg) {
        if(sizeErrorField.getText().equals(msg)){
            return true;
        }else{
            return false;
        }
    }

    public boolean isFormatErrorMsgDisplayed(String msg) {
        if(formatErrorField.getText().equals(msg)){
            return true;
        }else{
            return false;
        }
    }
}
