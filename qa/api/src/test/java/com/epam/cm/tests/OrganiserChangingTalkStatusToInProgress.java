package com.epam.cm.tests;

import com.epam.cm.base.*;
import io.restassured.http.ContentType;
import org.junit.Test;

import static io.restassured.RestAssured.config;
import static io.restassured.RestAssured.given;

public class OrganiserChangingTalkStatusToInProgress extends SimpleBaseTest {

    @Test
    public void positiveOrganiserGettingExistingUserTest(){

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth(). preemptive().basic(config.organiserUser,config.organiserPassword)
                .cookie(XSRF_TOKEN, response.cookie(XSRF_TOKEN))
                .header(X_XSRF_TOKEN, response.cookie(XSRF_TOKEN))
                .
                        when()
                .get( "api/user/1")
                .
                        then().log().all()
                .statusCode(200);



    }

}
