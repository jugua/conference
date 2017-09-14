package com.epam.cm.steps.serenity;

import com.epam.cm.core.utils.Randomizer;
import com.epam.cm.core.utils.WebDriverSupport;
import com.epam.cm.dto.AttachFileDTO;
import com.epam.cm.dto.MyTalksDTO;
import com.epam.cm.pages.MyTalksPage;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.steps.ScenarioSteps;

import java.awt.*;


public class MyTalksPageSteps extends ScenarioSteps {

    public static final int RAND_STR_LENGTH = 10;
    MyTalksPage myTalksPage;
    private static final int  MAX_COMMENT_LENTH = 1000;

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
    public void typeOrgComments(MyTalksDTO myTalksDTO){
        myTalksPage.setOrgComments(myTalksDTO.getComment());
    }

    public void clickRejectBtn(){
        myTalksPage.clickRejectBtn();
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


    @Step
    public String findRowWithStatus(String status)  {
        return myTalksPage.getStatus(status);
    }

    @Step
    public void clickFoundedTitle(String name){
        System.out.println();
        myTalksPage.clickTitle(name);
    }

    @Step
    public void clickFoundedSpeaker(){
        //refactor this method
        myTalksPage.clickSpeaker();
    }

    public void clickInProgressBtn() {
        myTalksPage.clickInProgressBtn();
    }

    public void clickApproveBtn() {
        myTalksPage.clickApproveBtn();
    }

    @Step
    public String getNoRejectionReasonErrMessage() {
        return myTalksPage.getNoRejectionReasonErrMessage();
    }

    @Step
    public boolean organiserCantTypeMoreThanMaxAllowedSymbol (){
        if(myTalksPage.getOrgCommentsActualLength()<= MAX_COMMENT_LENTH)
            return true;
        return false;
    }

    @Step
    public boolean areFieldsExceptCommentReadOnly() {
        return myTalksPage.areFieldsExceptCommentReadOnly();
    }

    @Step
    public boolean areViewFieldReadOnly(){
        return myTalksPage.areFieldInViewReadOnly();
    }

    public void justWait(){
        myTalksPage.waitABitLol();
    }

    @Step
    public boolean areFieldsReadOnlyForSpeaker() {
        return myTalksPage.areFieldsReadOnlyForSpeaker();
    }

    @Step
    public boolean areFieldsReadOnlyForOrganiser() {
        return myTalksPage.areFieldsReadOnlyForOrganiser();
    }

    @Step
    public void hoverAttachIcon() {
        myTalksPage.hoverAttachIcon();
    }

    @Step
    public String getAttachHintText() {
        return myTalksPage.getAttachHintText();
    }

    @Step
    public boolean isFullTextVisible() {
        return myTalksPage.isFullTextVisible();
    }

    @Step
    public void typeFullPathIntoAttachField(String pathToFile) throws AWTException {
        myTalksPage.typeFullPathIntoAttachField(pathToFile);
    }

    @Step
    public boolean isFileAttached(AttachFileDTO attachFileDTO) {
        return myTalksPage.isFileAttached(attachFileDTO);
    }

    @Step
    public boolean isMaxSizeErrorMsgDisplayed(String msg) {
       return myTalksPage.isMaxSizeErrorMsgDisplayed(msg);
    }

    @Step
    public boolean isFormatErrorMsgDisplayed(String msg) {
        return myTalksPage.isFormatErrorMsgDisplayed(msg);
    }

/*    @Step
    public boolean areFieldsReadOnly(MyTalksDTO dto) {
        typeTextInFields(dto);
        if(dto.getTitle().equals(myTalksPage.getOrganiserTitleValue()) &&
           dto.getDescription().equals(myTalksPage.getOrganiserDescriptionValue())&&
           dto.getTopic().equals(myTalksPage.getOrganiserTopicValue())&&
           dto.getType().equals(myTalksPage.getOrganiserTypeValue())&&
           dto.getLanguage().equals(myTalksPage.getOrganiserLanguageValue())&&
           dto.getLevel().equals(myTalksPage.getOrganiserLevelValue())&&
           dto.getAdditionalInfo().equals(myTalksPage.getOrganiserAdditionalInfoValue())){
           return true;
        }
        return false;
    }

    public void typeTextInFields(MyTalksDTO dto){
        myTalksPage.setOrganiserTitleField(Randomizer.generateRandomAlphaString(RAND_STR_LENGTH));
        myTalksPage.setOrganiserDescriptionField(Randomizer.generateRandomAlphaString(RAND_STR_LENGTH));
        myTalksPage.setOrganiserTopic(Randomizer.generateRandomAlphaString(RAND_STR_LENGTH));
        myTalksPage.setOrganiserType(Randomizer.generateRandomAlphaString(RAND_STR_LENGTH));
        myTalksPage.setOrganiserLanguage(Randomizer.generateRandomAlphaString(RAND_STR_LENGTH));
        myTalksPage.setOrganiserLevel(Randomizer.generateRandomAlphaString(RAND_STR_LENGTH));
        myTalksPage.setOrganiserAdditionalInfoField(Randomizer.generateRandomAlphaString(RAND_STR_LENGTH));
    }*/

}

