package com.epam.cm.tests;

import com.epam.cm.base.SimpleBaseTest;
import io.restassured.http.ContentType;
import org.junit.Test;

import static io.restassured.RestAssured.given;

// Tests are failed
// Bug ID: EPMFARMKPP-6895
// Link to Bug: https://jirapct.epam.com/jira/browse/EPMFARMKPP-6895

public class UpdateConferenceTests extends SimpleBaseTest {

    @Test
    public void getUpcomingConferencesAdminTest(){

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().basic(config.adminUser, config.adminPassword)
                .cookie("XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .header("X-XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .body("{\"title\": \"UpdatedConference\", \"description\": \"Update Conference Test\"," +
                        " \"location\": \"London\", \"start_date\": \"2017-09-09\",\"end_date\": \"2017-11-29\"," +
                        "\"no_dates\": \"true\", \"cfp_start_date\": \"2017-09-09\",\"cfp_end_date\": \"2017-11-29\" " +
                        "\"cfp_no_dates\": \"true\", \"topics\": [1,2,3,4],\"types\": [4,2] " +
                        "\"levels\": [4,3], \"topics\": [3, 4, 5] " +
                        "}")
                .
        when()
                .put( "/api/conference/update/9")
                .
        then().log().all()
                .statusCode(200);
    }

    @Test
    public void getUpcomingConferencesOrganiserTest(){

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().basic(config.organiserUser, config.organiserPassword)
                .cookie("XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .header("X-XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
                .body("{\"title\": \"UpdatedConference\", \"description\": \"Update Conference Test\"," +
                        " \"location\": \"London\", \"start_date\": \"2017-09-09\",\"end_date\": \"2017-11-29\"," +
                        "\"no_dates\": \"true\", \"cfp_start_date\": \"2017-09-09\",\"cfp_end_date\": \"2017-11-29\" " +
                        "\"cfp_no_dates\": \"true\", \"topics\": [1,2,3,4],\"types\": [4,2] " +
                        "\"levels\": [4,3], \"topics\": [3, 4, 5] " +
                        "}")
                .
        when()
                .put( "/api/conference/update/9")
                .
        then().log().all()
                .statusCode(200);
    }
}
