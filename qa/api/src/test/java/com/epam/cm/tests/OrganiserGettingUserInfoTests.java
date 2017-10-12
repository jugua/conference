package com.epam.cm.tests;

import com.epam.cm.base.*;
import io.restassured.http.ContentType;
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

    //6662
    @Test
    public void positiveOrganiserGettingExistingUserTest() {

        endResponse=
        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.organiserUser, config.organiserPassword)
                .cookie(TOKEN, response.cookie(TOKEN))
                .header(XTOKEN, response.cookie(TOKEN))
                .
                        when()
                .get(EndpointUrl.USER + TextConstants.EXISTING_USER_ID)
                .
                        then().log().all()
                .statusCode(200)
                .assertThat()
                .body(TextConstants.ID, hasToString(TextConstants.EXISTING_USER_ID))
        .body("roles", notNullValue()).extract().response();

        String jsonAsString = endResponse.getBody().asString();
        Map<String,?> jsonAsArrayList = from(jsonAsString).get("");
        int fieldsCount = jsonAsArrayList.size();

        Assert.assertEquals(15, fieldsCount);




    }

    //6794
    @Test
    public void negativeNonLoggedUserGettingExistingUserTest() {

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(TextConstants.WRONG_USER, TextConstants.WRONG_PASSWORD)
                .cookie(TOKEN, response.cookie(TOKEN))
                .header(XTOKEN, response.cookie(TOKEN))
                .
                        when()
                .get(EndpointUrl.USER+TextConstants.EXISTING_USER_ID)
                .
                        then().log().all()
                .statusCode(401)
                .assertThat()
                .body("error", hasToString("login_auth_err"));


    }

    //6666
    @Test
    public void negativeOrganiserGettingNonExistingUserTest() {

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.organiserUser, config.organiserPassword)
                .cookie(TOKEN, response.cookie(TOKEN))
                .header(XTOKEN, response.cookie(TOKEN))
                .
                        when()
                .get(EndpointUrl.USER+TextConstants.NON_EXISTING_USER_ID)
                .
                        then().log().all()
                .statusCode(404)
                .assertThat()
                .body("error", hasToString("user_not_found"))
                .body("secondsToExpiry", nullValue())
                .body("result", nullValue())
                .body("id", nullValue())
                .body("fields", nullValue());


    }
}
