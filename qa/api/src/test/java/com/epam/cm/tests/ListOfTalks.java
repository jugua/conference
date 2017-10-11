package com.epam.cm.tests;

import com.epam.cm.base.SimpleBaseTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Test;
import java.util.List;

import static com.epam.cm.base.EndpointUrl.TALK;
import static com.epam.cm.base.TextConst.*;
import static io.restassured.RestAssured.given;

public class ListOfTalks extends SimpleBaseTest {
    public static Response endresp;
    @Test
    public void listOfTalksIfOrganiser(){
        endresp =
        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.organiserUser, config.organiserPassword)
                .cookie(TOKEN, response.cookie(TOKEN))
                .header(XTOKEN, response.cookie(TOKEN))
                .
        when()
                .get(TALK)
                .
        then().log().all()
                .statusCode(200).assertThat()
                .body(TITLE, Matchers.notNullValue())
                .body(DESCRIPTION, Matchers.notNullValue())
                .body(DATE, Matchers.notNullValue())
                .extract().response();

        List<String> language = endresp.path(LANG);
        for (String lang:language){
            assert (lang.contains(ENGLISH)||lang.contains(RUSSIAN)||lang.contains(UKRAINIAN));
        }

        List<String> level = endresp.path(LEVEL);
        for (String lvl:level){
            assert (lvl.contains(BEGINNER)||lvl.contains(EXPERT)||lvl.contains(ADVANCED)||lvl.contains(INTERMEDIATE));
        }

        List<String> topic = endresp.path(TOPIC);
        for (String top:topic){
            assert (top.contains(ARCHITECTURE)||top.contains(BIGDATA)||top.contains(JVM)||top.contains(ML)||top.contains(SOFTWARE)||top.contains(WEBDEVELOPMENT)||top.contains(NEWTALKTOPIC));
        }

    }

    @Test
    public void listOfTalksIfSpeaker(){
        endresp =
        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.speakerUser, config.speakerPassword)
                .cookie(TOKEN, response.cookie(TOKEN))
                .header(XTOKEN, response.cookie(TOKEN))
                .
                        when()
                .get(TALK)
                .
                        then().log().all()
                .statusCode(200)
                .body(TITLE, Matchers.notNullValue())
                .body(DESCRIPTION, Matchers.notNullValue())
                .body(DATE, Matchers.notNullValue())
                .extract().response();

        List<String> language = endresp.path(LANG);
        for (String lang:language){
            assert (lang.contains(ENGLISH)||lang.contains(RUSSIAN)||lang.contains(UKRAINIAN));
        }

        List<String> level = endresp.path(LEVEL);
        for (String lvl:level){
            assert (lvl.contains(BEGINNER)||lvl.contains(EXPERT)||lvl.contains(ADVANCED)||lvl.contains(INTERMEDIATE));
        }

        List<String> topic = endresp.path(TOPIC);
        for (String top:topic){
            assert (top.contains(ARCHITECTURE)||top.contains(BIGDATA)||top.contains(JVM)||top.contains(ML)||top.contains(SOFTWARE)||top.contains(WEBDEVELOPMENT)||top.contains(NEWTALKTOPIC));
        }
    }
}
