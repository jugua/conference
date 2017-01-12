package com.epam.cm.steps.jbehave;

import com.epam.cm.core.utils.WebDriverSupport;

import org.jbehave.core.annotations.BeforeStory;

public class BaseUrlHook {

    @BeforeStory
    public void urlSetup() {
        // String env_url = getProperty(SITE_HOST)+":"+
        // getProperty(SITE_PORT);
        // WebDriverSupport.setBaseUrl(env_url);
        WebDriverSupport.maximizeWindow();
    }
}
