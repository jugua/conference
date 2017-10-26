package com.epam.cm.tests;

import com.epam.cm.base.EndpointUrl;
import com.epam.cm.base.SimpleBaseTest;
import com.epam.cm.base.TextConstants;
import com.epam.cm.jira.Jira;
import io.restassured.http.ContentType;
import org.junit.Test;
import org.hamcrest.BaseMatcher;
import org.hamcrest.collection.IsArrayWithSize;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


public class GetLevelsTests extends SimpleBaseTest {

    @Test
    @Jira("6738")
    public void positiveGetLevelsTest() {

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.adminUser, config.adminPassword)
                .cookie(TOKEN, response.cookie(TOKEN))
                .header(X_TOKEN, response.cookie(TOKEN))
                .
        when()
                .get(EndpointUrl.LEVELS)
                .
        then().log().all()
                .statusCode(200)
                .assertThat()
                .body(TextConstants.NAME,
                        hasItems(TextConstants.ADVANCED, TextConstants.BEGINNER, TextConstants.EXPERT,
                                TextConstants.INTERMEDIATE))
                .and()
                .assertThat()
                .body(TextConstants.NAME, hasSize(4))

                .and()
                .assertThat()
                .body(TextConstants.ID, notNullValue());
    }

    @Test
    @Jira("6744")
    public void negativeGetLevelsTest() {

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .cookie(TOKEN, response.cookie(TOKEN))
                .header(X_TOKEN, response.cookie(TOKEN))
                .
        when()
                .get(EndpointUrl.LEVELS)
                .
        then().log().all()
                .statusCode(401)
                .assertThat()
                .body(TextConstants.ERROR, hasToString(TextConstants.UNAUTHORIZED));
    }
}