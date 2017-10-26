package com.epam.cm.tests;

import com.epam.cm.base.EndpointUrl;
import com.epam.cm.base.SimpleBaseTest;
import com.epam.cm.jira.Jira;
import io.restassured.filter.cookie.CookieFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Ignore;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class LogoutTests extends SimpleBaseTest {


    @Test
    @Ignore
    @Jira("6620")
    public void positiveLogoutTest() {

        CookieFilter cookieFilter = new CookieFilter();

        Response resp =
        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.speakerUser, config.speakerPassword)
                .cookie(TOKEN, response.cookie(TOKEN))
                .header(X_TOKEN, response.cookie(TOKEN))
                .filter(cookieFilter)

                .
        when()
                .post(EndpointUrl.LOGIN)
        .then().log().all()
                .statusCode(200)
                .extract().response();


        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .filter(cookieFilter)
                .cookie(TOKEN, resp.cookie(TOKEN))
                .header(X_TOKEN, resp.cookie(TOKEN))

        .when()
                .post(EndpointUrl.LOGOUT)

                .then().log().all()
                .assertThat().cookie(TOKEN, Matchers.containsString(""))
                .extract().response();

    }


}
