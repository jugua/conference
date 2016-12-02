package com.epam.cm.rest;


import com.jayway.restassured.http.ContentType;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Step;
import org.junit.Test;
import org.junit.internal.runners.JUnit38ClassRunner;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import static net.serenitybdd.rest.SerenityRest.given;

@RunWith(SerenityRunner.class)
public class Rest  {


    @Test
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

    @Test
    public  void logout(){

        Map<String, Object> jsonAsMap = new LinkedHashMap<>();
        jsonAsMap.put("token", "583c1601a1613bf076f5f6b443929");

        given()
                .contentType(ContentType.JSON)
                .headers(jsonAsMap)
                .when()
                .get("http://localhost:5000/api/logout")

                .then().log().all().statusCode(200);


    }
    
}
