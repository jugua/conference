package com.epam.cm.tests;

import com.epam.cm.base.EndpointUrl;
import com.epam.cm.base.SimpleBaseTest;
import com.epam.cm.jira.Jira;
import com.epam.cm.utils.JsonLoader;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.Test;
import static io.restassured.RestAssured.given;

public class ChangePassword extends SimpleBaseTest {

    private String validContent  = JsonLoader.asString("ChangePasswordData.json");

    @Test
    @Jira("6829")
    public void ChangePasswordIfAuthorized(){
        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.speakerUser, config.speakerPassword)
                .cookie(TOKEN, response.cookie(TOKEN))
                .header(X_TOKEN, response.cookie(TOKEN))
                .body(validContent).
        when()
                .post(EndpointUrl.PASSWORD)
                .
        then().log().all()
                .statusCode(200)
                .assertThat()
                .body(Matchers.isEmptyString());
    }

    @Test
    @Jira("6865")
    public void ChangePasswordIfNOTAuthorized(){
        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .cookie(TOKEN, response.cookie(TOKEN))
                .header(X_TOKEN, response.cookie(TOKEN))
                .body(validContent).
        when()
                .post(EndpointUrl.PASSWORD)
                .
        then().log().all()
                .statusCode(401)
                .assertThat()
                .body(Matchers.isEmptyString());
    }
}