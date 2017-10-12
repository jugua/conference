package com.epam.cm.base;

import com.epam.cm.jira.Jira;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.util.Properties;

import static io.restassured.RestAssured.when;

public class SimpleBaseTest {

    protected Config config;
    protected Response response;

    @Rule
    public TestWatcher watchman= new TestWatcher() {


        @Override
        protected void finished( Description description) {

            System.out.println(description.getAnnotation(Jira.class).value());


        }
    };

    @Before
    public void setup(){

        Resource resource = new ClassPathResource("/config.properties");


        try{
            Properties props = PropertiesLoaderUtils.loadProperties(resource);
            config = new Config(props);
        }
        catch (Exception e) {throw new RuntimeException("oopps");}


        response =
                when().post(config.baseHost).then().log().all().
                        extract().response();
    }
}
