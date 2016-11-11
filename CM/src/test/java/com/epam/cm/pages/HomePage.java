package com.epam.cm.pages;

import com.epam.cm.core.page.WebPage;
import com.epam.cm.fragments.AccountMenuFragment;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.At;
import net.thucydides.core.annotations.DefaultUrl;
import org.junit.runners.Parameterized;
import org.omg.CORBA.Any;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.util.Collection;

/**
 *
 */
@At(urls = { "#HOST/#/" })
@DefaultUrl("http://localhost:5000/#/")
public class HomePage extends AnyPage {

    public HomePage(WebDriver driver) {
        super(driver);
    }



}


