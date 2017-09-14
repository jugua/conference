package com.epam.cm.core.page;

import java.util.ArrayList;
import java.util.List;

import com.epam.cm.core.logger.LoggerFactory;
import com.epam.cm.core.properties.PropertiesController;
import com.epam.cm.core.utils.WaitUtils;

import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.epam.cm.core.properties.PropertiesNames.AWAITILITY_AJAX_TIMEOUT;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

public class WebPage extends PageObject {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(WebPage.class);
    private static final Long AJAX_TIMEOUT = Long.valueOf(PropertiesController.getProperty(AWAITILITY_AJAX_TIMEOUT));

    public WebPage(final WebDriver driver) {
        super(driver);
    }

    public boolean isElementPresent(final String xpathLocator) {
        return !getDriver().findElements(By.xpath(xpathLocator)).isEmpty();
    }

    public void waitForElementPresent(final WebElementFacade element) {
        waitForCondition().until(driver -> element.isCurrentlyVisible());
    }

    public void waitForElementDisplayed(final WebElementFacade element, final int timeout) {
        withTimeoutOf(timeout, SECONDS).waitFor(element);
    }

    public void waitForPageToLoad() {
        waitForDocumentReady();
        // waitForAjaxRequestsComplete();
        // waitForAngularRequestComplete();
        waitForAngularV1ToFinish();
    }

    public void moveToElementAction(final WebElementFacade element) {
        final Actions builder = new Actions(this.getDriver());
        builder.moveToElement(element).build().perform();
    }

    public void setFocusOnElement(final WebElementFacade element) {
        final String script = "$(arguments[0]).focus();";
        getJavascriptExecutorFacade().executeScript(script, element);
    }

    public void scrollToElement(final WebElementFacade element) {
        final int yCoordinate = element.getLocation().getY();
        final String script = String.format("window.scrollTo(0,%s);", yCoordinate - 350);
        getJavascriptExecutorFacade().executeScript(script, element);
    }

    public void scrollAndClick(final WebElementFacade element) {
        scrollToElement(element);
        element.click();
    }

    protected <T> List<T> getElementsOrEmptyList(final List<T> elements) {
        final List<T> list = new ArrayList<>();
        try {
            list.addAll(elements);
        } catch (final NoSuchElementException e) {
            LOGGER.debug("No elements are present.", e);
        }
        return list;
    }

    public boolean isElementHighlighted(WebElementFacade element) {
        return element.getAttribute("class").contains("invalid");
    }

    public void acceptModal() {
        getAlert().accept();
        waitForPageToLoad();
    }

    public void declineModal() {
        getAlert().dismiss();
        waitForPageToLoad();
    }

    public void switchToDefaultContent() {
        getDriver().switchTo().defaultContent();
    }

    private void waitForAjaxRequestsComplete() {
        WaitUtils.doWait().atMost(AJAX_TIMEOUT, MILLISECONDS).with().pollInterval(1, SECONDS)
                .until(() -> (Boolean) getJavascriptExecutorFacade()
                        .executeScript("return typeof(jQuery) == 'function' ? (jQuery.active == 0) : true"));
    }

    private void waitForDocumentReady() {
        WaitUtils.doWait().atMost(AJAX_TIMEOUT, MILLISECONDS).with().pollInterval(1, SECONDS)
                .until(() -> (Boolean) getJavascriptExecutorFacade()
                        .executeScript("return document.readyState == 'complete'"));
    }

    // private void waitForAngularRequestComplete() {
    // WaitUtils.doWait().atMost(AJAX_TIMEOUT, MILLISECONDS).with().pollInterval(1, SECONDS)
    // .until(() -> (Boolean) getJavascriptExecutorFacade()
    // .executeScript("return (window.angular != null) && (angular.element(document).injector()!= null) "+
    // "&& (angular.element(document).injector().get('$http').pendingRequests.length === 0)"));
    // }

    public void waitForAngularV1ToFinish() {

        WebDriver driver = getDriver();
        final String query = "window.angularFinished;" + "waitForAngular =function() {"
                + "  window.angularFinished = false;" + "  var el = document.querySelector('body');"
                + "  var callback = (function(){window.angularFinished=1});"
                + "  angular.element(el).injector().get('$browser')."
                + "      notifyWhenNoOutstandingRequests(callback);};";
        try {
            ((JavascriptExecutor) driver).executeScript(query);
            ((JavascriptExecutor) driver).executeScript("waitForAngular()");

            ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver driver) {
                    Object noAjaxRequests = ((JavascriptExecutor) driver)
                            .executeScript("return window.angularFinished;");
                    return "1".equals(noAjaxRequests.toString());
                }
            };
            WebDriverWait wait = new WebDriverWait(driver, 30);
            wait.until(pageLoadCondition);
        } catch (Exception e) {
            LOGGER.error("Unable to load the page correctly");
        }
    }

    public void waitForAngularV2ToFinish() {
        WebDriver driver = getDriver();
        try {
            ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
                @Override
                public Boolean apply(WebDriver driver) {
                    return Boolean.valueOf(((JavascriptExecutor) driver)
                            .executeScript("return (window.angular !== undefined) "
                                    + "&& (angular.element(document).injector() !== undefined) "
                                    + "&& (angular.element(document).injector().get('$http').pendingRequests.length === 0)")
                            .toString());
                }
            };
            WebDriverWait wait = new WebDriverWait(driver, 30);
            wait.until(pageLoadCondition);
        } catch (Exception e) {
            LOGGER.error("Unable to load the page correctly");
        }
    }
}
