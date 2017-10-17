package com.epam.cm.tests;

import com.epam.cm.base.EndpointUrl;
import com.epam.cm.base.SimpleBaseTest;
import com.epam.cm.base.TextConstants;
import com.epam.cm.jira.Jira;
import com.epam.cm.utils.JsonLoader;
import io.restassured.http.ContentType;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasToString;

/**
 * Created by Mariia_Koltsova on 10/6/2017.
 */
public class ForgotPasswordTests extends SimpleBaseTest {

    private String validContent  = JsonLoader.asString("ForgotPasswordValidData.json");
    private String invalidContent  = JsonLoader.asString("ForgotPasswordInvalidData.json");

    @Test
    @Jira("6665")
    public void positiveForgotPasswordTest() {

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.speakerUser, config.speakerPassword)
                .cookie(TOKEN, response.cookie(TOKEN))
                .header(X_TOKEN, response.cookie(TOKEN))
                .body(validContent)
                .
        when()
                .post(EndpointUrl.FORGOT_PASSWORD)
                .
        then().log().all()
                .statusCode(200);
    }

    @Test
    @Jira("6697")
    public void negativeForgotPasswordTest() {

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.speakerUser, config.speakerPassword)
                .cookie(TOKEN, response.cookie(TOKEN))
                .header(X_TOKEN, response.cookie(TOKEN))
                .body(invalidContent)
                .
        when()
                .post(EndpointUrl.FORGOT_PASSWORD)
                .
        then().log().all()
                .statusCode(400)
                .assertThat()
                .body(TextConstants.ERROR, hasToString(TextConstants.EMAIL_NOT_FOUND));
    }
}
