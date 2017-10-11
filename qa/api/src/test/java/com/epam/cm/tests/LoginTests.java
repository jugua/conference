package com.epam.cm.tests;

import com.epam.cm.base.EndpointUrl;
import com.epam.cm.base.SimpleBaseTest;
import com.epam.cm.base.TextConst;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasToString;


public class LoginTests extends SimpleBaseTest {


    @Test //6621
    public void positiveLoginTest() {

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.speakerUser, config.speakerPassword)
                .cookie(TOKEN, response.cookie(TOKEN))
                .header(XTOKEN, response.cookie(TOKEN))
                .
                        when()
                .post(EndpointUrl.LOGIN)
                .
                        then().log().all()
                .statusCode(200)
                .assertThat().body(Matchers.isEmptyOrNullString())
                .extract().response();


    }

    @Test //6893
    public void negativeLoginTestInvalidLogin() {

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)

                .auth().preemptive().basic("gdgdyt873@eu.co", config.organiserPassword)
                .cookie(TOKEN, response.cookie(TOKEN))
                .header(XTOKEN, response.cookie(TOKEN))
                .
                        when()
                .post(EndpointUrl.LOGIN)

                .
                        then().log().all()
                .statusCode(401)
                .assertThat().body(TextConst.ERROR, hasToString(TextConst.LOGINERROR))
                .extract().response();

    }


    @Test //6619
    public void negativeLoginTestInvalidPass() {

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.organiserUser, "732723tf")
                .cookie(TOKEN, response.cookie(TOKEN))
                .header(XTOKEN, response.cookie(TOKEN))

                .
                        when()
                .post(EndpointUrl.LOGIN)
                .
                        then().log().all().assertThat()
                .statusCode(401)
                .assertThat().body(TextConst.ERROR, hasToString(TextConst.PASSWORDERROR))
                .extract().response();

    }

    @Test //6894
    public void negativeLoginTestEmptyFields() {

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic("", "")
                .cookie(TOKEN, response.cookie(TOKEN))
                .header(XTOKEN, response.cookie(TOKEN))

                .
                        when()
                .post(EndpointUrl.LOGIN)
                .
                        then().log().all()
                .statusCode(401)
                .assertThat().body(TextConst.ERROR, hasToString(TextConst.LOGINERROR))
                .extract().response();

    }
}
