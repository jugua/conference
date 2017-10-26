package com.epam.cm.pages;

import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.At;
import net.thucydides.core.annotations.DefaultUrl;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@At(urls = { "#HOST/#/" })
@DefaultUrl("/#/")
public class HomePage extends AnyPage {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "/html/body/div/header/div[2]/button")
    WebElementFacade yourAccountBtn;


}
