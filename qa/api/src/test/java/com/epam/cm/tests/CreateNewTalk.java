package com.epam.cm.tests;

import com.epam.cm.base.SimpleBaseTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.epam.cm.base.EndpointUrl.TALK;
import static com.epam.cm.base.TextConst.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;

public class CreateNewTalk extends SimpleBaseTest {

    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    Date currentDate = new Date();

    @Test
    public void createNewTalk(){

        given()
                .contentType(ContentType.URLENC)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.speakerUser, config.speakerPassword)
                .cookie(TOKEN, response.cookie(TOKEN))
                .header(X_TOKEN, response.cookie(TOKEN))
                .formParam(TITLE,"Test talk " + dateFormat.format(currentDate))
                .formParam(DESCRIPTION,"this is test talk for automate test")
                .formParam(TOPIC,"ML")
                .formParam(TYPE,"Lighting Talk")
                .formParam(LANG,"English")
                .formParam(LEVEL,"Beginner")
                .formParam(STATUS,"New")
                .formParam(DATE,"1506419805177").
        when()
                .post(TALK)
                .
        then().log().all()
                .statusCode(200).assertThat()
                .body(ERROR, Matchers.nullValue())
                .body(SECONDSTOEXPIRY, Matchers.nullValue())
                .body(RESULT, Matchers.nullValue())
                .body(ID, is(Integer.class))
                .body(FIELDS, Matchers.nullValue())
                .extract().response();
    }
}
