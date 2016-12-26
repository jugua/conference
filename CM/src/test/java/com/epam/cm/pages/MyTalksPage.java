package com.epam.cm.pages;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.WebDriver;

/**
 * Created by Serhii_Kobzar on 12/26/2016.
 */
public class MyTalksPage extends AnyPage  {

    public MyTalksPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//*[@class='my-talks__header']/a")
    WebElementFacade submitNewTalkBtn;

    //no my info
    @FindBy(xpath = "//*[@class='pop-up']/p")
    WebElementFacade errorMsgMyInfoNotFieldText;
    @FindBy(xpath = "//*[@class='btn pop-up__button']")
    WebElementFacade errorMsgMyInfoSubmitBtn;
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
    //big pop-up fields

    @FindBy(xpath = "//*[@class='pop-up-wrapper']/div/button")
    WebElementFacade cancelBigPopUpBtn;
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

    public void clickSubmitNewTalk(){
        submitNewTalkBtn.click();
    }

    public String getErrorNoMyInfoMsgText(){
        return errorMsgMyInfoNotFieldText.getText();
    }

    public void clickCancelNoMyInfoPopUpBtn(){
        cancelMyInfoBtn.click();
    }

    public void clickOKBtn(){
        errorMsgMyInfoSubmitBtn.click();
    }

    public void clickBigPopUpSbmBtn(){
        newTalkBigPopUpSubmitBtn.click();
    }

    public void clickOkBtn(){
        okBtnAfterInvalidData.click();
    }

    public String getAlertMsg(){
        return alertMsg.getText();
    }

    public String getFirstInfoMsgText(){
        return firstNotSavingMsg.getText();
    }

    public String getSecondInfoMsgText(){
        return secondNotSavingMsg.getText();
    }

    public String getThirdMsgText(){
        return thirdNotSavingMsg.getText();
    }

    public void clickYesBtn(){
        yesNotSavingBtn.click();
    }

    public void clickNoBtn(){
        noNotSavingBtn.click();
    }

    public void clickCancelBigPopUpBtn(){
        cancelBigPopUpBtn.click();
    }

    public void clickCancelInvalidData(){
        cancelInvalidDataBtn.click();
    }

    public void setTitleField(String title){
        titleField.type(title);
    }

    public void setDescriptionField(String description){
        descriptionField.type(description);
    }

    public void setAdditionalInfoField(String additionalInfo){
        additionalInfoField.type(additionalInfo);
    }

    public void selectTopic(String topic){
        topicDropDown.selectByValue(topic);
    }

    public void selectType(String type){
        typeDropDown.selectByValue(type);
    }

    public void selectLanguage(String language){
        languageDropDown.selectByValue(language);
    }

    public void selectLevel(String level){
        levelDropDown.selectByValue(level);
    }

}
