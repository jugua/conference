package com.epam.cm.tests;

import com.epam.cm.base.EndpointUrl;
import com.epam.cm.base.TextConstants;
import com.epam.cm.jira.Jira;
import com.epam.cm.utils.JsonLoader;
import io.restassured.http.ContentType;
import com.epam.cm.base.SimpleBaseTest;
import org.junit.Ignore;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

public class AddNewConferenceTest extends SimpleBaseTest{

    private String validContent  = JsonLoader.asString("AddNewConferenceValidData.json");
    private String invalidContent  = JsonLoader.asString("AddNewConferenceInvalidData.json");

    @Test
    @Jira("6622")
    @Ignore
    // Actual - 500 -Internal Server error on 8025
    public void positiveAddNewConferenceTest(){

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.adminUser, config.adminPassword)
                .cookie(TOKEN, response.cookie(TOKEN))
                .header(X_TOKEN, response.cookie(TOKEN))
                .body(validContent)
                .
        when()
                .post(EndpointUrl.CONFERENCE)
                .

        then().log().all()
                .statusCode(200)
                .assertThat()
                .body(TextConstants.ERROR, nullValue(), TextConstants.ID, notNullValue());
    }

    @Test
    @Jira("6659")
    public void negativeAddNewConferenceTest(){

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.speakerUser, config.speakerPassword)
                .cookie(TOKEN, response.cookie(TOKEN))
                .header(X_TOKEN, response.cookie(TOKEN))
                .body(validContent)
                .
        when()
                .post(EndpointUrl.CONFERENCE)
                .

        then().log().all()
                .statusCode(401)
                .assertThat()
                .body(TextConstants.ERROR, hasToString(TextConstants.UNAUTHORIZED));
    }

    @Ignore //test failed. bug 6825
    @Test
    @Jira("6822")
    public void negativeValidationFailedAddNewConferenceTest(){

        String invalidContent =JsonLoader.asString("AddNewConferenceInvalidData.json");

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.adminUser, config.adminPassword)
                .cookie(TOKEN, response.cookie(TOKEN))
                .header(X_TOKEN, response.cookie(TOKEN))
                .body(invalidContent)
                .
        when()
                .post(EndpointUrl.CONFERENCE)
                .
        then().log().all()
                .statusCode(400);
    }
}