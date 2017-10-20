package com.epam.cm.base;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;

import java.util.Properties;

import static org.springframework.core.io.support.PropertiesLoaderUtils.*;

public class EnvironmentUtils {

    public static Config getPropertiesFromConfig(String path){
        Resource resource = new ClassPathResource(path);

        try{
            Properties props = loadProperties(resource);
            Config config = new Config(props);
            String host = System.getProperty("host");

            config.baseHost = (StringUtils.isEmpty(host)) ? config.baseHost : host;

            return config;
        }
        catch (Exception e) {throw new RuntimeException("No way to read the configuration file :" + path);}


    }
}
