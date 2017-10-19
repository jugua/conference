package com.epam.cm.tests;

import com.epam.cm.base.EndpointUrl;
import com.epam.cm.base.SimpleBaseTest;
import com.epam.cm.base.TextConstants;
import com.epam.cm.jira.Jira;
import io.restassured.http.ContentType;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;

public class CreateNewTalk extends SimpleBaseTest {

    DateFormat dateFormat = new SimpleDateFormat(TextConstants.DATE_PATTERN);
    Date currentDate = new Date();

    @Test
    @Jira("6696")
    public void createNewTalk(){

        given()
                .contentType(ContentType.URLENC)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.speakerUser, config.speakerPassword)
                .cookie(TOKEN, response.cookie(TOKEN))
                .header(X_TOKEN, response.cookie(TOKEN))
                .formParam(TextConstants.TITLE,TextConstants.TEST_TITLE + dateFormat.format(currentDate))
                .formParam(TextConstants.DESCRIPTION, TextConstants.TEST_DESCRIPTION)
                .formParam(TextConstants.TOPIC,TextConstants.BIGDATA)
                .formParam(TextConstants.TYPE,TextConstants.LIGHTING)
                .formParam(TextConstants.LANG,TextConstants.ENGLISH)
                .formParam(TextConstants.LEVEL,TextConstants.BEGINNER)
                .formParam(TextConstants.STATUS,TextConstants.NEW)
                .formParam(TextConstants.DATE,TextConstants.TEST_DATE).
        when()
                .post(EndpointUrl.TALK)
                .
        then().log().all()
                .statusCode(200)
                .assertThat()
                .body(TextConstants.ERROR, nullValue(),
                        TextConstants.ID, notNullValue());
    }
}