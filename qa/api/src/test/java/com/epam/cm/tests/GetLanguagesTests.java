package com.epam.cm.tests;

import com.epam.cm.base.EndpointUrl;
import com.epam.cm.base.SimpleBaseTest;
import com.epam.cm.base.TextConstants;
import com.epam.cm.jira.Jira;
import io.restassured.http.ContentType;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * Created by Mariia_Koltsova on 10/5/2017.
 */
public class GetLanguagesTests extends SimpleBaseTest {

    @Test
    @Jira("6753")
    public void positiveGetLangTest(){

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.speakerUser, config.speakerPassword)
                .cookie(TOKEN, response.cookie(TOKEN))
                .header(X_TOKEN, response.cookie(TOKEN))
                .
                        when()
                .get(EndpointUrl.LANGUAGES)
                .
                        then().log().all()
                .statusCode(200)
                .assertThat()
                .body(TextConstants.NAME,
                        hasItems(TextConstants.ENGLISH, TextConstants.UKRAINIAN, TextConstants.RUSSIAN))
                .and()
                .assertThat()
                .body(TextConstants.NAME, hasSize(3))

                .and()
                .assertThat()
                .body(TextConstants.ID, notNullValue());

    }
    @Test
    @Jira("6755")
    public void negativeGetLangTest(){

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .cookie(TOKEN, response.cookie(TOKEN))
                .header(X_TOKEN, response.cookie(TOKEN))
                .
                        when()
                .get( EndpointUrl.LANGUAGES)
                .
                        then().log().all()
                .statusCode(401)
                .assertThat()
                .body(TextConstants.ERROR, hasToString(TextConstants.UNAUTHORIZED));

    }


}
