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

    protected static final String TOKEN = "XSRF-TOKEN";
    protected static final String X_TOKEN = "X-XSRF-TOKEN";

    protected Config config =
            EnvironmentUtils.getPropertiesFromConfig("/config.properties");
    protected Response response;

 @Rule
    public TestWatcher watchman;

    {
        watchman = new TestWatcher() {
            @Override
            protected void finished(Description description) {

                Jira annotation = description.getAnnotation(Jira.class);

                if (annotation == null)
                    throw  new RuntimeException("Executable test has not been marked by Jira annotation");

                System.out.println(Arrays.asList(
                        annotation.value()));
            }
        };
    }

    @Before
    public void setup(){
        RestAssured.defaultParser = Parser.JSON;
        RestAssured.registerParser("text/plain", Parser.JSON);

        response = when().get(config.baseHost);
    }
}
