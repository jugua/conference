package com.epam.cm.pages;

import com.epam.cm.core.page.WebPage;
import com.epam.cm.fragments.AccountMenuFragment;

import net.serenitybdd.core.annotations.findby.FindBy;

import org.openqa.selenium.WebDriver;

public class AnyPage extends WebPage {

    @FindBy(xpath = "//*[@class='menu-container']")
    AccountMenuFragment accountMenu;

    public AnyPage(WebDriver driver) {
        super(driver);
    }

    public AccountMenuFragment getMenu() {
        return accountMenu;
    }
}
