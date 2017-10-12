package com.epam.cm.tests;

import com.epam.cm.base.EndpointUrl;
import com.epam.cm.base.SimpleBaseTest;
import com.epam.cm.jira.Jira;
import io.restassured.http.ContentType;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.epam.cm.base.TextConstants.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

public class AddNewTopicTest extends SimpleBaseTest {

    Path pathToNewTopicData = Paths.get(getClass().getClassLoader()
            .getResource("AddNewTopicData").toURI());
    byte[] fileNewTopicData = Files.readAllBytes(pathToNewTopicData);
    String validContent = new String(fileNewTopicData);

    public AddNewTopicTest() throws IOException, URISyntaxException {
    }

    @Test
    @Jira("6679")
    public void positiveAddNewTalkTypeTest(){

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.adminUser, config.adminPassword)
                .cookie(TOKEN, response.cookie(TOKEN))
                .header(XTOKEN, response.cookie(TOKEN))
                .body(validContent)
                .

        when()
                .post(EndpointUrl.TOPIC)
                .

        then().log().all()
                .statusCode(200)
                .assertThat().body(ERROR, nullValue(), ID, notNullValue())
                .extract().response();
    }

    @Test
    @Jira("6681")
    public void negativeAddNewTalkTypeTest(){

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.speakerUser, config.speakerPassword)
                .cookie(TOKEN, response.cookie(TOKEN))
                .header(XTOKEN, response.cookie(TOKEN))
                .body(validContent)
                .

        when()
                .post( EndpointUrl.TOPIC)
                .

        then().log().all()
                .statusCode(401)
                .assertThat().body(ERROR, hasToString(UNAUTHORIZED))
                .extract().response();
    }
}