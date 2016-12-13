package com.epam.cm.pages;

import com.epam.cm.core.page.WebPage;
import com.epam.cm.fragments.AccountMenuFragment;

import net.serenitybdd.core.annotations.findby.FindBy;

import org.openqa.selenium.WebDriver;

/**
 * Created by Denys_Shmyhin on 11/11/2016.
 */
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
