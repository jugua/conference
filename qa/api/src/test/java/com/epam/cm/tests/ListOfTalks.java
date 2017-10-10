package com.epam.cm.tests;

import com.epam.cm.base.SimpleBaseTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Test;
import java.util.List;

import static io.restassured.RestAssured.given;

public class ListOfTalks extends SimpleBaseTest {
    public static Response endresp;
    @Test
    public void listOfTalksIfOrganiser(){
        endresp =
        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.organiserUser, config.organiserPassword)
                .cookie("XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .header("X-XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .
        when()
                .get( "/api/talk")
                .
        then().log().all()
                .statusCode(200).assertThat()
                .body("title", Matchers.notNullValue())
                .body("description", Matchers.notNullValue())
                .body("date", Matchers.notNullValue())
                .extract().response();

        List<String> language = endresp.path("lang");
        for (String lang:language){
            assert (lang.contains("English")||lang.contains("Russian")||lang.contains("Ukrainian"));
        }

        List<String> level = endresp.path("level");
        for (String lvl:level){
            assert (lvl.contains("Beginner")||lvl.contains("Expert")||lvl.contains("Advanced")||lvl.contains("Intermediate"));
        }

        List<String> topic = endresp.path("topic");
        for (String top:topic){
            assert (top.contains("Architecture & Cloud")||top.contains("BigData & NoSQL")||top.contains("JVM Languages and new programming paradigms")||top.contains("ML")||top.contains("Software engineering practices")||top.contains("Web development and Java Enterprise technologies")||top.contains("New talk topic test"));
        }

    }

    @Test
    public void listOfTalksIfSpeaker(){
        endresp =
        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.speakerUser, config.speakerPassword)
                .cookie("XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .header("X-XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .
                        when()
                .get( "/api/talk")
                .
                        then().log().all()
                .statusCode(200)
                .body("title", Matchers.notNullValue())
                .body("description", Matchers.notNullValue())
                .body("date", Matchers.notNullValue())
                .extract().response();

        List<String> language = endresp.path("lang");
        for (String lang:language){
            assert (lang.contains("English")||lang.contains("Russian")||lang.contains("Ukrainian"));
        }

        List<String> level = endresp.path("level");
        for (String lvl:level){
            assert (lvl.contains("Beginner")||lvl.contains("Expert")||lvl.contains("Advanced")||lvl.contains("Intermediate"));
        }

        List<String> topic = endresp.path("topic");
        for (String top:topic){
            assert (top.contains("Architecture & Cloud")||top.contains("BigData & NoSQL")||top.contains("JVM Languages and new programming paradigms")||top.contains("ML")||top.contains("Software engineering practices")||top.contains("Web development and Java Enterprise technologies")||top.contains("New talk topic test"));
        }
    }
}
