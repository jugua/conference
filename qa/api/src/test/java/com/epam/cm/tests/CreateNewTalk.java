package com.epam.cm.tests;

import com.epam.cm.base.EndpointUrl;
import com.epam.cm.base.SimpleBaseTest;
import com.epam.cm.base.TextConstants;
import com.epam.cm.jira.Jira;
import com.epam.cm.utils.JsonLoader;
import io.restassured.http.ContentType;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;

public class CreateNewTalk extends SimpleBaseTest {

    //String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());

    private String validContent  = JsonLoader.asString("CreateNewTalkValidData.json");

    @Test //TODO
    @Jira("6696")
    public void createNewTalk(){

        given()
                .contentType(ContentType.JSON)
                .baseUri(config.baseHost)
                .auth().preemptive().basic(config.speakerUser, config.speakerPassword)
                .cookie(TOKEN, response.cookie(TOKEN))
                .header(X_TOKEN, response.cookie(TOKEN))
                .body(validContent)
                .
        when()
                .post(EndpointUrl.TALK)
                .
        then().log().all()
                .statusCode(200)
                .assertThat()
                .body(TextConstants.ERROR, nullValue(),
                        TextConstants.ID, notNullValue());
    }
}
