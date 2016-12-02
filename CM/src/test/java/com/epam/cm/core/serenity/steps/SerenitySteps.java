package com.epam.cm.core.serenity.steps;


import com.epam.cm.core.page.WebPage;
import net.serenitybdd.core.pages.PageObject;
import net.thucydides.core.pages.Pages;
import net.thucydides.core.steps.ScenarioSteps;

public class SerenitySteps extends ScenarioSteps {

    public SerenitySteps(final Pages pages) {
        super(pages);
    }

    protected <T extends WebPage> T getPage(final Class<T> pageObjectClass) {
        return getPages().getPage(pageObjectClass);
    }

    protected boolean isCurrentPageAt(final Class<? extends PageObject> pageObjectClass) {
        return getPages().isCurrentPageAt(pageObjectClass);
    }

    protected void waitForPageToLoad() {
        getPage(WebPage.class).waitForPageToLoad();
    }

}
