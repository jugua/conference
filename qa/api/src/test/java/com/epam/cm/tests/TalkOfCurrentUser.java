package com.epam.cm.tests;

import com.epam.cm.base.EndpointUrl;
import com.epam.cm.base.SimpleBaseTest;
import com.epam.cm.base.TextConstants;
import com.epam.cm.jira.Jira;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.isOneOf;

public class TalkOfCurrentUser extends SimpleBaseTest {


    @Test
    @Jira("6867")
    public void talkOfCurrentUserIfOrganiser(){

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.organiserUser, config.organiserPassword)
                .cookie(TOKEN, response.cookie(TOKEN))
                .header(X_TOKEN, response.cookie(TOKEN))
                .
        when()
                .get(EndpointUrl.TALK + "5")
                .
        then().log().all()
                .statusCode(200)
                .assertThat()
                .body(TextConstants.TITLE, Matchers.notNullValue(),
                        TextConstants.DESCRIPTION, Matchers.notNullValue(),
                        TextConstants.LANG, isOneOf(TextConstants.RUSSIAN, TextConstants.ENGLISH, TextConstants.UKRAINIAN),
                        TextConstants.LEVEL, isOneOf(TextConstants.BEGINNER, TextConstants.EXPERT, TextConstants.ADVANCED, TextConstants.INTERMEDIATE));
    }

    @Test
    @Jira("6756")
    public void talkOfCurrentUserIfSpeaker() {

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.speakerUser, config.speakerPassword)
                .cookie(TOKEN, response.cookie(TOKEN))
                .header(X_TOKEN, response.cookie(TOKEN))
                .
                        when()
                .get(EndpointUrl.TALK + "5")
                .
                        then().log().all()
                .statusCode(401)
                .assertThat()
                .body(TextConstants.ERROR, hasToString(TextConstants.UNAUTHORIZED));
    }
}