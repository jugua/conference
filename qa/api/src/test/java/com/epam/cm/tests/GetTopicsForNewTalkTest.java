package com.epam.cm.tests;

import com.epam.cm.base.SimpleBaseTest;
import io.restassured.http.ContentType;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class GetTopicsForNewTalkTest extends SimpleBaseTest{

    @Test //6690
    public void positiveGetTypesTest(){

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.speakerUser, config.speakerPassword)
                .cookie("XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .header("X-XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .
        when()
                .get( "/api/topic")
                .

        then().log().all()
                .statusCode(200);
    }

    @Test //6693
    public void negativeGetTypesTest(){

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .cookie("XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .header("X-XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .
        when()
                .get( "/api/topic")
                .

        then().log().all()
                .statusCode(401);
    }
}
