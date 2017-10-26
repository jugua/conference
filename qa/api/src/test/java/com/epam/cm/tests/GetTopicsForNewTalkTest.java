package com.epam.cm.tests;

import com.epam.cm.base.EndpointUrl;
import com.epam.cm.base.SimpleBaseTest;
import com.epam.cm.jira.Jira;
import io.restassured.http.ContentType;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasToString;
import static com.epam.cm.base.TextConstants.*;

public class GetTopicsForNewTalkTest extends SimpleBaseTest{

    @Test
    @Jira("6690")
    public void positiveGetTypesTest(){

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.speakerUser, config.speakerPassword)
                .cookie(TOKEN, response.cookie(TOKEN))
                .header(X_TOKEN, response.cookie(TOKEN))
                .
        when()
                .get(EndpointUrl.TOPICS)
                .

        then().log().all()
                .statusCode(200)
                .assertThat()
                .body(NAME, hasItems(JVM, SOFTWARE, ARCHITECTURE));
    }

    @Test
    @Jira("6693")
    public void negativeGetTypesTest(){

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .cookie(TOKEN, response.cookie(TOKEN))
                .header(X_TOKEN, response.cookie(TOKEN))
                .
        when()
                .get(EndpointUrl.TOPICS)
                .

        then().log().all()
                .statusCode(401)
                .assertThat()
                .body(ERROR, hasToString(UNAUTHORIZED));
    }
}