package com.epam.cm.tests;

import com.epam.cm.base.*;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import static io.restassured.RestAssured.given;



public class UpdateConferenceTests extends SimpleBaseTest {


    //6866
    @Test
    public void updateConferenceAdminTest(){

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.adminUser, config.adminPassword)
                .cookie(SimpleBaseTest.TOKEN, response.cookie(SimpleBaseTest.TOKEN))
                .header(SimpleBaseTest.XTOKEN, response.cookie(SimpleBaseTest.TOKEN))
                .body(ConferenceConstants.CONFERENCE_BODY_JSON)
                .
        when()
                .patch(EndpointUrl.UPDATE_CONFERENCE)
                .
        then().log().all()
                .statusCode(200).assertThat()
                .body("error", Matchers.nullValue())
                .extract().response();
    }


    //6864
    // Test is failed on old server
    // Bug ID: EPMFARMKPP-6895
    // Link to Bug: https://jirapct.epam.com/jira/browse/EPMFARMKPP-6895
    @Test
    @Ignore
    public void updateConferenceOrganiserTest(){

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.organiserUser, config.organiserPassword)
                .cookie(SimpleBaseTest.TOKEN, response.cookie(SimpleBaseTest.TOKEN))
                .header(SimpleBaseTest.XTOKEN, response.cookie(SimpleBaseTest.TOKEN))
                .body(ConferenceConstants.CONFERENCE_BODY_JSON)
                .
        when()
                .patch(EndpointUrl.UPDATE_CONFERENCE)
                .
        then().log().all()
                .statusCode(200).assertThat()
                .body("error", Matchers.nullValue())
                .extract().response();
    }


    //6863
    @Test
    public void updateConferenceUserWithInvalidCredentialsTest(){

        endResponse =
        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(TextConstants.WRONG_USER, TextConstants.WRONG_PASSWORD)
                .cookie(SimpleBaseTest.TOKEN, response.cookie(SimpleBaseTest.TOKEN))
                .header(SimpleBaseTest.XTOKEN, response.cookie(SimpleBaseTest.TOKEN))
                .body(ConferenceConstants.CONFERENCE_BODY_JSON)
                .
        when()
                .patch(EndpointUrl.UPDATE_CONFERENCE)
                .
        then().log().all()
                .statusCode(401).extract().response();

        String jsonAsString = endResponse.getBody().asString();

        Assert.assertEquals(ConferenceConstants.LOGIN_AUTH_ERR, jsonAsString);
    }

    //6864
    @Test
    public void updateNonExistingConferenceAdminTest(){

        endResponse =
        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.adminUser, config.adminPassword)
                .cookie(SimpleBaseTest.TOKEN, response.cookie(SimpleBaseTest.TOKEN))
                .header(SimpleBaseTest.XTOKEN, response.cookie(SimpleBaseTest.TOKEN))
                .body(ConferenceConstants.NON_EXISTING_CONFERENCE_BODY_JSON)
                .
        when()
                .patch(EndpointUrl.UPDATE_NON_EXISTING_CONFERENCE)
                .
        then().log().all()
                .statusCode(404).extract().response();
    }
}
