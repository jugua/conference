package com.epam.cm.pages;

import net.thucydides.core.annotations.At;
import net.thucydides.core.annotations.DefaultUrl;

import org.openqa.selenium.WebDriver;

@At(urls = { "#HOST/#/" })
@DefaultUrl("/#/")
public class HomePage extends AnyPage {

    public HomePage(WebDriver driver) {
        super(driver);
    }

}
