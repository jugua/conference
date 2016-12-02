package com.epam.cm.steps.jbehave;

import com.epam.cm.core.properties.PropertiesController;
import com.epam.cm.core.properties.PropertiesNames;
import com.epam.cm.core.utils.WebDriverSupport;
import org.jbehave.core.annotations.BeforeStory;

import static com.epam.cm.core.properties.PropertiesController.*;
import static com.epam.cm.core.properties.PropertiesNames.SITE_HOST;
import static com.epam.cm.core.properties.PropertiesNames.SITE_PORT;

/**
 * Created by Denys_Shmyhin on 11/16/2016.
 */
public class BaseUrlHook {

    @BeforeStory
    public void urlSetup(){
//        String env_url =  getProperty(SITE_HOST)+":"+
//                getProperty(SITE_PORT);
//        WebDriverSupport.setBaseUrl(env_url);
        WebDriverSupport.maximizeWindow();
    }
}
