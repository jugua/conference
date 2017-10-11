package com.epam.cm.base;

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
    protected Response endResponse;

    public static final String XSRF_TOKEN = "XSRF-TOKEN";
    public static final String X_XSRF_TOKEN = "X-XSRF-TOKEN";

    @Before
    public void setup(){

        Resource resource = new ClassPathResource("/config.properties");


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
