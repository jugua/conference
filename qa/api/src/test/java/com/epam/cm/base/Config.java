package com.epam.cm.base;

import java.util.Properties;


public class Config {
    public String baseHost;
    public String organiserUser;
    public String organiserPassword;
    public String speakerUser;
    public String speakerPassword;
    public String adminUser;
    public String adminPassword;
    public String wrongUser;
    public String wrongPassword;


    public Config (Properties properties){
        baseHost = properties.getProperty("baseHost");
        organiserUser = properties.getProperty("organiser.user");
        organiserPassword = properties.getProperty("organiser.password");
        speakerUser = properties.getProperty("speaker.user");
        speakerPassword = properties.getProperty("speaker.password");
        adminUser = properties.getProperty("admin.user");
        adminPassword = properties.getProperty("admin.password");
        wrongUser = properties.getProperty("wrong.user");
        wrongPassword = properties.getProperty("wrong.password");
    }

}
