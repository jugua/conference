package com.epam.cm.tests;

import io.restassured.http.ContentType;
import org.junit.Test;
import com.epam.cm.base.SimpleBaseTest;

import static io.restassured.RestAssured.given;


public class PastConferencesTests extends SimpleBaseTest {

    @Test
    public void getPastConferencesNonLoggedUserTest(){

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .
        when()
                .get( "/api/conference/past")
                .
        then().log().all()
                .statusCode(200);
    }

    @Test
    public void getPastConferencesAdminTest(){

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().basic(config.adminUser, config.adminPassword)
                .cookie("XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .header("X-XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .
        when()
                .get( "/api/conference/past")
                .
        then().log().all()
                .statusCode(200);
    }

    @Test
    public void getPastConferencesOrganiserTest(){

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().basic(config.organiserUser, config.organiserPassword)
                .cookie("XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .header("X-XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
        .when()
                .get( "/api/conference/past")
        .then().log().all()
                .statusCode(200);
    }

    @Test
    public void getPastConferencesUserWithInvalidCredentialsTest(){

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth()
                .preemptive().basic(config.wrongUser,config.wrongPassword)
        .when()
                .get( "/api/conference/past")
        .then().log().all()
                .statusCode(401);
    }
}
