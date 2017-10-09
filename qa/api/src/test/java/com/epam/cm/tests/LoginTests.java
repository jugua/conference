package com.epam.cm.tests;

import com.epam.cm.base.SimpleBaseTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class LoginTests extends SimpleBaseTest {


    @Test
    public void positiveLoginTest(){
        Response response1 =
        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.speakerUser, config.speakerPassword)
                .cookie("XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .header("X-XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .
                        when()
                .post( "/api/login")
                .
                        then().log().all()
                .statusCode(200).extract().response();

    }
    @Test
    public void negativeLoginTestInvalidLogin(){


        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic("gdgdyt873@eu.co", config.organiserPassword)
                .cookie("XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .header("X-XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .
                        when()
                .post( "/api/login")
                .
                        then().log().all()
                .statusCode(401).extract().response();



    }

    @Test
    public void negativeLoginTestInvalidPass(){

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.organiserUser, "732723tf")
                .cookie("XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .header("X-XSRF-TOKEN", response.cookie("XSRF-TOKEN"))

                .
                        when()
                .post( "/api/login")
                .
                        then().log().all()
                .statusCode(401).extract().response();

    }
    @Test
    public void negativeLoginTestEmptyFields(){

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic("", "")
                .cookie("XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .header("X-XSRF-TOKEN", response.cookie("XSRF-TOKEN"))

                .
                        when()
                .post( "/api/login")
                .
                        then().log().all()
                .statusCode(401).extract().response();

    }
}
