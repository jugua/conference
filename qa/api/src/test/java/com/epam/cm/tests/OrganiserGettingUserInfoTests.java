package com.epam.cm.tests;

import com.epam.cm.base.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Test;


import static io.restassured.RestAssured.config;
import static io.restassured.RestAssured.given;

public class OrganiserGettingUserInfoTests extends SimpleBaseTest{

    //6662
    @Test
    public void positiveOrganiserGettingExistingUserTest(){
        endResponse =
            given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth(). preemptive().basic(config.organiserUser,config.organiserPassword)
                .cookie(XSRF_TOKEN, response.cookie(XSRF_TOKEN))
                .header(X_XSRF_TOKEN, response.cookie(XSRF_TOKEN))
                .
                        when()
                .get(EndPointURL.API_USER_EXISTING)
                .
                        then().log().all()
                .statusCode(200)
                .extract().response();


    }

    //6794
    @Test
    public void negativeNonLoggedUserGettingExistingUserTest(){

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth(). preemptive().basic(config.wrongUser,config.wrongPassword)
                .cookie(XSRF_TOKEN, response.cookie(XSRF_TOKEN))
                .header(X_XSRF_TOKEN, response.cookie(XSRF_TOKEN))
                .
                        when()
                .get(EndPointURL.API_USER_EXISTING)
                .
                        then().log().all()
                .statusCode(401);



    }

    //6666
    @Test
    public void negativeOrganiserGettingNonExistingUserTest(){

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth(). preemptive().basic(config.organiserUser,config.organiserPassword)
                .cookie(XSRF_TOKEN, response.cookie(XSRF_TOKEN))
                .header(X_XSRF_TOKEN, response.cookie(XSRF_TOKEN))
                .
                        when()
                .get( EndPointURL.API_USER_NONEXISTING)
                .
                        then().log().all()
                .statusCode(404);



    }
}
