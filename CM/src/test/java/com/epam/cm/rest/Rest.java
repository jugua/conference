package com.epam.cm.rest;


import com.jayway.restassured.http.ContentType;
import net.thucydides.core.annotations.Step;

import java.util.LinkedHashMap;
import java.util.Map;

import static net.serenitybdd.rest.SerenityRest.given;
import static net.serenitybdd.rest.SerenityRest.then;

/**
 * Created by Denys_Shmyhin on 11/13/2016.
 */
public class Rest  {

    public static void main(String[] args) {
        restTest();
    }
    public static void  restTest(){
        Map<String, Object> jsonAsMap = new LinkedHashMap<>();
        jsonAsMap.put("mail", "tester@tester.com");
        jsonAsMap.put("password", "tester");
        String  asd =jsonAsMap.toString();
        given()
                .contentType(ContentType.JSON)
                .body(jsonAsMap)
        .when()
                .post("http://localhost:5000/api/login")
        .then()
                .statusCode(200);

    }
}
