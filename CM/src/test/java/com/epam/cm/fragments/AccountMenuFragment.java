package com.epam.cm.fragments;


import net.serenitybdd.core.annotations.ImplementedBy;
import net.serenitybdd.core.pages.WebElementFacade;

@ImplementedBy(AccountMenuFragmentImpl.class)
public interface AccountMenuFragment {

    String getAccountMenuTitle();
    WebElementFacade getSignInButton();
    WebElementFacade getSignOutButton();
    WebElementFacade getLoginField();
    WebElementFacade getPasswordField();
    WebElementFacade getAccountMenuButton();


}
