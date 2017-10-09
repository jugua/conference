package com.epam.cm.tests;

import com.epam.cm.base.SimpleBaseTest;
import io.restassured.http.ContentType;
import org.junit.Test;

import static io.restassured.RestAssured.given;


public class UpcomingConferencesTests extends SimpleBaseTest {

    @Test
    public void getUpcomingConferencesNonLoggedUserTest(){

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .
        when()
                .get( "/api/conference/upcoming")
                .
        then().log().all()
                .statusCode(200);
    }

    @Test
    public void getUpcomingConferencesAdminTest(){

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().basic(config.adminUser, config.adminPassword)
                .cookie("XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .header("X-XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .
        when()
                .get( "/api/conference/upcoming")
                .
        then().log().all()
                .statusCode(200);
    }

    @Test
    public void getUpcomingConferencesOrganiserTest(){

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().basic(config.organiserUser, config.organiserPassword)
                .cookie("XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .header("X-XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
        .when()
                .get( "/api/conference/upcoming")
        .then().log().all()
                .statusCode(200);
    }

    @Test
    public void getUpcomingConferencesUserWithInvalidCredentialsTest(){

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth()
                .preemptive().basic(config.wrongUser,config.wrongPassword)
        .when()
                .get( "/api/conference/upcoming")
        .then().log().all()
                .statusCode(401);
    }
}
