package com.epam.cm.fragments;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.core.pages.WidgetObjectImpl;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.pagefactory.ElementLocator;

/**
 *
 */

public class AccountMenuFragmentImpl extends WidgetObjectImpl implements AccountMenuFragment {

    public AccountMenuFragmentImpl(PageObject page, ElementLocator locator, WebElement webElement, long timeoutInMilliseconds) {
        super(page, locator, webElement, timeoutInMilliseconds);
    }

    public AccountMenuFragmentImpl(PageObject page, ElementLocator locator, long timeoutInMilliseconds) {
        super(page, locator, timeoutInMilliseconds);
    }

    @FindBy(xpath = "//*[@class='menu-container']/button")
    private WebElementFacade accountBtn;

    @FindBy(xpath = "//input[@id='sign-in-email']")
    private WebElementFacade emailField;


    @FindBy(xpath = "//input[@id='sign-in-password']")
    private WebElementFacade passwordField;



    @FindBy(xpath = "//sign-in//input[@class='btn sign-in__submit']")
    private WebElementFacade signInBtn;

    @FindBy(xpath = "//*[@class='menu-list']//li[contains(@class,'sign-out')]")
    private WebElementFacade signOutBtn;


    @Override
    public String getAccountMenuTitle() {
        return accountBtn.getText();
    }

    @Override
    public WebElementFacade getSignInButton() {
        return signInBtn;
    }

    @Override
    public WebElementFacade getSignOutButton() {
        return signOutBtn;
    }

    @Override
    public WebElementFacade getLoginField() {
        return emailField;
    }

    @Override
    public WebElementFacade getPasswordField() {
        return passwordField;
    }

    @Override
    public WebElementFacade getAccountMenuButton() {
        return accountBtn;
    }
}
