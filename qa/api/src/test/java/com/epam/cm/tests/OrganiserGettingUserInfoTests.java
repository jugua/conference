package com.epam.cm.tests;

import com.epam.cm.base.*;
import com.epam.cm.jira.Jira;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import static io.restassured.path.json.JsonPath.from;

import java.util.Map;

import static io.restassured.RestAssured.config;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


public class OrganiserGettingUserInfoTests extends SimpleBaseTest {


    @Test
    @Jira("6662")
    public void positiveOrganiserGettingExistingUserTest() {

        Response endResponse =
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
                then()
                        .log().all()
                        .statusCode(200)
                        .assertThat()
                        .body(TextConstants.ID, hasToString(TextConstants.EXISTING_USER_ID))
                        .body(TextConstants.ROLES, notNullValue()).extract().response();

        String jsonAsString = endResponse.getBody().asString();
        Map<String, ?> jsonAsArrayList = from(jsonAsString).get("");
        int fieldsCount = jsonAsArrayList.size();

        Assert.assertEquals(15, fieldsCount);


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
                .get(EndpointUrl.USER + TextConstants.EXISTING_USER_ID)
                .
        then()
                .log().all()
                .statusCode(401)
                .assertThat()
                .body(TextConstants.ERROR, hasToString("login_auth_err"));


    }

    //6666
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
                .get(EndpointUrl.USER + TextConstants.NON_EXISTING_USER_ID)
                .
        then()
                .log().all()
                .statusCode(404)
                .assertThat()
                .body(TextConstants.ERROR, hasToString("user_not_found"))
                .body(TextConstants.SECONDS_TO_EXPIRY, nullValue())
                .body(TextConstants.RESULT, nullValue())
                .body(TextConstants.ID, nullValue())
                .body(TextConstants.FIELDS, nullValue());


    }
}
