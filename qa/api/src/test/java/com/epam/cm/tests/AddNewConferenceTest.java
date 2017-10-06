package com.epam.cm.tests;

import io.restassured.http.ContentType;
import com.epam.cm.base.SimpleBaseTest;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class AddNewConferenceTest extends SimpleBaseTest{

    @Test //6622
    public void positiveAddNewConferenceTest(){

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.adminUser, config.adminPassword)
                .cookie("XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .header("X-XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .body("{ \"title\": \"TestConferenceBlaBla\", \"description\": \"Test\", \"location\": \"Kyiv\", \"start_date\": \"2017-10-06\", \"end_date\": \"2017-11-06\", \"no_dates\": \"true\", \"cfp_start_date\": \"2017-10-06\", \"cfp_end_date\": \"2017-11-06\", \"cfp_no_dates\": \"true\", \"topics\": [1,2,3,4], \"types\": [4,2], \"languages\": [1,3], \"levels\": [4,3], \"organisers\": [3, 4, 5] }")
                .
        when()
                .post( "/api/conference")
                .

        then().log().all()
                .statusCode(200);
    }

    @Test //6659
    public void negativeAddNewConferenceTest(){

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.speakerUser, config.speakerPassword)
                .cookie("XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .header("X-XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .body("{ \"title\": \"TestConferenceBla\", \"description\": \"Test\", \"location\": \"Kyiv\", \"start_date\": \"2017-10-06\", \"end_date\": \"2017-11-06\", \"no_dates\": \"true\", \"cfp_start_date\": \"2017-10-06\", \"cfp_end_date\": \"2017-11-06\", \"cfp_no_dates\": \"true\", \"topics\": [1,2,3,4], \"types\": [4,2], \"languages\": [1,3], \"levels\": [4,3], \"organisers\": [3, 4, 5] }")
                .
        when()
                .post( "/api/conference")
                .

        then().log().all()
                .statusCode(401);
    }

    @Test //6822
    public void negativeValidationFailedAddNewConferenceTest(){

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.speakerUser, config.speakerPassword)
                .cookie("XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .header("X-XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .body("{ \"title\": \"\", \"description\": \"\", \"location\": \"\", \"start_date\": \"\", \"end_date\": \"2017-11-29\", \"no_dates\": \"true\", \"cfp_start_date\": \"2017-09-09\", \"cfp_end_date\": \"2017-11-29\", \"cfp_no_dates\": \"true\", \"topics\": [1,2,3,4], \"types\": [4,2], \"languages\": [1,3], \"levels\": [4,3], \"organisers\": [3, 4, 5] }")
                .
                        when()
                .post( "/api/conference")
                .

                        then().log().all()
                .statusCode(400);
    }
}
