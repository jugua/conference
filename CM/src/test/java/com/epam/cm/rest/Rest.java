package com.epam.cm.rest;

import java.util.LinkedHashMap;
import java.util.Map;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Cookies;
import com.jayway.restassured.response.Header;
import com.jayway.restassured.response.Response;

import net.serenitybdd.junit.runners.SerenityRunner;

import org.junit.Test;
import org.junit.runner.RunWith;

import static com.jayway.restassured.http.ContentType.JSON;

@RunWith(SerenityRunner.class)
public class Rest {

    Map<String, String> userCookie;

    @Test
    public void loginAsUser() {
        RestAssured.baseURI = "http://10.17.132.37:8050";
        RestAssured.basePath = "/api";
        Map<String, String> userCookie;

        // stage 1: get "XSRF-TOKEN"
        Response resp = RestAssured.given().log().all().get();

        userCookie = new LinkedHashMap<>(resp.cookies());
        // stage 2: get new "JSESSIONID"
        String newJsessionId = RestAssured.given().auth().preemptive().basic("tester@tester.com", "tester")
                .headers("X-XSRF-TOKEN", resp.getCookie("XSRF-TOKEN")).cookies(resp.cookies()).contentType(JSON).log()
                .all().post("/login").then().log().all().statusCode(200).extract().cookie("JSESSIONID");

        userCookie.put("JSESSIONID", newJsessionId);
        // stage 3: update "XSRF-TOKEN" for logged user
        String newXsrfToken = RestAssured.given().contentType(JSON).cookies(userCookie).log().all().get("/user/current")
                .then().log().all().statusCode(202).extract().cookie("XSRF-TOKEN");

        userCookie.put("XSRF-TOKEN", newXsrfToken);
        //

        userCookie.entrySet().forEach(System.out::println);

    }

    @Test
    public void createTalk() {

        RestAssured.baseURI = "http://10.17.132.37:8050";
        RestAssured.basePath = "/api";
        Map<String, String> userCookie;

        // stage 1: get "XSRF-TOKEN"
        Response resp = RestAssured.given().log().all().get();

        Cookies cookies = resp.getDetailedCookies();

        userCookie = new LinkedHashMap<>(resp.cookies());
        // stage 2: get new "JSESSIONID"
        Response response2 = RestAssured.given().auth().preemptive().basic("tester@tester.com", "tester")
                .headers("X-XSRF-TOKEN", cookies.get("XSRF-TOKEN")).cookies(cookies)
                .contentType("application/json;charset=UTF-8").log().all().post("/login").then().log().all()
                .statusCode(200).extract().response();

        userCookie.putAll(response2.cookies());

        // stage 3: update "XSRF-TOKEN" for logged user
        Response response3 = RestAssured.given()
                .contentType("application/json;charset=UTF-8")
                .header(new Header("X-XSRF-TOKEN", cookies.get("X-XSRF-TOKEN").getValue()))
                .cookies(cookies)
                .log()
                .all().get("/user/current").then().log().all().statusCode(202).extract().response();

        userCookie.putAll(response3.cookies());

        // Response resp2 = RestAssured.given().log().all().get();

        // stage 3: update "XSRF-TOKEN" for logged user

        Response response4 = RestAssured.given().contentType("application/json;charset=UTF-8").header(new Header("X-XSRF-TOKEN", response3.getCookie("X-XSRF-TOKEN"))).cookies(response3.getDetailedCookies()).log()
                .all().post("/logout").then().statusCode(204).log().all().extract().response();

        userCookie.entrySet().forEach(System.out::println);
    }

}
