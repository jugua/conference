package com.epam.cm.pages;

import java.util.List;

import com.epam.cm.dto.MyInfoFieldsDTO;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;

import org.openqa.selenium.WebDriver;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.openqa.selenium.By.xpath;

public class MyInfoPage extends AnyPage {

    public static final String SUCCESS_SAVE_OK_BUTTON = "./input[@type='button']";
    public static final String ATTENTOIN_OK_BTN = "./div/input[1]";
    public static final String MY_INFO_FIELDS = ".//input[contains(@id,'my-info')] | .//textarea[contains(@id,'my-info')]";
    public static final String Paragraph = "./p";
    private static final String EXPECTED_SUCCESS_SAVE_MSG = "Changes saved successfully";
    private static final String EXPECTED_ATTENTION_SAVE_MSG = "Would you like to save changes?";
    private static final String EXPECTED_ERROR_SAVE_MSG = "Please fill in all mandatory fields";
    public static final String MY_INFO_PATH = "//ul[@class = 'tabs-list']/li[1]/a";
    public static final String HEADER = "//header";

    @FindBy(xpath = "//ul[@class='tabs-list']/li[1]/a[@class = 'tabs-list__anchor tabs-list__anchor_active']")
    WebElementFacade myInfoTabActive;
    @FindBy(xpath = "//ul[@class='tabs-list']/li[2]/a[@class = 'tabs-list__anchor tabs-list__anchor_active']")
    WebElementFacade myTalksTabActive;
    @FindBy(xpath = "/html/body/div/div/tabs/div/div/div/ui-view/my-info/div[1]/div")
    WebElementFacade successfullySavedInfoPopUp;
    @FindBy(xpath = "/html/body/div/div/tabs/div/div/div/ui-view/my-info/div[2]/div")
    WebElementFacade attentionPopUp;
    @FindBy(xpath = "html/body/div[1]/div/tabs/div/div/div/ui-view/my-info/div[1]/div")
    WebElementFacade errorPopUp;
    @FindBy(xpath = "//input[@value='save']")
    WebElementFacade saveButton;
    @FindBy(xpath = "//ul[@class = 'tabs-list']/li[2]/a")
    WebElementFacade myTalksTab;
    @FindBy(xpath = "//ul[@class = 'tabs-list']/li[1]/a")
    WebElementFacade myInfoTab;
    @FindBy(xpath = "//*[@id = 'my-info-bio']")
    WebElementFacade shortBioField;
    @FindBy(xpath = "//*[@id = 'my-info-job']")
    WebElementFacade jobTitleField;
    @FindBy(xpath = "//*[@id = 'my-info-linkedin-past-conferences']")
    WebElementFacade pastConferencesField;
    @FindBy(xpath = "//*[@id = 'my-info-additional-info']")
    WebElementFacade additionalInfoField;
    @FindBy(xpath = "html/body/div[1]/div/tabs/div/div/div/ui-view/my-info/div[1]/div/input")
    WebElementFacade errorPopUpOkButton;
    @FindBy(xpath = "//*[@class='my-talks__header']/a")
    WebElementFacade myTalksHeaderBtn;
    private List<WebElementFacade> myInfoFields;

    public MyInfoPage(final WebDriver driver) {
        super(driver);
    }

    public boolean isMyInfoTabActive() {
        withTimeoutOf(5, SECONDS).waitFor(additionalInfoField);
        if (myInfoTabActive.isCurrentlyVisible()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean areAllFieldsEmpty() {
        withTimeoutOf(5, SECONDS).waitFor(additionalInfoField);
        myInfoFields = findAll(xpath(MY_INFO_FIELDS));
        for (WebElementFacade element : myInfoFields) {
            if (!element.getText().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public void fillMyInfoFields(MyInfoFieldsDTO myInfoDTO) {
        int i = 0;
        withTimeoutOf(5, SECONDS).waitFor(additionalInfoField);
        myInfoFields = findAll(xpath(MY_INFO_FIELDS));
        for (WebElementFacade element : myInfoFields) {
            element.type(myInfoDTO.getListOfFields().get(i));
            i++;
        }
    }

    public boolean isInformationSavedSuccessfullyPopUp() {
        withTimeoutOf(5, SECONDS).waitFor(successfullySavedInfoPopUp);
        if (successfullySavedInfoPopUp.findBy(xpath(Paragraph)).getText().trim()
                .equalsIgnoreCase(EXPECTED_SUCCESS_SAVE_MSG)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isInformationSavedSuccessfully(MyInfoFieldsDTO myInfoDTO) {
        int i = 0;
        withTimeoutOf(5, SECONDS).waitFor(additionalInfoField);
        myInfoFields = findAll(xpath(MY_INFO_FIELDS));
        for (WebElementFacade element : myInfoFields) {
            String actual = element.getValue();
            String expected = myInfoDTO.getListOfFields().get(i).trim();
            if ((actual.trim()).equalsIgnoreCase(expected)) {
                i++;
            } else {
                return false;
            }
        }
        return true;
    }

    public void clickInformationSavedSuccessfullyPopUpOkButton() {
        successfullySavedInfoPopUp.findBy(xpath(SUCCESS_SAVE_OK_BUTTON)).click();
    }

    public void clickAttentionPopUpYesButton() {
        attentionPopUp.findBy(xpath(ATTENTOIN_OK_BTN)).click();
    }

    public void clickSaveButton() {
        saveButton.withTimeoutOf(5, SECONDS).waitUntilClickable().click();
    }

    public void clickMyTalksTab() {
        moveTo(MY_INFO_PATH);
        myTalksTab.withTimeoutOf(5, SECONDS).waitUntilClickable().click();
    }

    public boolean isAttentionPopUpShown() {
        withTimeoutOf(5, SECONDS).waitFor(attentionPopUp);
        if (attentionPopUp.findBy(xpath(Paragraph)).getText().trim().equalsIgnoreCase(EXPECTED_ATTENTION_SAVE_MSG)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isErrorPopUpShown() {
        withTimeoutOf(5, SECONDS).waitFor(errorPopUp);
        if (errorPopUp.findBy(xpath(Paragraph)).getText().trim().equalsIgnoreCase(EXPECTED_ERROR_SAVE_MSG)) {
            return true;
        } else {
            return false;
        }
    }

    public void clickErrorPopUpOkButton() {
        errorPopUpOkButton.withTimeoutOf(5, SECONDS).waitUntilClickable().click();
    }

    public int getShortBioActualLength() {
        return shortBioField.getValue().length();
    }

    public int getJobTitleActualLength() {
        return jobTitleField.getValue().length();
    }

    public int getPastConferencesActualLength() {
        return pastConferencesField.getValue().length();
    }

    public int getAdditionalInfoActualLength() {
        return additionalInfoField.getValue().length();
    }

    public boolean myTalksTabIsActive() {
        withTimeoutOf(5, SECONDS).waitFor(myTalksHeaderBtn);
        if (myTalksTabActive.isCurrentlyVisible()) {
            return true;
        } else {
            return false;
        }
    }

    public void clickMyInfoTab() {
        myInfoTab.withTimeoutOf(5, SECONDS).waitUntilClickable().click();
    }

    public void scrollToTop() {
        moveTo(HEADER);
    }

}
