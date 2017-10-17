package com.epam.cm.tests;

import com.epam.cm.base.EndpointUrl;
import com.epam.cm.base.SimpleBaseTest;
import com.epam.cm.base.TextConstants;
import com.epam.cm.jira.Jira;
import com.epam.cm.utils.JsonLoader;
import io.restassured.http.ContentType;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.nullValue;

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

    private String validContent  = JsonLoader.asString("AddNewConferenceValidData.json");
    private String invalidContentExistingUser  = JsonLoader.asString("RegistrationNewUserInvalidDataExistingUser.json");

    @Test
    @Jira("6674")
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
                .
        when()
                .post(EndpointUrl.USER)
                .
        then()
                .log().all()
                .statusCode(202)
                .assertThat()
                .body(TextConstants.ERROR, nullValue(), TextConstants.RESULT, hasToString(TextConstants.SUCCESS));
    }

    @Test
    @Jira("6675")
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
                .
        when()
                .post( EndpointUrl.USER)
                .
        then()
                .log().all()
                .statusCode(400)
                .assertThat()
                .body(TextConstants.ERROR, hasToString(TextConstants.EMPTY_FIELDS));
    }

    @Test
    @Jira("6677")
    public void negativeRegistrationExistingUserTest(){

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.speakerUser, config.speakerPassword)
                .cookie(TOKEN, response.cookie(TOKEN))
                .header(X_TOKEN, response.cookie(TOKEN))
                .body(invalidContentExistingUser)
        .when()
                .post( EndpointUrl.USER)
                .
        then()
                .log().all()
                .statusCode(409)
                .assertThat()
                .body(TextConstants.ERROR, hasToString(TextConstants.EXISTING_EMAIL));
    }
}






















