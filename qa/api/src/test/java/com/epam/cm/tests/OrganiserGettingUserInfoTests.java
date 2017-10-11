package com.epam.cm.tests;

import com.epam.cm.base.*;
import io.restassured.http.ContentType;
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
                .cookie(TOKEN, response.cookie(TOKEN))
                .header(XTOKEN, response.cookie(TOKEN))
                .
                        when()
                .get(EndpointUrl.USER_EXISTING)
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
                .cookie(TOKEN, response.cookie(TOKEN))
                .header(XTOKEN, response.cookie(TOKEN))
                .
                        when()
                .get(EndpointUrl.USER_EXISTING)
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
                .cookie(TOKEN, response.cookie(TOKEN))
                .header(XTOKEN, response.cookie(TOKEN))
                .
                        when()
                .get( EndpointUrl.USER_NONEXISTING)
                .
                        then().log().all()
                .statusCode(404);



    }
}
