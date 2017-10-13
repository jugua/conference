package com.epam.cm.tests;

import com.epam.cm.base.*;
import com.epam.cm.jira.Jira;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;


import java.util.ArrayList;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.path.json.JsonPath.from;


public class GetPastConferencesTests extends SimpleBaseTest {

    @Test
    @Jira("6812")
    public void getPastConferencesNonLoggedUserTest(){

        Response endResponse =
        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .
        when()
                .get(EndpointUrl.PAST_CONFERENCE)
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

        Assert.assertTrue(jsonAsArrayList.size() > ConferenceConstants.LEAST_NUMBER_OF_CONFERENCES);

        for (Map m : jsonAsArrayList) {
            Assert.assertTrue(m.values().size() <= ConferenceConstants.FIELDS_NUMBER_OF_CONFERENCES_JSON);
        }
    }

    @Test
    @Jira("6811")
    public void getPastConferencesAdminTest(){

        Response endResponse =
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
        int pastConferencesCount = jsonAsArrayList.size();

        Assert.assertTrue(pastConferencesCount > ConferenceConstants.LEAST_NUMBER_OF_CONFERENCES);

        for (Map m : jsonAsArrayList) {
            int pastConferenceFieldsCount = m.values().size();
            Assert.assertTrue(pastConferenceFieldsCount <= ConferenceConstants.FIELDS_NUMBER_OF_CONFERENCES_JSON);
        }

    }

    @Test
    @Jira("6816")
    public void getPastConferencesOrganiserTest(){

        Response endResponse =
        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().basic(config.organiserUser, config.organiserPassword)
                .cookie(SimpleBaseTest.TOKEN, response.cookie(SimpleBaseTest.TOKEN))
                .header(SimpleBaseTest.X_TOKEN, response.cookie(SimpleBaseTest.TOKEN))
        .when()
                .get(EndpointUrl.PAST_CONFERENCE)
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
       int pastConferencesCount = jsonAsArrayList.size();

        Assert.assertTrue(pastConferencesCount > ConferenceConstants.LEAST_NUMBER_OF_CONFERENCES);

        for (Map m : jsonAsArrayList) {
            int pastConferenceFieldsCount = m.values().size();
            Assert.assertTrue(pastConferenceFieldsCount <= ConferenceConstants.FIELDS_NUMBER_OF_CONFERENCES_JSON);
        }
    }

    @Test
    @Jira("6815")
    public void getPastConferencesUserWithInvalidCredentialsTest(){

        Response endResponse =
        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth()
                .preemptive().basic(TextConstants.WRONG_USER, TextConstants.WRONG_PASSWORD)
        .when()
                .get(EndpointUrl.PAST_CONFERENCE)
        .then().log().all()
                .statusCode(401).extract().response();

        String jsonAsString = endResponse.getBody().asString();

        Assert.assertEquals(ConferenceConstants.LOGIN_AUTH_ERR, jsonAsString);
    }
}
