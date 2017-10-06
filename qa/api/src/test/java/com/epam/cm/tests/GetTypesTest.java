package com.epam.cm.tests;

import com.epam.cm.base.SimpleBaseTest;
import io.restassured.http.ContentType;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class GetTypesTest extends SimpleBaseTest{

    @Test //6656
    public void positiveGetTypesTest(){

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.speakerUser, config.speakerPassword)
                .cookie("XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .header("X-XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .
        when()
                .get( "/api/type")
                .

        then().log().all()
                .statusCode(200);
    }

    @Test //6655
    public void negativeGetTypesTest(){

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .cookie("XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .header("X-XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .
        when()
                .get( "/api/type")
                .

        then().log().all()
                .statusCode(401);
    }
}