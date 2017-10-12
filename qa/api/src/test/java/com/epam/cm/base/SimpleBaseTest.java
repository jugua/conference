package com.epam.cm.base;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import org.junit.Before;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.util.Properties;

import static io.restassured.RestAssured.when;

public class SimpleBaseTest {

    protected Config config;
    protected Response response;
    public static final String TOKEN = "XSRF-TOKEN";
    public static final String XTOKEN = "X-XSRF-TOKEN";

    @Before
    public void setup(){

        Resource resource = new ClassPathResource("/config.properties");
        RestAssured.defaultParser = Parser.JSON;
        RestAssured.registerParser("text/plain", Parser.JSON);


        try{
            Properties props = PropertiesLoaderUtils.loadProperties(resource);
            config = new Config(props);
        }
        catch (Exception e) {throw new RuntimeException("oopps");}


        response =
                when().get(config.baseHost).then().log().all().
                        extract().response();
    }
}
