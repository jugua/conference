package com.epam.cm.tests;

import com.epam.cm.base.EndpointUrl;
import com.epam.cm.base.SimpleBaseTest;
import com.epam.cm.base.TextConst;
import io.restassured.http.ContentType;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasToString;

/**
 * Created by Mariia_Koltsova on 10/6/2017.
 */
public class ForgotPasswordTests extends SimpleBaseTest {

    @Test //6665
    public void positiveForgotPasswordTest() {

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.speakerUser, config.speakerPassword)
                .cookie(TOKEN, response.cookie(TOKEN))
                .header(XTOKEN, response.cookie(TOKEN))
                .body("{\"mail\":\"test@test.com\"}")
                .
                        when()
                .post(EndpointUrl.FORGOTPASS)
                .
                        then().log().all()
                .statusCode(200).extract().response();

    }

    @Test //6697
    public void negativeForgotPasswordTest() {

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.speakerUser, config.speakerPassword)
                .cookie(TOKEN, response.cookie(TOKEN))
                .header(XTOKEN, response.cookie(TOKEN))
                .body("{\"mail\":\"test1@t.com\"}")
                .
                        when()
                .post(EndpointUrl.FORGOTPASS)
                .
                        then().log().all()
                .statusCode(400)
                .assertThat().body(TextConst.ERROR, hasToString(TextConst.EMAILNOTFOUND))
                .extract().response();

    }


}
