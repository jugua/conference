package com.epam.cm.steps.serenity;

import com.epam.cm.core.mail.MailCatcherClient;
import com.epam.cm.core.utils.WebDriverSupport;
import com.epam.cm.pages.HomePage;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.pages.Pages;
import net.thucydides.core.steps.ScenarioSteps;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class ForgotPaswordPageSteps extends ScenarioSteps {

    HomePage homePage;
    MailCatcherClient mailCatcherClient = new MailCatcherClient();

    public ForgotPaswordPageSteps(final Pages pages) {
        super(pages);
    }

    @Step
    public void gotoTheHomePage() {

        getPages().get(HomePage.class).open();
        WebDriverSupport.clearLocalStorage();
        WebDriverSupport.reloadPage();
    }

    @Step
    public void clickOnAccountMenu() {
        homePage.getMenu().clickAccountMenuButton();
    }

    @Step
    public void clickForgotPwLink() {
        homePage.getMenu().clickForgotPwLink();
    }

    @Step
    public void checkPopUpIsPresent() {
        homePage.getMenu().popUpISPresent();
    }

    @Step
    public void checkForgotPwLinkIsPresent() {
        homePage.getMenu().forgotLblIsPresent();
    }

    @Step
    public String getForgotPwLblText() {
        return homePage.getMenu().forgotLblText();
    }

    @Step
    public void checkContinueBtn() {
        homePage.getMenu().continiuBtnIsPresent();
    }

    @Step
    public void checkCancelBtn() {
        homePage.getMenu().cancelBtnIsPresent();
    }

    @Step
    public void clickContinueBtn() {
        homePage.getMenu().clickContinueButton();
    }

    @Step
    public String getEmptyEmailMsgText() {
        return homePage.getMenu().getEmtyEmailMsgTxt();
    }

    @Step
    public String getInvalidEmailMsg() {
        return homePage.getMenu().getInvalidEmailMsg();
    }

    @Step
    public boolean isEmailForgotPwFieldHighlighted() {
        return homePage.getMenu().isEmailForgotPwHighlighted();
    }

    @Step
    public void typeEmailForgotPwField(String email) {
        homePage.getMenu().setEmailForgotPwFieldField(email);
    }

    @Step
    public String getNotficationText() {
        return homePage.getMenu().getPopUpNotification();
    }

    @Step
    public boolean isCancelBtnPresent() {
        return homePage.getMenu().cancelNotifiPopUpBtnisPresent();
    }

    @Step
    public void typeNewPassword(String password){
        homePage.getMenu().setNewPwForConfirmationEmail(password);
    }

    @Step
    public void typeConfirmNewPassword(String password){
        homePage.getMenu().setConfirmNewPwForConfirmationEmail(password);
    }

    @Step
    public void openLinkFromEmail() {
        String emailHtml = mailCatcherClient.getEmailById(mailCatcherClient.getLastEmail().getId(), MailCatcherClient.ResponseType.HTML).toString();
        Pattern pattern = Pattern.compile("href=\"([^\"]*)\"");
        java.util.regex.Matcher matcher = pattern.matcher(emailHtml);
        ArrayList<String> links = new ArrayList<String>();
        while(matcher.find()){
            links.add(matcher.group(1));
        }
        String link = links.get(links.size()-1);
        getDriver().get(link);
    }

    @Step
    public void clickOnSaveBtn() {
        homePage.getMenu().clickSaveBtn();
    }

    @Step
    public String getNoExistAccErrorMsg(){
        return homePage.getMenu().forgotErrorMsg();
    }
}
