package com.epam.cm.tests;

import com.epam.cm.base.EndpointUrl;
import com.epam.cm.base.SimpleBaseTest;
import io.restassured.http.ContentType;
import org.junit.Test;

import static com.epam.cm.base.TextConstants.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasToString;

public class GetTypesTest extends SimpleBaseTest{

    @Test //6655
    public void positiveGetTypesTest(){

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.speakerUser, config.speakerPassword)
                .cookie(TOKEN, response.cookie(TOKEN))
                .header(XTOKEN, response.cookie(TOKEN))
                .
        when()
                .get(EndpointUrl.TYPE)
                .

        then().log().all()
                .statusCode(200)
                .assertThat().body(NAME,
                    hasItems(REGULAR, ONLINE, LIGHTING, HANDS_ON_LAB))
                .extract().response();
    }

    @Test //6656
    public void negativeGetTypesTest(){

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .cookie(TOKEN, response.cookie(TOKEN))
                .header(XTOKEN, response.cookie(TOKEN))
                .

        when()
                .get(EndpointUrl.TYPE)
                .

            then().log().all()
                .statusCode(401)
                .assertThat().body(ERROR, hasToString(UNAUTHORIZED))
                .extract().response();
    }
}