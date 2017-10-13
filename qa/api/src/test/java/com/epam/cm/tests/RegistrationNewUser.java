package com.epam.cm.tests;

import com.epam.cm.base.SimpleBaseTest;
import io.restassured.http.ContentType;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static io.restassured.RestAssured.given;

/**
 * Created by Oleh_Buryi on 10/6/2017.
 */
public class RegistrationNewUser extends SimpleBaseTest {
    public static String getCurrentTimeStamp() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMddHHmmss");//dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }
    @Test
    public void positiveRegistrationNewUserTest(){

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.speakerUser, config.speakerPassword)
                .cookie(TOKEN, response.cookie(TOKEN))
                .header(X_TOKEN, response.cookie(TOKEN))
                .body("{\"fname\": \"fnametest1\", \"lname\": \"lnametest1\", " +
                        "\"mail\": \""+"autoUser"+getCurrentTimeStamp()+"@mailtest1.com\"," +
                        " \"password\": \"1testtest1\",\"confirm\": \"1testtest1\" }")
                .when()
                .post( "/api/user")
                .
                        then()
                .log().all()
                .statusCode(202).extract().response();

    }
    @Test
    public void negativeRegistrationNewUserTest(){

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.speakerUser, config.speakerPassword)
                .cookie(TOKEN, response.cookie(TOKEN))
                .header(X_TOKEN, response.cookie(TOKEN))
                .body("{\"lname\": \"lnametest1\", " +
                        "\"mail\": \""+"autoUser"+getCurrentTimeStamp()+"@mailtest1.com\"," +
                        " \"password\": \"1testtest1\",\"confirm\": \"1testtest1\" }")
                .when()
                .post( "/api/user")
                .
                        then()
                .log().all()
                .statusCode(400).extract().response();
    }
    @Test
    public void negativeRegistrationExistingUserTest(){

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.speakerUser, config.speakerPassword)
                .cookie(TOKEN, response.cookie(TOKEN))
                .header(X_TOKEN, response.cookie(TOKEN))
                .body("{\"fname\": \"fnametest1\", \"lname\": \"lnametest1\", " +
                        "\"mail\": \"speaker@speaker.com\"," +
                        " \"password\": \"1testtest1\",\"confirm\": \"1testtest1\" }")
                .when()
                .post( "/api/user")
                .
                        then()
                .log().all()
                .statusCode(409).extract().response();

    }

}






















