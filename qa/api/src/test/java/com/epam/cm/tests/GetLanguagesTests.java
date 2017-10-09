package com.epam.cm.tests;

import com.epam.cm.base.SimpleBaseTest;
import io.restassured.http.ContentType;
import org.junit.Test;

import static io.restassured.RestAssured.given;

/**
 * Created by Mariia_Koltsova on 10/5/2017.
 */
public class GetLanguagesTests extends SimpleBaseTest {

    @Test
    public void positiveGetLangTest(){

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.speakerUser, config.speakerPassword)
                .cookie("XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .header("X-XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .
                        when()
                .get( "/api/lang")
                .
                        then().log().all()
                .statusCode(200).extract().response();

    }
    @Test
    public void negativeGetLangTest(){
        //Response response1 =
        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .cookie("XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .header("X-XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .
                        when()
                .get( "/api/lang")
                .
                        then().log().all()
                .statusCode(401).extract().response();

    }


}
