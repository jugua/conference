package com.epam.cm.tests;

import com.epam.cm.base.SimpleBaseTest;
import io.restassured.filter.cookie.CookieFilter;
import io.restassured.filter.session.SessionFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Ignore;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.sessionId;

/**
 * Created by Mariia_Koltsova on 10/5/2017.
 */
public class LogoutTests extends SimpleBaseTest {


    @Test
    @Ignore
    public void positiveLogoutTest(){

         CookieFilter cookieFilter = new CookieFilter();

         Response response1 =
        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.speakerUser, config.speakerPassword)
                .cookie("XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .header("X-XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .filter(cookieFilter)

                .
                        when()
                .post( "/api/login")
                    .then().log().all()
                .statusCode(200).extract().response();


        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .filter(cookieFilter)
                .cookie("XSRF-TOKEN", response1.cookie("XSRF-TOKEN"))
                .header("X-XSRF-TOKEN", response1.cookie("XSRF-TOKEN"))


                .   when()
                .post( "/api/logout")
                .then().log().all()
                .statusCode(200).extract().response();

    }



}
