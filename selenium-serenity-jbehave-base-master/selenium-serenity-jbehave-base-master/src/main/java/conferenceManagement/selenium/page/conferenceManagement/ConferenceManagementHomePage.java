package conferenceManagement.selenium.page.conferenceManagement;

import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.DefaultUrl;
import org.openqa.selenium.WebDriver;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by Lev_Serba on 11/7/2016.
 */
@DefaultUrl("http://localhost:9000/#")
public class ConferenceManagementHomePage extends PageObject {

    public ConferenceManagementHomePage(WebDriver driver) {
        super(driver);
    }

    @FindBy(className = "menu-container")
    WebElementFacade accMenu;
    @FindBy(className = "menu-list__title")
    WebElementFacade signOutButton;
    @FindBy(id="sign-in-email")
    WebElementFacade loginField;
    @FindBy(id="sign-in-password")
    WebElementFacade passwordField;
    @FindBy(xpath = "//form/input[@type = 'submit']")
    WebElementFacade signInButton;
    @FindBy(xpath = "//div[@class='menu-container__content ng-hide']")
    WebElementFacade showMenuFlag;






    public String getAccountName(){
        setImplicitTimeout(2, SECONDS);
        return accMenu.find(By.xpath("button")).getText();

    }

    private void showMenu(){
        if(showMenuFlag.isVisible()){
            accMenu.click();
        }
    }


    public boolean hasSignOutMenu() {

        //showMenu();
        accMenu.click();
        if(signOutButton.isCurrentlyVisible()){
            return true;
        }
        return false;

    }

    public void clickSignOut() {
        signOutButton.click();
    }

    public void setLogin(String login) {
        loginField.clear();
        loginField.type(login);
    }

    public void setPassword(String password) {
        passwordField.clear();
        passwordField.type(password);
    }

    public void clickSignInButton() {
        signInButton.click();
    }
}
