package com.epam.cm.tests;

import com.epam.cm.base.SimpleBaseTest;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.Test;


import static io.restassured.RestAssured.given;

public class ChangePassword extends SimpleBaseTest {

    @Test
    public void ChangePasswordIfAuthorized(){
        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.speakerUser, config.speakerPassword)
                .cookie("XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .header("X-XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .body("{\"currentPassword\": \"speaker\", \"newPassword\": \"speaker\", \"confirmNewPassword\": \"speaker\"}").
        when()
                .post( "/api/user/current/password")
                .
        then().log().all()
                .statusCode(200).assertThat().body(Matchers.equalTo("")).extract().response();
    }

    @Test
    public void ChangePasswordIfNOTAuthorized(){
        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .cookie("XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .header("X-XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .body("{\"currentPassword\": \"speaker\", \"newPassword\": \"speaker\", \"confirmNewPassword\": \"speaker\"}").
                when()
                .post( "/api/user/current/password")
                .
                        then().log().all()
                .statusCode(401).assertThat().body(Matchers.equalTo("")).extract().response();
    }
}
