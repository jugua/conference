package com.epam.cm.rest;


import com.jayway.restassured.http.ContentType;
import net.thucydides.core.annotations.Step;

import java.util.LinkedHashMap;
import java.util.Map;

import static net.serenitybdd.rest.SerenityRest.given;


public class Rest  {



    @Step
    public  void  login(){
        Map<String, Object> jsonAsMap = new LinkedHashMap<>();
        jsonAsMap.put("mail", "tester@tester.com");
        jsonAsMap.put("password", "tester");
        String  asd =jsonAsMap.toString();
        given()
                .contentType(ContentType.JSON)
                .body(jsonAsMap)
        .when()
                .post("http://localhost:5000/api/login")
        .then().statusCode(200);

    }

    @Step
    public  void logout(){

        Map<String, Object> jsonAsMap = new LinkedHashMap<>();
        jsonAsMap.put("token", "582ef531aac019ba7671148233973");

        given()
                .contentType(ContentType.JSON)
                .headers(jsonAsMap)
                .when()
                .get("http://localhost:5000/api/logout")
                .then().statusCode(200);


    }
}
