package com.epam.cm.tests;

import com.epam.cm.base.SimpleBaseTest;
import com.epam.cm.jira.Jira;
import io.restassured.http.ContentType;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Created by Oleh_Buryi on 10/5/2017.
 */
public class CurrentLoggedUserTest extends SimpleBaseTest {

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
                .get( "/api/user/current")
                .
                        then()
                .log().all()
                .statusCode(202)
                 .assertThat() .body("id", equalTo(1));

    }
    @Test
    @Jira("6828")
    public void negativeCurrentLoggedUserNoTokenTest(){

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)

                        .when()
                .get( "/api/user/current")
                .
                        then()
                .log().all()
                .statusCode(401).extract().response();

    }
    @Test
    @Jira("6830")
    public void positiveUpdateUserInfoTest(){

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.speakerUser, config.speakerPassword)
                .cookie(TOKEN, response.cookie(TOKEN))
                .header(X_TOKEN, response.cookie(TOKEN))
                .body("{ \"id\": 1, \"roles\": [\"ROLE_SPEAKER\"],\"mail\": \"speaker@speaker.com\", \"fname\": \"Master\", \"lname\": \"Trybel\", \"bio\": \"Short bio000\", \"job\": \"JuniorTest\",\"company\": \"EPAM\", \"past\": \"Past conference\",\"photo\": \"api/user/current/photo/1\", \"linkedin\": \"linkedin.com\", \"twitter\": \"twitter.com\", \"facebook.com\": \"facebook.com\", \"blog\": \"userblog.com\", \"info\": \"Additional info\" }")
                .when()
                .post( "/api/user/current")
                .
                        then()
                .log().all()
                .statusCode(200).extract().response();

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
                .body("{ \"id\": 1, \"roles\": [\"ROLE_SPEAKER\"],\"mail\": \"speaker@speaker.com\", \"fname\": \"Master\", \"lname\": \"Trybel\", \"bio\": \"Short bio000\",\"company\": \"EPAM\", \"past\": \"Past conference\",\"photo\": \"api/user/current/photo/1\", \"linkedin\": \"linkedin.com\", \"twitter\": \"twitter.com\", \"facebook.com\": \"facebook.com\", \"blog\": \"userblog.com\", \"info\": \"Additional info\" }")
                .when()
                .post( "/api/user/current")
                .
                        then()
                .log().all()
                .statusCode(400).extract().response();

    }
}
