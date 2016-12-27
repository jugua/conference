package com.epam.cm.steps.serenity;

import com.epam.cm.core.utils.WebDriverSupport;
import com.epam.cm.dto.MyTalksDTO;
import com.epam.cm.pages.MyTalksPage;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;

/**
 * Created by Serhii_Kobzar on 12/26/2016.
 */
public class MyTalksPageSteps extends ScenarioSteps {

    MyTalksPage myTalksPage;

    @Step
    public void clickSubmitNewTalkBtn() {
        myTalksPage.clickSubmitNewTalk();
    }

    @Step
    public String getEmptyMyInfoPopUpText() {
        return myTalksPage.getErrorNoMyInfoMsgText();
    }

    @Step
    public String getEmptyFieldsPopUpText() {
        return myTalksPage.getEmptyFieldsAlertMsg();
    }

    @Step
    public String getFirstInfoMsg(){
        return myTalksPage.getFirstInfoMsgText();
    }

    @Step
    public String getSecondInfoMsg(){
        return myTalksPage.getSecondInfoMsgText();
    }

    @Step
    public String getThirdInfoMsg(){
        return myTalksPage.getThirdInfoMsgText();
    }

    @Step
    public void clickOkBtn() {
        myTalksPage.clickErrorMyInfoOkBtn();
    }

    @Step
    public void clickBigPopUpSubmitBtn() {
        myTalksPage.clickBigPopUpSbmBtn();
    }

    @Step
    public void clickBigPopUpCancelBtn(){
        myTalksPage.clickCancelBigPopUpBtn();
    }

    @Step
    public void clickYesInfoBtn(){
        myTalksPage.clickYesBtn();
    }

    @Step
    public boolean isTitleHighL() {
        return myTalksPage.isTitleHighlighted();
    }

    @Step
    public boolean isDescriptionHighL() {
        return myTalksPage.isDescriptionHighlighted();
    }

    @Step
    public boolean isTopicHighL() {
        return myTalksPage.isTopicHighLighted();
    }

    @Step
    public boolean isTypeHighL() {
        return myTalksPage.isTypeHighlighted();
    }

    @Step
    public boolean isLanguageHighL() {
        return myTalksPage.isLanguageHighlighted();
    }

    @Step
    public boolean isLevelHighL() {
        return myTalksPage.isLevelHighlighted();
    }

    @Step
    public void typeTitle(MyTalksDTO myTalks){
        myTalksPage.setTitleField(myTalks.getTitle());
    }

    @Step
    public void typeDescription(MyTalksDTO myTalks){
        myTalksPage.setDescriptionField(myTalks.getDescription());
    }

    @Step
    public void typeAdditionalInfo(MyTalksDTO myTalks){
        myTalksPage.setAdditionalInfoField(myTalks.getAdditionalInfo());
    }

    @Step
    public void chooseTopic(MyTalksDTO myTalksDTO){
        myTalksPage.selectTopic(myTalksDTO.getTopic());
    }

    @Step
    public void chooseType(MyTalksDTO myTalksDTO){
        myTalksPage.selectType(myTalksDTO.getType());
    }

    @Step
    public void chooseLanguage(MyTalksDTO myTalksDTO){
        myTalksPage.selectLanguage(myTalksDTO.getLanguage());
    }

    @Step
    public void chooseLevel(MyTalksDTO myTalksDTO){
        myTalksPage.selectLevel(myTalksDTO.getLevel());
    }

    @Step
    public boolean userCantTypeMoreThenMaxTitle(MyTalksDTO myTalksDTO){
        if (myTalksPage.getTitleLength()<= myTalksDTO.getTitle().length())
            return true;
        WebDriverSupport.reloadPage();
        return false;
    }

    @Step
    public boolean userCantTypeMoreThenMaxDescription(MyTalksDTO myTalksDTO){
        if(myTalksPage.getDescriptionLength()<= myTalksDTO.getDescription().length())
            return true;
        WebDriverSupport.reloadPage();
        return false;
    }

    @Step
    public boolean userCantTypeMoreThenMaAdditionalInfo(MyTalksDTO myTalksDTO){
        if(myTalksPage.getAdditionalInfoLength()<=myTalksDTO.getAdditionalInfo().length())
            return true;
        WebDriverSupport.reloadPage();
        return false;
    }
}
