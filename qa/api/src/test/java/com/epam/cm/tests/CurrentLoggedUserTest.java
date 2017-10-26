package com.epam.cm.tests;

import com.epam.cm.base.EndpointUrl;
import com.epam.cm.base.SimpleBaseTest;
import com.epam.cm.base.TextConstants;
import com.epam.cm.jira.Jira;
import com.epam.cm.utils.JsonLoader;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.Ignore;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Created by Oleh_Buryi on 10/5/2017.
 */
public class CurrentLoggedUserTest extends SimpleBaseTest {

    private String validContent  = JsonLoader.asString("CurrentLoggedUserValidData.json");
    private String inValidContent  = JsonLoader.asString("CurrentLoggedUserInvalidData.json");

    @Test
    @Jira("6826")
    public void positiveCurrentLoggedUserTest(){

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.speakerUser, config.speakerPassword)
                .cookie(TOKEN, response.cookie(TOKEN))
                .header(X_TOKEN, response.cookie(TOKEN))
                .
        when()
                .get( EndpointUrl.USER_CURRENT)
                .
        then()
                .log().all()
                .statusCode(202)
                .assertThat()
                .body(TextConstants.ID, equalTo(1));
    }

    @Test
    @Jira("6830")
    @Ignore
    // Actual - 500 -Internal Server error on 8025
    public void positiveUpdateUserInfoTest(){

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.speakerUser, config.speakerPassword)
                .cookie(TOKEN, response.cookie(TOKEN))
                .header(X_TOKEN, response.cookie(TOKEN))
                .body(validContent)
                .
        when()
                .post( EndpointUrl.USER_CURRENT)
                .
        then().log().all()
                .statusCode(200)
                .assertThat()
                .body(Matchers.isEmptyOrNullString());
    }

    @Test
    @Jira("6832")
    public void negativeJsonTypeUpdateTest(){

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.speakerUser, config.speakerPassword)
                .cookie(TOKEN, response.cookie(TOKEN))
                .header(X_TOKEN, response.cookie(TOKEN))
                .body(inValidContent)
        .when()
                .post(EndpointUrl.USER_CURRENT)
                .
        then().log().all()
                .statusCode(400)
                .assertThat()
                .body(Matchers.isEmptyOrNullString());
    }
}