package com.epam.cm.tests;

import com.epam.cm.base.EndpointUrl;
import com.epam.cm.base.SimpleBaseTest;
import com.epam.cm.jira.Jira;
import com.epam.cm.utils.JsonLoader;
import io.restassured.http.ContentType;
import org.junit.Test;

import static com.epam.cm.base.TextConstants.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

public class AddNewTalkTypeTest extends SimpleBaseTest {

    private String validContent = JsonLoader.asString("AddNewTalkTypeData.json");

    @Test
    @Jira("6657")
    public void positiveAddNewTalkTypeTest(){

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.adminUser, config.adminPassword)
                .cookie(TOKEN, response.cookie(TOKEN))
                .header(X_TOKEN, response.cookie(TOKEN))
                .body(validContent)
                .

        when()
                .post(EndpointUrl.TYPE)
                .

        then().log().all()
                .statusCode(200)
                .assertThat().body(ERROR, nullValue(), ID, notNullValue())
                .extract().response();
    }

    @Test
    @Jira("6660")
    public void negativeAddNewTalkTypeTest(){

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.speakerUser, config.speakerPassword)
                .cookie(TOKEN, response.cookie(TOKEN))
                .header(X_TOKEN, response.cookie(TOKEN))
                .body(validContent)
                .

        when()
                .post( EndpointUrl.TYPE)
                .

        then().log().all()
                .statusCode(401)
                .assertThat().body(ERROR, hasToString(UNAUTHORIZED))
                .extract().response();
    }
}