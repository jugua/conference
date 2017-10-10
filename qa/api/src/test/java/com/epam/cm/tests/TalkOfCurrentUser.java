package com.epam.cm.tests;

import com.epam.cm.base.SimpleBaseTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class TalkOfCurrentUser extends SimpleBaseTest {
public static Response endresp;
    @Test
    public void talkOfCureentUserIfOrganiser(){
endresp =
        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.organiserUser, config.organiserPassword)
                .cookie("XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .header("X-XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .
        when()
                .get( "/api/talk/33")
                .
        then().log().all()
                .statusCode(200)
                .body("title", Matchers.notNullValue())
                .body("description", Matchers.notNullValue())
                .body("date", Matchers.notNullValue())
                .extract().response();

            assert (endresp.path("lang").equals("English")||endresp.path("lang").equals("Russian")||endresp.path("lang").equals("Ukrainian"));

            assert (endresp.path("level").equals("Beginner")||endresp.path("level").equals("Expert")||endresp.path("level").equals("Advanced")||endresp.path("level").equals("Intermediate"));

            assert (endresp.path("topic").equals("Architecture & Cloud")||endresp.path("topic").equals("BigData & NoSQL")||endresp.path("topic").equals("JVM Languages and new programming paradigms")||endresp.path("topic").equals("ML")||endresp.path("topic").equals("Software engineering practices")||endresp.path("topic").equals("Web development and Java Enterprise technologies")||endresp.path("topic").equals("New talk topic test"));

    }

    @Test
    public void talkOfCurrentUserIfSpeaker(){

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.speakerUser, config.speakerPassword)
                .cookie("XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .header("X-XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .
                        when()
                .get( "/api/talk/60")
                .
                        then().log().all()
                .statusCode(200)
                .body("title", Matchers.notNullValue())
                .body("description", Matchers.notNullValue())
                .body("date", Matchers.notNullValue())
                .extract().response();

        assert (endresp.path("lang").equals("English")||endresp.path("lang").equals("Russian")||endresp.path("lang").equals("Ukrainian"));

        assert (endresp.path("level").equals("Beginner")||endresp.path("level").equals("Expert")||endresp.path("level").equals("Advanced")||endresp.path("level").equals("Intermediate"));

        assert (endresp.path("topic").equals("Architecture & Cloud")||endresp.path("topic").equals("BigData & NoSQL")||endresp.path("topic").equals("JVM Languages and new programming paradigms")||endresp.path("topic").equals("ML")||endresp.path("topic").equals("Software engineering practices")||endresp.path("topic").equals("Web development and Java Enterprise technologies")||endresp.path("topic").equals("New talk topic test"));

    }
}
