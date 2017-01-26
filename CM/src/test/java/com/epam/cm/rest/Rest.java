package com.epam.cm.rest;


import com.epam.cm.dto.restDto.TalksRestDTO;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.webdriver.ThucydidesWebDriverSupport;
import org.junit.Test;
import org.junit.internal.runners.JUnit38ClassRunner;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.openqa.selenium.Cookie;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import static com.jayway.restassured.http.ContentType.JSON;
import static net.serenitybdd.rest.SerenityRest.given;

@RunWith(SerenityRunner.class)
public class Rest {

    Map<String, String> userCookie;

    @Test
    public void loginAsUser(){
        RestAssured.baseURI = "http://10.17.132.37:8050";
        RestAssured.basePath = "/api";
        Map<String, String> userCookie;

        //stage 1: get "XSRF-TOKEN"
        Response resp = RestAssured.given().log().all().get();

        userCookie = new LinkedHashMap<>(resp.cookies());
        //stage 2:  get new "JSESSIONID"
        String newJsessionId =
                RestAssured.given().auth().preemptive().
                        basic("tester@tester.com", "tester").
                        headers("X-XSRF-TOKEN", resp.getCookie("XSRF-TOKEN")).
                        cookies(resp.cookies()).
                        contentType(JSON).log().all().
                        post("/login").
                        then().log().all().statusCode(200).
                        extract().cookie("JSESSIONID");

        userCookie.put("JSESSIONID", newJsessionId);
        //stage 3: update "XSRF-TOKEN" for  logged user
        String newXsrfToken =
                RestAssured.given().contentType(JSON).
                        cookies(userCookie).log().all().
                        get("/user/current").
                        then().log().all().statusCode(202).
                        extract().cookie("XSRF-TOKEN");

        userCookie.put("XSRF-TOKEN", newXsrfToken);
        //

        userCookie.entrySet().forEach(System.out::println);

    }

    @Test
    public void createTalk() {

        RestAssured.baseURI = "http://10.17.132.37:8050";
        RestAssured.basePath = "/api";
        Map<String, String> userCookie;

        //stage 1: get "XSRF-TOKEN"
        Response resp = RestAssured.given().log().all().get();

        userCookie = new LinkedHashMap<>(resp.cookies());
        //stage 2:  get new "JSESSIONID"
        String newJsessionId =
                RestAssured.given().auth().preemptive().
                        basic("tester@tester.com", "tester").
                        headers("X-XSRF-TOKEN", resp.getCookie("XSRF-TOKEN")).
                        cookies(resp.cookies()).
                        contentType(JSON).log().all().
                        post("/login").
                        then().log().all().statusCode(200).
                        extract().cookie("JSESSIONID");

        userCookie.put("JSESSIONID", newJsessionId);
        //stage 3: update "XSRF-TOKEN" for  logged user
        String newXsrfToken =
                RestAssured.given().contentType(JSON).
                        cookies(userCookie).log().all().
                        get("/user/current").
                        then().log().all().statusCode(202).
                        extract().cookie("XSRF-TOKEN");

        userCookie.put("XSRF-TOKEN", newXsrfToken);

        //Response resp2 = RestAssured.given().log().all().get();

        //stage 3: update "XSRF-TOKEN" for  logged user

        System.out.println(resp.getCookies());
       // System.out.println(resp2.getCookies());
        System.out.println(userCookie.toString());




        String logout = RestAssured.given().contentType(JSON).
                cookies(userCookie).log().all().
                post("/logout").
                then().log().all().statusCode(204).extract().statusLine();


        userCookie.entrySet().forEach(System.out::println);
    }




    @Test
    public void fp() {
        RestAssured.baseURI = "http://10.17.132.37:8050";
        RestAssured.basePath = "/api";
        Map<String, String> userCookie;

        ThucydidesWebDriverSupport.getDriver().get("http://10.17.132.37:8050");

        //stage 1: get "XSRF-TOKEN"
        Response resp = RestAssured.given().log().all().get();
        System.out.println(resp.getCookies());
        userCookie = new LinkedHashMap<>(resp.cookies());


        String sessionId = String.valueOf(ThucydidesWebDriverSupport.getDriver().manage().getCookieNamed("JSESSIONID"));
        String token = String.valueOf(ThucydidesWebDriverSupport.getDriver().manage().getCookieNamed("XSRF-TOKEN"));

        userCookie.put("XSRF-TOKEN", token);
        userCookie.put("JSESSIONID", sessionId);

        RestAssured.given().contentType(JSON).body("mail: \"tester@tester.com\"").
                /*headers("X-XSRF-TOKEN", resp.getCookie("XSRF-TOKEN")).*/
                cookies(userCookie).log().all().post("/forgot-password").
                then().statusCode(200).log().all();
    }

}
