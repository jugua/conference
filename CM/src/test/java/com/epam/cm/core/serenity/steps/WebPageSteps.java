package com.epam.cm.core.serenity.steps;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.pages.Pages;

public class WebPageSteps extends SerenitySteps {

    public WebPageSteps(final Pages pages) {
        super(pages);
    }

    @Step
    public String getCurrentPageUrl() {
        return getDriver().getCurrentUrl();
    }

}
