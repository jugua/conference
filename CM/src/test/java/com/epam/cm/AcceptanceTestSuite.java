package com.epam.cm;


import net.serenitybdd.jbehave.SerenityStories;

import static com.epam.cm.core.utils.WebdriverPath.bindWebdriverPath;

public class AcceptanceTestSuite extends SerenityStories {

    public AcceptanceTestSuite(){

        bindWebdriverPath();

    }




}
