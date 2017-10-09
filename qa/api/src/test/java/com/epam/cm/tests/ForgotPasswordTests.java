package com.epam.cm.tests;

import com.epam.cm.base.SimpleBaseTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Test;

import static io.restassured.RestAssured.given;

/**
 * Created by Mariia_Koltsova on 10/6/2017.
 */
public class ForgotPasswordTests extends SimpleBaseTest {

    @Test
    public void positiveForgotPasswordTest(){
        //Response response1 =
                given()
                        .contentType(ContentType.JSON)
                        .baseUri(config.baseHost)
                        .auth().preemptive().basic(config.speakerUser, config.speakerPassword)
                        .cookie("XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                        .header("X-XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                        .body("{\"mail\":\"test@test.com\"}")
                        .
                                when()
                        .post( "/api/forgot-password")
                        .
                                then().log().all()
                        .statusCode(200).extract().response();

    }

    @Test
    public void negativeForgotPasswordTest(){
        //Response response1 =
        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.speakerUser, config.speakerPassword)
                .cookie("XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .header("X-XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .body("{\"mail\":\"test1@t.com\"}")
                .
                        when()
                .post( "/api/forgot-password")
                .
                        then().log().all()
                .statusCode(400).extract().response();

    }


}
