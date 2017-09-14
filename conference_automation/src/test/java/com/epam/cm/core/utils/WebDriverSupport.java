package com.epam.cm.core.utils;

import net.serenitybdd.core.annotations.findby.By;
import net.thucydides.core.webdriver.ThucydidesWebDriverSupport;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public final class WebDriverSupport {

    private WebDriverSupport() {
    }

    public static void maximizeWindow() {
        getDriver().manage().window().maximize();
    }

    public static void restartBrowser() {
        ThucydidesWebDriverSupport.closeAllDrivers();
        maximizeWindow();
    }

    public static String getBaseUrl() {
        return ThucydidesWebDriverSupport.getPages().getConfiguration().getBaseUrl();
    }

    public static void setBaseUrl(final String url) {
        ThucydidesWebDriverSupport.getPages().getConfiguration().setDefaultBaseUrl(url);
    }

    public static void clearCookies() {
        getDriver().manage().deleteAllCookies();
    }

    public static void reloadPage() {
        getDriver().navigate().refresh();
    }

    public static void switchToFrame() {
        final WebElement iframe = getDriver().findElement(By.tagName("iframe"));
        getDriver().switchTo().frame(iframe);
    }

    public static void switchToDefaultContent() {
        getDriver().switchTo().defaultContent();
    }

    public static void clearLocalStorage() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript(String.format("window.localStorage.clear();"));
    }

    private static WebDriver getDriver() {
        return ThucydidesWebDriverSupport.getDriver();
    }

}
