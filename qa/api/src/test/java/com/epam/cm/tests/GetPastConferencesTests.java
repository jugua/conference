package com.epam.cm.tests;

import com.epam.cm.base.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.path.json.JsonPath.from;


public class GetPastConferencesTests extends SimpleBaseTest {
    int pastConferencesCount = 0;
    int pastConferenceFieldsCount = 0;


    @Before
    public void setCountersToZero() {
        pastConferencesCount = 0;
        pastConferenceFieldsCount = 0;
    }

    //6812
    @Test
    public void getPastConferencesNonLoggedUserTest(){

        endResponse =
        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .
        when()
                .get(EndPointURL.PAST_CONFERENCE)
                .
        then().log().all()
                .statusCode(200).assertThat()
                .body("id", Matchers.notNullValue())
                .body("title", Matchers.notNullValue())
                .body("description", Matchers.notNullValue())
                .body("location", Matchers.notNullValue())
                .body("start_date", Matchers.notNullValue())
                .body("end_date", Matchers.notNullValue())
                .body("call_for_paper_start_date", Matchers.notNullValue())
                .body("call_for_paper_end_date", Matchers.notNullValue())
                .body("cfp_active", Matchers.notNullValue())
                .extract().response();

        String jsonAsString = endResponse.getBody().asString();

        ArrayList<Map<String,?>> jsonAsArrayList = from(jsonAsString).get("");

        for (Map m : jsonAsArrayList) {
            pastConferencesCount++;
            pastConferenceFieldsCount = m.values().size();
        }

        Assert.assertTrue(pastConferencesCount > ConferenceConstants.LEAST_NUMBER_OF_CONFERENCES);
        Assert.assertTrue(pastConferenceFieldsCount <= ConferenceConstants.FIELDS_NUMBER_OF_CONFERENCES_JSON);
    }


    //6811
    @Test
    public void getPastConferencesAdminTest(){

        endResponse =
        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().basic(config.adminUser, config.adminPassword)
                .cookie(SimpleBaseTest.XSRF_TOKEN, response.cookie(SimpleBaseTest.XSRF_TOKEN))
                .header(SimpleBaseTest.X_XSRF_TOKEN, response.cookie(SimpleBaseTest.XSRF_TOKEN))
                .
        when()
                .get(EndPointURL.PAST_CONFERENCE)
                .
        then().log().all()
                .statusCode(200).assertThat()
                .body("id", Matchers.notNullValue())
                .body("title", Matchers.notNullValue())
                .body("description", Matchers.notNullValue())
                .body("location", Matchers.notNullValue())
                .body("start_date", Matchers.notNullValue())
                .body("end_date", Matchers.notNullValue())
                .body("call_for_paper_start_date", Matchers.notNullValue())
                .body("call_for_paper_end_date", Matchers.notNullValue())
                .body("cfp_active", Matchers.notNullValue())
                .extract().response();

        String jsonAsString = endResponse.getBody().asString();

        ArrayList<Map<String,?>> jsonAsArrayList = from(jsonAsString).get("");

        for (Map m : jsonAsArrayList) {
            pastConferencesCount++;
            pastConferenceFieldsCount = m.values().size();
        }

        Assert.assertTrue(pastConferencesCount > ConferenceConstants.LEAST_NUMBER_OF_CONFERENCES);
        Assert.assertTrue(pastConferenceFieldsCount <= ConferenceConstants.FIELDS_NUMBER_OF_CONFERENCES_JSON);

    }

    //6816
    @Test
    public void getPastConferencesOrganiserTest(){

        endResponse =
        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().basic(config.organiserUser, config.organiserPassword)
                .cookie(SimpleBaseTest.XSRF_TOKEN, response.cookie(SimpleBaseTest.XSRF_TOKEN))
                .header(SimpleBaseTest.X_XSRF_TOKEN, response.cookie(SimpleBaseTest.XSRF_TOKEN))
        .when()
                .get(EndPointURL.PAST_CONFERENCE)
        .then().log().all()
                .statusCode(200).assertThat()
                .body("id", Matchers.notNullValue())
                .body("title", Matchers.notNullValue())
                .body("description", Matchers.notNullValue())
                .body("location", Matchers.notNullValue())
                .body("start_date", Matchers.notNullValue())
                .body("end_date", Matchers.notNullValue())
                .body("call_for_paper_start_date", Matchers.notNullValue())
                .body("call_for_paper_end_date", Matchers.notNullValue())
                .body("cfp_active", Matchers.notNullValue())
                .extract().response();

        String jsonAsString = endResponse.getBody().asString();

        ArrayList<Map<String,?>> jsonAsArrayList = from(jsonAsString).get("");

        for (Map m : jsonAsArrayList) {
            pastConferencesCount++;
            pastConferenceFieldsCount = m.values().size();
        }

        Assert.assertTrue(pastConferencesCount > ConferenceConstants.LEAST_NUMBER_OF_CONFERENCES);
        Assert.assertTrue(pastConferenceFieldsCount <= ConferenceConstants.FIELDS_NUMBER_OF_CONFERENCES_JSON);
    }

    //6815
    @Test
    public void getPastConferencesUserWithInvalidCredentialsTest(){

        endResponse =
        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth()
                .preemptive().basic(config.wrongUser,config.wrongPassword)
        .when()
                .get(EndPointURL.PAST_CONFERENCE)
        .then().log().all()
                .statusCode(401).extract().response();

        String jsonAsString = endResponse.getBody().asString();

        Assert.assertEquals(ConferenceConstants.LOGIN_AUTH_ERR, jsonAsString);
    }
}
