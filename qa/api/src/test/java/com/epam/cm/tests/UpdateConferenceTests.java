package com.epam.cm.tests;

import com.epam.cm.base.*;
import io.restassured.http.ContentType;
import org.junit.Test;

import static io.restassured.RestAssured.given;



public class UpdateConferenceTests extends SimpleBaseTest {

    //        List<Integer> id = endResponse.path("id");

    //6866
    @Test
    public void getUpcomingConferencesAdminTest(){

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.adminUser, config.adminPassword)
                .cookie(SimpleBaseTest.XSRF_TOKEN, response.cookie(SimpleBaseTest.XSRF_TOKEN))
                .header(SimpleBaseTest.X_XSRF_TOKEN, response.cookie(SimpleBaseTest.XSRF_TOKEN))
                .body(ConferenceConstants.CONFERENCE_BODY_JSON)
                .
        when()
                .patch(EndPointURL.UPDATE_CONFERENCE)
                .
        then().log().all()
                .statusCode(200);
    }


    //6864
    // Test is failed on old server
    // Bug ID: EPMFARMKPP-6895
    // Link to Bug: https://jirapct.epam.com/jira/browse/EPMFARMKPP-6895
    @Test
    public void getUpcomingConferencesOrganiserTest(){

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.organiserUser, config.organiserPassword)
                .cookie(SimpleBaseTest.XSRF_TOKEN, response.cookie(SimpleBaseTest.XSRF_TOKEN))
                .header(SimpleBaseTest.X_XSRF_TOKEN, response.cookie(SimpleBaseTest.XSRF_TOKEN))
                .body(ConferenceConstants.CONFERENCE_BODY_JSON)
                .
        when()
                .patch(EndPointURL.UPDATE_CONFERENCE)
                .
        then().log().all()
                .statusCode(200);
    }


    //6863
    @Test
    public void getUpcomingConferencesUserWithInvalidCredentialsTest(){

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.wrongUser,config.wrongPassword)
                .cookie(SimpleBaseTest.XSRF_TOKEN, response.cookie(SimpleBaseTest.XSRF_TOKEN))
                .header(SimpleBaseTest.X_XSRF_TOKEN, response.cookie(SimpleBaseTest.XSRF_TOKEN))
                .body(ConferenceConstants.CONFERENCE_BODY_JSON)
                .
        when()
                .patch(EndPointURL.UPDATE_CONFERENCE)
                .
        then().log().all()
                .statusCode(401);
    }
}
