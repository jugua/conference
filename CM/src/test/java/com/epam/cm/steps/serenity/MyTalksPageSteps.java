package com.epam.cm.steps.serenity;

import com.epam.cm.pages.MyTalksPage;
import net.thucydides.core.annotations.Step;
import org.yecht.Data;

/**
 * Created by Serhii_Kobzar on 12/26/2016.
 */
public class MyTalksPageSteps {

    MyTalksPage myTalksPage;

    @Step
    public void clickSubmitNewTalkBtn(){
        myTalksPage.clickSubmitNewTalk();
    }

    @Step
    public String getEmptyMyInfoPopUpText(){
        return myTalksPage.getErrorNoMyInfoMsgText();
    }

    @Step
    public String getEmptyFieldsPopUpText(){
        return myTalksPage.getEmptyFieldsAlertMsg();
    }

    @Step
    public void clickOkBtn(){
        myTalksPage.clickOkBtn();
    }

    @Step
    public void clickBigPopUpSubmitBtn(){
        myTalksPage.clickBigPopUpSbmBtn();
    }

    @Step
    public boolean isTitleHighL(){
        return myTalksPage.isTitleHighlighted();
    }

    @Step
    public boolean isDescriptionHighL(){
        return myTalksPage.isDescriptionHighlighted();
    }

    @Step
    public boolean isTopicHighL(){
        return myTalksPage.isTopicHighLighted();
    }

    @Step
    public boolean isTypeHighL(){
        return myTalksPage.isTypeHighlighted();
    }

    @Step
    public boolean isLanguageHighL(){
        return myTalksPage.isLanguageHighlighted();
    }

    @Step
    public boolean isLevelHighL(){
        return myTalksPage.isLevelHighlighted();
    }

}
