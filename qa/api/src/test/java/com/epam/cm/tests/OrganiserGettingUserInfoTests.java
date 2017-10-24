package com.epam.cm.tests;

import com.epam.cm.base.*;
import com.epam.cm.jira.Jira;
import io.restassured.http.ContentType;
import org.junit.Test;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


public class OrganiserGettingUserInfoTests extends SimpleBaseTest {


    @Test
    @Jira("6662")
    public void positiveOrganiserGettingExistingUserTest() {

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.organiserUser, config.organiserPassword)
                .cookie(TOKEN, response.cookie(TOKEN))
                .header(X_TOKEN, response.cookie(TOKEN))
                .
        when()
                .get(EndpointUrl.USER + TextConstants.EXISTING_USER_ID)
                .
        then().log().all()
                .statusCode(200)
                .assertThat()
                .body(TextConstants.ID, hasToString(TextConstants.EXISTING_USER_ID), TextConstants.ROLES, notNullValue());
    }

    @Test
    @Jira("6794")
    public void negativeNonLoggedUserGettingExistingUserTest() {

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(TextConstants.WRONG_USER, TextConstants.WRONG_PASSWORD)
                .cookie(TOKEN, response.cookie(TOKEN))
                .header(X_TOKEN, response.cookie(TOKEN))
                .
        when()
                .get(EndpointUrl.USER+TextConstants.EXISTING_USER_ID)
                .
        then().log().all()
                .statusCode(401)
                .assertThat()
                .body(TextConstants.ERROR, hasToString(TextConstants.LOGIN_ERROR));
    }

    @Test
    @Jira("6666")
    public void negativeOrganiserGettingNonExistingUserTest() {

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.organiserUser, config.organiserPassword)
                .cookie(TOKEN, response.cookie(TOKEN))
                .header(X_TOKEN, response.cookie(TOKEN))
                .
        when()
                .get(EndpointUrl.USER+TextConstants.NON_EXISTING_USER_ID)
                .
        then().log().all()
                .statusCode(404)
                .assertThat()
                .body(TextConstants.ERROR, hasToString(TextConstants.USER_NOT_FOUND));
    }
}
