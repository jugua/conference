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
    @FindBy(xpath = "//*[@class='pop-up']/p")
    WebElementFacade errorMsgMyInfoNotFieldText;
    @FindBy(xpath = "//*[@class='btn pop-up__button']")
    WebElementFacade errorMsgMyInfoSubmitBtn;

    public void clickSubmitNEwTalk(){
        submitNewTalkBtn.click();
    }

    public String getErrorNoMyInfoMsgText(){
        return errorMsgMyInfoNotFieldText.getText();
    }

    public void clickOKBtn(){
        errorMsgMyInfoSubmitBtn.click();
    }
}
