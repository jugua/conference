package com.epam.cm.tests;

import com.epam.cm.base.SimpleBaseTest;
import io.restassured.http.ContentType;
import org.junit.Test;


import static io.restassured.RestAssured.config;
import static io.restassured.RestAssured.given;

public class GettingUserInfoTests extends SimpleBaseTest{

    @Test
    public void positiveOrganiserGettingExistingUserTest(){

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth(). preemptive().basic(config.organiserUser,config.organiserPassword)
                .cookie("XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .header("X-XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .
                        when()
                .get( "api/user/1")
                .
                        then().log().all()
                .statusCode(200);



    }

    @Test
    public void negativeNonLoggedUserGettingExistingUserTest(){

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth(). preemptive().basic(config.wrongUser,config.wrongPassword)
                .cookie("XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .header("X-XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .
                        when()
                .get( "api/user/1")
                .
                        then().log().all()
                .statusCode(401);



    }

    @Test
    public void negativeOrganiserGettingNonExistingUserTest(){

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth(). preemptive().basic(config.organiserUser,config.organiserPassword)
                .cookie("XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .header("X-XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .
                        when()
                .get( "api/user/500")
                .
                        then().log().all()
                .statusCode(404);



    }
}
