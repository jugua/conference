package conferenceManagement.selenium.steps.serenitySteps;

import conferenceManagement.selenium.page.conferenceManagement.ConferenceManagementHomePage;
import net.thucydides.core.annotations.Step;


/**
 * Created by Lev_Serba on 11/7/2016.
 */
public class SignInSteps {

    ConferenceManagementHomePage cmHomePage;
    String email, password, username;

    @Step
    public void openHomePage() {
        cmHomePage.open();
    }

    @Step
    public void logout() {
        cmHomePage.refresh();
        if(cmHomePage.hasSignOutMenu()){
            cmHomePage.clickSignOut();
        }
    }

    @Step
     public void loginAsUser(String nickname) {
        switch (nickname){
            case "valid user":
                email = System.getProperty("cm-valid.email");
                password = System.getProperty("cm-valid.password");
                username = System.getProperty("cm-valid.username");
                break;
            case "invalid user":
                email = System.getProperty("cm-invalid.email");
                password = System.getProperty("cm-invalid.password");
                username = System.getProperty("cm-invalid.username");
                break;
            default:
        }
        cmHomePage.setEmail(email);
        cmHomePage.setPassword(password);
        cmHomePage.clickSignInButton();
    }

    @Step
    public boolean isSignedIn() {
        if(cmHomePage.hasSignOutMenu() && (cmHomePage.getAccountName().toLowerCase()).contains(username.toLowerCase())) {
            return true;
        }
        return false;
    }
}
