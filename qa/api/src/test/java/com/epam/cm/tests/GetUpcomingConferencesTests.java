package com.epam.cm.tests;

import com.epam.cm.base.*;
import com.epam.cm.jira.Jira;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasToString;


public class GetUpcomingConferencesTests extends SimpleBaseTest {

    @Test
    @Jira("6732")
    public void getUpcomingConferencesNonLoggedUserTest(){

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .
        when()
                .get(EndpointUrl.UPCOMING_CONFERENCE)
                .
        then().log().all()
                .statusCode(200)
                .assertThat()
                .body(TextConstants.ID, Matchers.notNullValue(),
                        TextConstants.TITLE, Matchers.notNullValue());
    }

    @Test
    @Jira("6729")
    public void getUpcomingConferencesAdminTest(){

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().basic(config.adminUser, config.adminPassword)
                .cookie(SimpleBaseTest.TOKEN, response.cookie(SimpleBaseTest.TOKEN))
                .header(SimpleBaseTest.X_TOKEN, response.cookie(SimpleBaseTest.TOKEN))
                .
        when()
                .get(EndpointUrl.UPCOMING_CONFERENCE)
                .
        then().log().all()
                .statusCode(200)
                .assertThat()
                .body(TextConstants.ID, Matchers.notNullValue(),
                        TextConstants.TITLE, Matchers.notNullValue());
    }

    @Test
    @Jira("6759")
    public void getUpcomingConferencesOrganiserTest(){

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().basic(config.organiserUser, config.organiserPassword)
                .cookie(SimpleBaseTest.TOKEN, response.cookie(SimpleBaseTest.TOKEN))
                .header(SimpleBaseTest.X_TOKEN, response.cookie(SimpleBaseTest.TOKEN))
        .when()
                .get(EndpointUrl.UPCOMING_CONFERENCE)
        .then().log().all()
                .statusCode(200)
                .assertThat()
                .body(TextConstants.ID, Matchers.notNullValue(),
                        TextConstants.TITLE, Matchers.notNullValue());}

    @Test
    @Jira("6806")
    public void getUpcomingConferencesUserWithInvalidCredentialsTest(){

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth()
                .preemptive().basic(TextConstants.WRONG_USER, TextConstants.WRONG_PASSWORD)
        .when()
                .get(EndpointUrl.UPCOMING_CONFERENCE)
        .then().log().all()
                .statusCode(401)
                .assertThat()
                .body(TextConstants.ERROR, hasToString(TextConstants.LOGIN_ERROR));
    }
}