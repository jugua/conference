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

    @FindBy(className = "menu-container")
    WebElementFacade accMenu;
    @FindBy(xpath = "//li[@class = 'menu-list__item menu-list__item_sign-out']/a")
    WebElementFacade signOutButton;
    @FindBy(id="sign-in-email")
    WebElementFacade loginField;
    @FindBy(id="sign-in-password")
    WebElementFacade passwordField;
    @FindBy(xpath = "//form/input[@type = 'submit']")
    WebElementFacade signInButton;

    WebDriver  driver;

    public ConferenceManagementHomePage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public String getAccountName(){
        setImplicitTimeout(2, SECONDS);
        return accMenu.find(By.xpath("button")).getText();
    }

    public void refresh(){
        driver.navigate().refresh();
    }

    public boolean hasSignOutMenu() {
        accMenu.click();
        if(signOutButton.isCurrentlyVisible()){
            return true;
        }
        return false;
    }

    public void clickSignOut() {
        signOutButton.click();
    }

    public void setEmail(String login) {
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
