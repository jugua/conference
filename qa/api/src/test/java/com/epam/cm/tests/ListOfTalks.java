package com.epam.cm.tests;

import com.epam.cm.base.EndpointUrl;
import com.epam.cm.base.SimpleBaseTest;
import com.epam.cm.base.TextConstants;
import com.epam.cm.jira.Jira;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.isOneOf;

public class ListOfTalks extends SimpleBaseTest {

    @Test //TODO good response check
    @Jira("6707")
    public void listOfTalksIfOrganiser(){

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.organiserUser, config.organiserPassword)
                .cookie(TOKEN, response.cookie(TOKEN))
                .header(X_TOKEN, response.cookie(TOKEN))
                .
        when()
                .get(EndpointUrl.TALK)
                .
        then().log().all()
                .statusCode(200)
                .assertThat()
                .body(TextConstants.TITLE, Matchers.notNullValue(), TextConstants.DESCRIPTION, Matchers.notNullValue());
    }

    @Test
    @Jira("6706")
    public void listOfTalksIfSpeaker(){

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.speakerUser, config.speakerPassword)
                .cookie(TOKEN, response.cookie(TOKEN))
                .header(X_TOKEN, response.cookie(TOKEN))
                .
        when()
                .get(EndpointUrl.TALK)
                .
        then().log().all()
                .statusCode(200)
                .assertThat()
                .body(TextConstants.TITLE, Matchers.notNullValue(), TextConstants.DESCRIPTION, Matchers.notNullValue());

//        List<String> language = endresp.path(LANG);
//        for (String lang:language){
//            assert (lang.contains(ENGLISH)||lang.contains(RUSSIAN)||lang.contains(UKRAINIAN));
//        }
//
//        List<String> level = endresp.path(LEVEL);
//        for (String lvl:level){
//            assert (lvl.contains(BEGINNER)||lvl.contains(EXPERT)||lvl.contains(ADVANCED)||lvl.contains(INTERMEDIATE));
//        }
//
//        List<String> topic = endresp.path(TOPIC);
//        for (String top:topic){
//            assert (top.contains(ARCHITECTURE)||top.contains(BIGDATA)||top.contains(JVM)||top.contains(ML)||top.contains(SOFTWARE)||top.contains(WEBDEVELOPMENT)||top.contains(NEWTALKTOPIC));
//        }
    }
}