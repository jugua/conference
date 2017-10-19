package com.epam.cm.tests;

import com.epam.cm.base.*;
import com.epam.cm.jira.Jira;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasToString;

public class GetPastConferencesTests extends SimpleBaseTest {

    @Test
    @Jira("6812")
    public void getPastConferencesNonLoggedUserTest(){

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .
        when()
                .get(EndpointUrl.PAST_CONFERENCE)
                .
        then().log().all()
                .statusCode(200)
                .assertThat()
                .body(TextConstants.ID, Matchers.notNullValue(),
                        TextConstants.TITLE, Matchers.notNullValue());
    }

    @Test
    @Jira("6811")
    public void getPastConferencesAdminTest(){

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().basic(config.adminUser, config.adminPassword)
                .cookie(SimpleBaseTest.TOKEN, response.cookie(SimpleBaseTest.TOKEN))
                .header(SimpleBaseTest.X_TOKEN, response.cookie(SimpleBaseTest.TOKEN))
                .
        when()
                .get(EndpointUrl.PAST_CONFERENCE)
                .
        then().log().all()
                .statusCode(200)
                .assertThat()
                .body(TextConstants.ID, Matchers.notNullValue(),
                        TextConstants.TITLE, Matchers.notNullValue());
    }

    @Test
    @Jira("6816")
    public void getPastConferencesOrganiserTest(){

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().basic(config.organiserUser, config.organiserPassword)
                .cookie(SimpleBaseTest.TOKEN, response.cookie(SimpleBaseTest.TOKEN))
                .header(SimpleBaseTest.X_TOKEN, response.cookie(SimpleBaseTest.TOKEN))
        .when()
                .get(EndpointUrl.PAST_CONFERENCE)
        .then().log().all()
                .statusCode(200)
                .assertThat()
                .body(TextConstants.ID, Matchers.notNullValue(),
                        TextConstants.TITLE, Matchers.notNullValue());
    }

    @Test
    @Jira("6815")
    public void getPastConferencesUserWithInvalidCredentialsTest(){


        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth()
                .preemptive().basic(TextConstants.WRONG_USER, TextConstants.WRONG_PASSWORD)
        .when()
                .get(EndpointUrl.PAST_CONFERENCE)
        .then().log().all()
                .statusCode(401)
                .assertThat()
                .body(TextConstants.ERROR, hasToString(TextConstants.LOGIN_ERROR));
    }
}