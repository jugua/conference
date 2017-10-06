package com.epam.cm.tests;

import com.epam.cm.base.SimpleBaseTest;
import io.restassured.http.ContentType;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class AddNewTopicTest extends SimpleBaseTest {

    @Test //6679
    public void positiveAddNewTalkTypeTest(){

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.adminUser, config.adminPassword)
                .cookie("XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .header("X-XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .body("{\"name\": \"New talk topic test\"}")
                .

        when()
                .post( "/api/topic")
                .
        then().log().all()
                .statusCode(200);
    }

    @Test //6681
    public void negativeAddNewTalkTypeTest(){

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.speakerUser, config.speakerPassword)
                .cookie("XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .header("X-XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .body("{\"name\": \"New talk topic test\"}")
                .

        when()
                .post( "/api/topic")
                .
        then().log().all()
                .statusCode(401);
    }
}
