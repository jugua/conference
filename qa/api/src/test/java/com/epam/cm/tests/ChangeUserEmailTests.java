package com.epam.cm.tests;

import com.epam.cm.base.SimpleBaseTest;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.Test;

import static com.epam.cm.base.EndpointUrl.EMAIL;
import static com.epam.cm.base.TextConst.EMAILNOTFOUND;
import static com.epam.cm.base.TextConst.ERROR;
import static io.restassured.RestAssured.given;

public class ChangeUserEmailTests extends SimpleBaseTest {
    @Test
    public void PositiveChangeTest(){

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.organiserUser, config.organiserPassword)
                .cookie("XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .header("X-XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .body("{\"mail\":\"oh_allah@epam.com\"}").
        when()
                .post(EMAIL).
        then()
                .log().all()
                .statusCode(200).assertThat().body(Matchers.equalTo("")).extract().response();
    }
    @Test
    public void EmailExistsChangeTest(){

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.organiserUser, config.organiserPassword)
                .cookie("XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .header("X-XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .body("{\"mail\":\""+config.organiserUser+"\"}").
        when()
                .post(EMAIL).
        then()
                .log().all()
                .statusCode(409).assertThat()
                    .body(ERROR, Matchers.equalTo("email_already_exists"));
    }
    @Test
    public void NotAuthorisedChangeTest(){

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .cookie("XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .header("X-XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .body("{\"mail\":\"oh_allah@epam.com\"}").
        when()
                .post(EMAIL).
        then()
                .log().all()
                .statusCode(401).assertThat().body(Matchers.equalTo("")).extract().response();
    }
    @Test
    public void WrongDataChangeTest() {

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.organiserUser, config.organiserPassword)
                .cookie("XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .header("X-XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .body("{\"mail\":\"ohallahepasdasdamcom\"}").
        when()
                .post(EMAIL).
        then()
                .log().all()
                .statusCode(400).assertThat().body(Matchers.equalTo("")).extract().response();
    }
    @Test
    public void DBErrorChangeTest() {

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.organiserUser, config.organiserPassword)
                .cookie("XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .body("{\"mail\":\"oh_allah@epam.com\"}").
        when()
                .post(EMAIL).
        then()
                .log().all()
                .statusCode(403).extract().response();
    }
}
