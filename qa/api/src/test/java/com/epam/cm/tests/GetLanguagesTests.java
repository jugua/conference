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
public class GetLanguagesTests extends SimpleBaseTest {

    @Test //6753
    public void positiveGetLangTest() {

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.speakerUser, config.speakerPassword)
                .cookie(TOKEN, response.cookie(TOKEN))
                .header(XTOKEN, response.cookie(TOKEN))
                .
                        when()
                .get(EndpointUrl.LANGUAGES)
                .
                        then().log().all()
                .statusCode(200)
                .assertThat()
                .body(TextConst.NAME,
                        hasItems(TextConst.ENGLISH, TextConst.UKRAINIAN, TextConst.RUSSIAN))
                .and().assertThat().body(TextConst.ID, notNullValue())
                .extract().response();

    }

    @Test //6755
    public void negativeGetLangTest() {

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .cookie(TOKEN, response.cookie(TOKEN))
                .header(XTOKEN, response.cookie(TOKEN))
                .
                        when()
                .get(EndpointUrl.LANGUAGES)
                .
                        then().log().all()
                .statusCode(401)
                .assertThat().body(TextConst.ERROR, hasToString(TextConst.UNAUTHORIZED))
                .extract().response();

    }


}
