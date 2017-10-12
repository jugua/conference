package com.epam.cm.tests;

import com.epam.cm.base.EndpointUrl;
import com.epam.cm.base.SimpleBaseTest;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import io.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.epam.cm.base.TextConstants.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

@RunWith(DataProviderRunner.class)
public class AddNewTalkTypeTest extends SimpleBaseTest {

    @DataProvider
    public static Object[][] newTalkTypeDataProvider() {
        return new Object[][] {
                {"{\"name\": \"Some talk type test\"}"}
        };
    }

    @UseDataProvider("newTalkTypeDataProvider")
    @Test //6657
    public void positiveAddNewTalkTypeTest(String topicName){

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.adminUser, config.adminPassword)
                .cookie(TOKEN, response.cookie(TOKEN))
                .header(XTOKEN, response.cookie(TOKEN))
                .body(topicName)
                .

        when()
                .post(EndpointUrl.TYPE)
                .

        then().log().all()
                .statusCode(200)
                .assertThat().body(ERROR, nullValue(), ID, notNullValue())
                .extract().response();
    }

    @UseDataProvider("newTalkTypeDataProvider")
    @Test //6660
    public void negativeAddNewTalkTypeTest(String topicName){

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.speakerUser, config.speakerPassword)
                .cookie(TOKEN, response.cookie(TOKEN))
                .header(XTOKEN, response.cookie(TOKEN))
                .body(topicName)
                .

        when()
                .post( EndpointUrl.TYPE)
                .

        then().log().all()
                .statusCode(401)
                .assertThat().body(ERROR, hasToString(UNAUTHORIZED))
                .extract().response();
    }
}