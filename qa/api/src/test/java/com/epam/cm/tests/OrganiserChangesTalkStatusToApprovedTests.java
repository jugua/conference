package com.epam.cm.tests;

import com.epam.cm.base.SimpleBaseTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ResponseBodyExtractionOptions;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.epam.cm.base.EndpointUrl.LOGOUT;
import static com.epam.cm.base.EndpointUrl.TALK;
import static com.epam.cm.base.TextConst.*;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;

public class OrganiserChangesTalkStatusToApprovedTests extends SimpleBaseTest {
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    Date currentDate = new Date();
    ResponseBodyExtractionOptions response1;
    @Before
    public void CreateNewTalk(){
        response1 =
            given()
                    .contentType(ContentType.URLENC)
                    .baseUri(config.baseHost)
                    .auth().preemptive().basic(config.speakerUser, config.speakerPassword)
                    .cookie(TOKEN, response.cookie(TOKEN))
                    .header(XTOKEN, response.cookie(TOKEN))
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
                    .body(ID, Matchers.is(Integer.class))
                    .body(FIELDS, Matchers.nullValue())
                    .extract().body();
    }
    @Test
    public void PositiveApprovalTest(){
        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.organiserUser, config.organiserPassword)
                .cookie("XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .header("X-XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .body("{\"status\":\"Approved\",\"comment\":null}").
        when()
                .patch(""+TALK+"/"+response1.jsonPath().get(ID)).
        then().log().all()
                .statusCode(200).assertThat()
                .body(ERROR, Matchers.nullValue())
                .body(SECONDSTOEXPIRY, Matchers.nullValue())
                .body(RESULT, Matchers.is(TALKUPDATED))
                .body(ID, Matchers.nullValue())
                .body(FIELDS, Matchers.nullValue())
                .extract().response();
    }
    @Test
    public void UserNotAuthorisedTest(){
        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .cookie("XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .header("X-XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .body("{\"status\":\"Approved\",\"comment\":null}").
        when()
                .patch(""+TALK+"/"+response1.jsonPath().get(ID)).
        then().log().all()
                .statusCode(401).assertThat()
                .body(ERROR, Matchers.is(UNAUTHORIZED))
                .body(SECONDSTOEXPIRY, Matchers.nullValue())
                .body(RESULT, Matchers.nullValue())
                .body(ID, Matchers.nullValue())
                .body(FIELDS, Matchers.nullValue())
                .extract().response();
    }
    @Test
    public void TalkDoesNotExistTest(){
        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.organiserUser, config.organiserPassword)
                .cookie("XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .header("X-XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .body("{\"status\":\"Approved\",\"comment\":null}").
        when()
                .patch(""+TALK+"/0").
        then().log().all()
                .statusCode(404).assertThat()
                .body(ERROR, Matchers.is(TALKNOTFOUND))
                .body(SECONDSTOEXPIRY, Matchers.nullValue())
                .body(RESULT, Matchers.nullValue())
                .body(ID, Matchers.nullValue())
                .body(FIELDS, Matchers.nullValue())
                .extract().response();
    }
    @Test
    public void EmptyStatusTest() {
        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.organiserUser, config.organiserPassword)
                .cookie("XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .header("X-XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .body("{\"status\":null,\"comment\":null}").
        when()
                .patch(""+TALK+"/"+response1.jsonPath().get(ID)).
        then().log().all()
                .statusCode(400).assertThat()
                .body(ERROR, Matchers.is(STATUSISNULL))
                .body(SECONDSTOEXPIRY, Matchers.nullValue())
                .body(RESULT, Matchers.nullValue())
                .body(ID, Matchers.nullValue())
                .body(FIELDS, Matchers.nullValue())
                .extract().response();
    }
    @Test
    public void WrongTalkStatusTest() {
        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.organiserUser, config.organiserPassword)
                .cookie("XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .header("X-XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .body("{\"status\":\"Calabanga!\",\"comment\":null}").
        when()
                .patch(""+TALK+"/"+response1.jsonPath().get(ID)).
        then().log().all()
                .statusCode(409).assertThat()
                .body(ERROR, Matchers.is(WRONGSTATUS))
                .body(SECONDSTOEXPIRY, Matchers.nullValue())
                .body(RESULT, Matchers.nullValue())
                .body(ID, Matchers.nullValue())
                .body(FIELDS, Matchers.nullValue())
                .extract().response();
    }

    @Test
    public void TooLongCommentTest() {
        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.organiserUser, config.organiserPassword)
                .cookie("XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .header("X-XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .body("{\"status\":\"Approved\",\"comment\":\""+COMMENT1001SYMBOL+"\"}").
        when()
                .patch(""+TALK+"/"+response1.jsonPath().get(ID)).
        then().log().all()
                .statusCode(413).assertThat()
                .body(ERROR, Matchers.is(COMMENTTOOLONG))
                .body(SECONDSTOEXPIRY, Matchers.nullValue())
                .body(RESULT, Matchers.nullValue())
                .body(ID, Matchers.nullValue())
                .body(FIELDS, Matchers.nullValue())
                .extract().response();
    }
}
