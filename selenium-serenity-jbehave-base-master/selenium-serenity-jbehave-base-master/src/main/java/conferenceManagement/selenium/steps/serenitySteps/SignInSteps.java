package conferenceManagement.selenium.steps.serenitySteps;

import conferenceManagement.selenium.page.conferenceManagement.ConferenceManagementHomePage;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.Step;
import org.codehaus.groovy.runtime.powerassert.SourceText;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Lev_Serba on 11/7/2016.
 */
public class SignInSteps {

    ConferenceManagementHomePage cmHomePage;
    String username, password;

    @FindBy(className = "menu-container")
    WebElementFacade accMenu;

    @Step
    public void openHomePage() {
        cmHomePage.open();
    }

    @Step
    public void logout() {
        if(cmHomePage.hasSignOutMenu()){
            cmHomePage.clickSignOut();
        }
    }

    @Step
     public void loginAsUser(String nickname) {
        switch (nickname){
            case "valid user":
                username = System.getProperty("cm-valid.username");
                password = System.getProperty("cm-valid.password");
                break;
            case "invalid user":
                username = System.getProperty("cm-invalid.username");
                password = System.getProperty("cm-invalid.password");
                break;
            default:
        }

//        username = "speaker@speaker.com";
//        password = "speaker";


        cmHomePage.setLogin(username);
        cmHomePage.setPassword(password);
        cmHomePage.clickSignInButton();



    }

    @Step
    public boolean isSignedIn() {
       // System.out.println(cmHomePage.getAccountName().split("@")[0].toLowerCase());
        if(cmHomePage.hasSignOutMenu() && username.contains(cmHomePage.getAccountName().split("'")[0].toLowerCase())) {
            return true;
        }
        return false;
    }


}
