package com.epam.cm.tests;

import com.epam.cm.base.*;
import com.epam.cm.jira.Jira;
import com.epam.cm.utils.JsonLoader;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.Ignore;
import org.junit.Test;

import static com.epam.cm.base.TextConstants.SUCCESSFULLY_UPDATED;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasToString;

public class UpdateConferenceTests extends SimpleBaseTest {

    private String validContent  = JsonLoader.asString("UpdateConferenceValidData.json");
    private String invalidContent  = JsonLoader.asString("UpdateConferenceNonExisting.json");

    @Test
    @Jira("6866")
    public void updateConferenceAdminTest(){

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.adminUser, config.adminPassword)
                .cookie(TOKEN, response.cookie(TOKEN))
                .header(X_TOKEN, response.cookie(TOKEN))
                .body(validContent)
                .
        when()
                .patch(EndpointUrl.UPDATE_CONFERENCE)
                .
        then().log().all()
                .statusCode(200)
                .assertThat()
                .body(TextConstants.ERROR, Matchers.hasToString(SUCCESSFULLY_UPDATED));
    }

    // Test is failed on old server
    // Bug ID: EPMFARMKPP-6895
    // Link to Bug: https://jirapct.epam.com/jira/browse/EPMFARMKPP-6895
    //Bug is marked as fixed
    // On new server 401 - unatorized is displayed
    @Test
    @Jira("6864")
    @Ignore
    public void updateConferenceOrganiserTest(){

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.organiserUser, config.organiserPassword)
                .cookie(TOKEN, response.cookie(TOKEN))
                .header(X_TOKEN, response.cookie(TOKEN))
                .body(validContent)
                .
        when()
                .patch(EndpointUrl.UPDATE_CONFERENCE)
                .
        then().log().all()
                .statusCode(200).assertThat()
                .body(TextConstants.ERROR, Matchers.hasToString(SUCCESSFULLY_UPDATED));
    }

    @Test
    @Jira("6863")
    public void updateConferenceUserWithInvalidCredentialsTest(){

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(TextConstants.WRONG_USER, TextConstants.WRONG_PASSWORD)
                .cookie(TOKEN, response.cookie(TOKEN))
                .header(X_TOKEN, response.cookie(TOKEN))
                .body(validContent)
                .
        when()
                .patch(EndpointUrl.UPDATE_CONFERENCE)
                .
        then().log().all()
                .statusCode(401)
                .assertThat()
                .body(TextConstants.ERROR, hasToString(TextConstants.LOGIN_ERROR));
    }

    @Test
    @Jira("6864")
    @Ignore
    // Update is available, status 200 - successfully updated
    public void updateNonExistingConferenceAdminTest(){

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.adminUser, config.adminPassword)
                .cookie(TOKEN, response.cookie(TOKEN))
                .header(X_TOKEN, response.cookie(TOKEN))
                .body(invalidContent)
                .
        when()
                .patch(EndpointUrl.UPDATE_NON_EXISTING_CONFERENCE)
                .
        then().log().all()
                .statusCode(404);
    }
}
