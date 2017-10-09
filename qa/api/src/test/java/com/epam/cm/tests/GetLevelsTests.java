package com.epam.cm.tests;

import com.epam.cm.base.SimpleBaseTest;
import io.restassured.http.ContentType;
import org.junit.Test;

import static io.restassured.RestAssured.given;

/**
 * Created by Mariia_Koltsova on 10/5/2017.
 */
public class GetLevelsTests extends SimpleBaseTest{

    @Test
    public void positiveGetLevelsTest(){

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.adminUser, config.adminPassword)
                .cookie("XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .header("X-XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .
                        when()
                .get( "/api/level")
                .
                        then().log().all()
                .statusCode(200).extract().response();

    }

    @Test
    public void negativeGetLevelsTest(){

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.invalidUser, config.invalidPassword)
                .cookie("XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .header("X-XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .
                        when()
                .get( "/api/level")
                .
                        then().log().all()
                .statusCode(401).extract().response();

    }

}
