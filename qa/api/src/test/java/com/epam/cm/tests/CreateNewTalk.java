package com.epam.cm.tests;

import com.epam.cm.base.SimpleBaseTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class CreateNewTalk extends SimpleBaseTest {
    public static Response endresp;
    @Test
    public void createNewTalk(){

        given()
                .contentType(ContentType.URLENC)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.speakerUser, config.speakerPassword)
                .cookie("XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .header("X-XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .formParam("title","TestTalk1")
                .formParam("description","this is test talk for automate test")
                .formParam("topic","ML")
                .formParam("type","Lighting Talk")
                .formParam("lang","English")
                .formParam("level","Beginner")
                .formParam("status","New")
                .formParam("date","1506419805177").
        when()
                .post( "/api/talk")
                .
        then().log().all()
                .statusCode(200).assertThat()
                .body("error", Matchers.nullValue())
                .body("secondsToExpiry", Matchers.nullValue())
                .body("result", Matchers.nullValue())
                .body("id", Matchers.is(Integer.class))
                .body("fields", Matchers.nullValue())
                .extract().response();
    }
}
