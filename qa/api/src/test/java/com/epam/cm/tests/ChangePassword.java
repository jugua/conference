package com.epam.cm.tests;

import com.epam.cm.base.SimpleBaseTest;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.Test;
import static com.epam.cm.base.EndpointUrl.PASSWORD;
import static io.restassured.RestAssured.given;

public class ChangePassword extends SimpleBaseTest {

    public static final String passwordChangeJSON = "{\"currentPassword\": \"speaker\", \"newPassword\": \"speaker\", \"confirmNewPassword\": \"speaker\"}";

    @Test
    public void ChangePasswordIfAuthorized(){
        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.speakerUser, config.speakerPassword)
                .cookie(TOKEN, response.cookie(TOKEN))
                .header(X_TOKEN, response.cookie(TOKEN))
                .body(passwordChangeJSON).
        when()
                .post(PASSWORD)
                .
        then().log().all()
                .statusCode(200).assertThat().body(Matchers.equalTo("")).extract().response();
    }

    @Test
    public void ChangePasswordIfNOTAuthorized(){
        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .cookie(TOKEN, response.cookie(TOKEN))
                .header(X_TOKEN, response.cookie(TOKEN))
                .body(passwordChangeJSON).
                when()
                .post(PASSWORD)
                .
                        then().log().all()
                .statusCode(401).assertThat().body(Matchers.equalTo("")).extract().response();
    }
}
