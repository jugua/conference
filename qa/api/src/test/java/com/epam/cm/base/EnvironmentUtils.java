package com.epam.cm.base;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.util.Properties;

public class EnvironmentUtils {
    public static Config getPropertiesFromConfig(String path){
        Resource resource = new ClassPathResource("/config.properties");

        try{
            Properties props = PropertiesLoaderUtils.loadProperties(resource);
            return new Config(props);
        }
        catch (Exception e) {throw new RuntimeException("oopps");}


    }



}

