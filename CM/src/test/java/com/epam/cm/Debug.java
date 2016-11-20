package com.epam.cm;

import net.serenitybdd.jbehave.SerenityStory;

import static com.epam.cm.core.utils.WebdriverPath.bindWebdriverPath;

public class Debug extends SerenityStory {
    public Debug() {

        bindWebdriverPath();
    }
}
