package com.epam.cm.tests;

import com.epam.cm.base.EndpointUrl;
import com.epam.cm.base.SimpleBaseTest;
import com.epam.cm.base.TextConstants;
import com.epam.cm.jira.Jira;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class ListOfTalks extends SimpleBaseTest {

    @Test
    @Jira("6707")
    public void listOfTalksIfOrganiser(){

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.organiserUser, config.organiserPassword)
                .cookie(TOKEN, response.cookie(TOKEN))
                .header(X_TOKEN, response.cookie(TOKEN))
                .
        when()
                .get(EndpointUrl.TALK)
                .
        then().log().all()
                .statusCode(200)
                .assertThat()
                .body(TextConstants.ID, Matchers.notNullValue(),
                        TextConstants.TITLE, Matchers.notNullValue(),
                        TextConstants.DESCRIPTION, Matchers.notNullValue());
    }

    @Test
    @Jira("6706")
    public void listOfTalksIfSpeaker(){

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.speakerUser, config.speakerPassword)
                .cookie(TOKEN, response.cookie(TOKEN))
                .header(X_TOKEN, response.cookie(TOKEN))
                .
        when()
                .get(EndpointUrl.TALK)
                .
        then().log().all()
                .statusCode(200)
                .assertThat()
                .body(TextConstants.ID, Matchers.notNullValue(),
                        TextConstants.TITLE, Matchers.notNullValue(),
                        TextConstants.DESCRIPTION, Matchers.notNullValue());
    }
}