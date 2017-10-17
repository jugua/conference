package com.epam.cm.tests;

import com.epam.cm.base.SimpleBaseTest;
import com.epam.cm.jira.Jira;
import io.restassured.http.ContentType;
import org.junit.Test;

import static com.epam.cm.tests.RegistrationNewUser.*;
import static io.restassured.RestAssured.given;

public class RegistrationNewUserByAdmin extends SimpleBaseTest {


    @Test
    @Jira("6689")
    public void positiveRegistrationUserByAdminTest() {

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.adminUser, config.adminPassword)
                .cookie(TOKEN, response.cookie(TOKEN))
                .header(X_TOKEN, response.cookie(TOKEN))
                .body("{\"fname\": \"fnametest1\", \"lname\": \"lnametest1\", " +
                        "\"mail\": \"" + "autoUser" + getCurrentTimeStamp() + "@mailtest1.com\"," +
                        " \"password\": \"1testtest1\",\"confirm\": \"1testtest1\", \"roleName\" : \"ROLE_SPEAKER\" }")
                .when()
                .post("api/user/create")
                .
                        then()
                .log().all()
                .statusCode(202).extract().response();

    }

    @Test
    @Jira("6694")
    public void absentParamRegistrationUserByAdminTest() {

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.adminUser, config.adminPassword)
                .cookie(TOKEN, response.cookie(TOKEN))
                .header(X_TOKEN, response.cookie(TOKEN))
                .body("{\"fname\": \"fnametest1\", \"lname\": \"lnametest1\", " +
                        " \"password\": \"1testtest1\",\"confirm\": \"1testtest1\", \"roleName\" : \"ROLE_SPEAKER\" }")
                .when()
                .post("api/user/create")
                .
                        then()
                .log().all()
                .statusCode(400).extract().response();

    }

    @Test
    @Jira("7040")
    public void unauthorizedRegistrationUserByAdminTest() {

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .cookie(TOKEN, response.cookie(TOKEN))
                .header(X_TOKEN, response.cookie(TOKEN))
                .body("{\"fname\": \"fnametest1\", \"lname\": \"lnametest1\", " +
                "\"mail\": \"" + "autoUser" + getCurrentTimeStamp() + "@mailtest1.com\"," +
                " \"password\": \"1testtest1\",\"confirm\": \"1testtest1\", \"roleName\" : \"ROLE_SPEAKER\" }")

                .when()
                .post("api/user/create")
                .
                        then()
                .log().all()
                .statusCode(401).extract().response();

    }
    @Test
    @Jira("6691")
    public void negativeRegistrationExistingUserByAdminTest() {

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.adminUser, config.adminPassword)
                .cookie(TOKEN, response.cookie(TOKEN))
                .header(X_TOKEN, response.cookie(TOKEN))
                .body("{\"fname\": \"fnametest1\", \"lname\": \"lnametest1\", " +
                        "\"mail\": \"speaker@speaker.com\"," +
                        " \"password\": \"1testtest1\",\"confirm\": \"1testtest1\", \"roleName\" : \"ROLE_SPEAKER\" }")
                .when()
                .post("api/user/create")
                .
                        then()
                .log().all()
                .statusCode(409).extract().response();

    }
}

