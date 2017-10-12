package com.epam.cm.tests;

import com.epam.cm.base.EndpointUrl;
import com.epam.cm.base.SimpleBaseTest;
import com.epam.cm.base.TextConstants;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasToString;

@RunWith(DataProviderRunner.class)
public class LoginTests extends SimpleBaseTest {

    @DataProvider
    public static Object[][] invalidLoginDataProvider() {
        return new Object[][] {
                { "gdgdyt873@eu.co", "organiser" },
                { "", "" }
        };
    }

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

    @Test //6893, 6894
    @UseDataProvider("invalidLoginDataProvider")
    public void negativeLoginTestInvalidLoginError(String login, String password) {

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(login, password)
                .cookie(TOKEN, response.cookie(TOKEN))
                .header(XTOKEN, response.cookie(TOKEN))
                .
                        when()
                .post(EndpointUrl.LOGIN)

                .
                        then().log().all()
                .statusCode(401)
                .assertThat().body(TextConstants.ERROR, hasToString(TextConstants.LOGIN_ERROR))

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
                .assertThat().body(TextConstants.ERROR, hasToString(TextConstants.PASSWORD_ERROR))
                .extract().response();

    }


}