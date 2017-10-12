package com.epam.cm.base;

import com.epam.cm.jira.Jira;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import java.util.Arrays;

import static io.restassured.RestAssured.when;


public class SimpleBaseTest {

    public static final String TOKEN = "XSRF-TOKEN";
    public static final String XTOKEN = "X-XSRF-TOKEN";

    protected Config config =
            EnvironmentUtils.getPropertiesFromConfig("/config.properties");
    protected Response response;

    @Rule
    public TestWatcher watchman= new TestWatcher() {
        @Override
        protected void finished( Description description) {
            System.out.println(Arrays.asList(
                    description.getAnnotation(Jira.class).value()));
        }
    };


    @Before
    public void setup(){
        RestAssured.defaultParser = Parser.JSON;
        RestAssured.registerParser("text/plain", Parser.JSON);

        response = when().get(config.baseHost);
    }
}