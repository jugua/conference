package com.epam.cm.tests;

import com.epam.cm.base.EndpointUrl;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import io.restassured.http.ContentType;
import com.epam.cm.base.SimpleBaseTest;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.epam.cm.base.TextConstants.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

@RunWith(DataProviderRunner.class)
public class AddNewConferenceTest extends SimpleBaseTest{

    @DataProvider
    public static Object[][] newConferenceValidDataProvider() {
        return new Object[][] {
                {"{ \"title\": \"TestConference\", \"description\": \"Test\", \"location\": \"Kyiv\", " +
                        "\"start_date\": \"2017-09-09\", \"end_date\": \"2017-11-29\", \"no_dates\": \"true\", " +
                        "\"cfp_start_date\": \"2017-09-09\", \"cfp_end_date\": \"2017-11-29\", \"cfp_no_dates\": \"true\", " +
                        "\"topics\": [1,2,3,4], \"types\": [4,2], \"languages\": [1,3], \"levels\": [4,3], \"organisers\": [3, 4, 5] }"
                }
        };
    }

    @DataProvider
    public static Object[][] newConferenceInvalidDataProvider() {
        return new Object[][] {
                {" { \"title\": \"\", \"description\": \"\", \"location\": \"\", \"start_date\": \"\", \"end_date\": \"2017-11-29\", " +
                        "\"no_dates\": \"true\", \"cfp_start_date\": \"2017-09-09\", \"cfp_end_date\": \"2017-11-29\", \"cfp_no_dates\": \"true\", " +
                        "\"topics\": [1,2,3,4], \"types\": [4,2], \"languages\": [1,3], \"levels\": [4,3], \"organisers\": [3, 4, 5] }"
                }
        };
    }

    @UseDataProvider("newConferenceValidDataProvider")
    @Test //6622
    public void positiveAddNewConferenceTest(String newConferenceData){

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.adminUser, config.adminPassword)
                .cookie(TOKEN, response.cookie(TOKEN))
                .header(XTOKEN, response.cookie(TOKEN))
                .body(newConferenceData)
                .
        when()
                .post( EndpointUrl.CONFERENCE)
                .

        then().log().all()
                .statusCode(200)
                .assertThat().body(ERROR, nullValue(), ID, notNullValue())
                .extract().response();
    }

    @UseDataProvider("newConferenceValidDataProvider")
    @Test //6659
    public void negativeAddNewConferenceTest(String newConferenceData){

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.speakerUser, config.speakerPassword)
                .cookie(TOKEN, response.cookie(TOKEN))
                .header(XTOKEN, response.cookie(TOKEN))
                .body(newConferenceData)
                .
        when()
                .post( EndpointUrl.CONFERENCE)
                .

        then().log().all()
                .statusCode(401)
                .assertThat().body(ERROR, hasToString(UNAUTHORIZED))
                .extract().response();
    }

    @Ignore //test failed. bug 6825
    @UseDataProvider("newConferenceInvalidDataProvider")
    @Test //6822
    public void negativeValidationFailedAddNewConferenceTest(String newConferenceData){

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.adminUser, config.adminPassword)
                .cookie(TOKEN, response.cookie(TOKEN))
                .header(XTOKEN, response.cookie(TOKEN))
                .body(newConferenceData)
                .

        when()
                .post( EndpointUrl.CONFERENCE)
                .

        then().log().all()
                .statusCode(400)
                .extract().response();
    }
}