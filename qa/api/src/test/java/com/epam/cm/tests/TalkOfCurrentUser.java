package com.epam.cm.tests;

import com.epam.cm.base.SimpleBaseTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static com.epam.cm.base.EndpointUrl.TALK;
import static com.epam.cm.base.TextConst.*;
import static io.restassured.RestAssured.given;

public class TalkOfCurrentUser extends SimpleBaseTest {

public static Response endresp;

    @Test
    public void talkOfCurrentUserIfOrganiser(){
        endresp =
        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.organiserUser, config.organiserPassword)
                .cookie(TOKEN, response.cookie(TOKEN))
                .header(XTOKEN, response.cookie(TOKEN))
                .
        when()
                .get(TALK + "/33")
                .
        then().log().all()
                .statusCode(200)
                .body(TITLE, Matchers.notNullValue())
                .body(DESCRIPTION, Matchers.notNullValue())
                .body(DATE, Matchers.notNullValue())
                .extract().response();

            assert (endresp.path(LANG).equals(ENGLISH)||endresp.path(LANG).equals(RUSSIAN)||endresp.path(LANG).equals(UKRAINIAN));

            assert (endresp.path(LEVEL).equals(BEGINNER)||endresp.path(LEVEL).equals(EXPERT)||endresp.path(LEVEL).equals(ADVANCED)||endresp.path(LEVEL).equals(INTERMEDIATE));

            assert (endresp.path(TOPIC).equals(ARCHITECTURE)||endresp.path(TOPIC).equals(BIGDATA)||endresp.path(TOPIC).equals(JVM)||endresp.path(TOPIC).equals(ML)||endresp.path(TOPIC).equals(SOFTWARE)||endresp.path(TOPIC).equals(WEBDEVELOPMENT)||endresp.path(TOPIC).equals(NEWTALKTOPIC));

    }

    @Test @Ignore
    public void talkOfCurrentUserIfSpeaker(){

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.speakerUser, config.speakerPassword)
                .cookie(TOKEN, response.cookie(TOKEN))
                .header(XTOKEN, response.cookie(TOKEN))
                .
                        when()
                .get(TALK + "/60")
                .
                        then().log().all()
                .statusCode(200)
                .body(TITLE, Matchers.notNullValue())
                .body(DESCRIPTION, Matchers.notNullValue())
                .body(DATE, Matchers.notNullValue())
                .extract().response();

        assert (endresp.path(LANG).equals(ENGLISH)||endresp.path(LANG).equals(RUSSIAN)||endresp.path(LANG).equals(UKRAINIAN));

        assert (endresp.path(LEVEL).equals(BEGINNER)||endresp.path(LEVEL).equals(EXPERT)||endresp.path(LEVEL).equals(ADVANCED)||endresp.path(LEVEL).equals(INTERMEDIATE));

        assert (endresp.path(TOPIC).equals(ARCHITECTURE)||endresp.path(TOPIC).equals(BIGDATA)||endresp.path(TOPIC).equals(JVM)||endresp.path(TOPIC).equals(ML)||endresp.path(TOPIC).equals(SOFTWARE)||endresp.path(TOPIC).equals(WEBDEVELOPMENT)||endresp.path(TOPIC).equals(NEWTALKTOPIC));

    }
}
