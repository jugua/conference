package com.epam.cm.tests;

import com.epam.cm.base.EndpointUrl;
import com.epam.cm.base.SimpleBaseTest;
import com.epam.cm.base.TextConst;
import io.restassured.http.ContentType;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * Created by Mariia_Koltsova on 10/5/2017.
 */
public class GetLevelsTests extends SimpleBaseTest {

    @Test //6738
    public void positiveGetLevelsTest() {

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.adminUser, config.adminPassword)
                .cookie(TOKEN, response.cookie(TOKEN))
                .header(XTOKEN, response.cookie(TOKEN))
                .
                        when()
                .get(EndpointUrl.LEVELS)
                .
                        then().log().all()
                .statusCode(200)
                .assertThat()
                .body(TextConst.NAME,
                        hasItems(TextConst.ADVANCED, TextConst.BEGINNER, TextConst.EXPERT,
                                TextConst.INTERMEDIATE))
                .and().assertThat().body(TextConst.ID, notNullValue())
                .extract().response();

    }

    @Test //6744
    public void negativeGetLevelsTest() {

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.speakerUser, "76w883")
                .cookie(TOKEN, response.cookie(TOKEN))
                .header(XTOKEN, response.cookie(TOKEN))
                .
                        when()
                .get(EndpointUrl.LEVELS)
                .
                        then().log().all()
                .statusCode(401)
                .assertThat().body(TextConst.ERROR, hasToString(TextConst.PASSWORDERROR))
                .extract().response();

    }

}
