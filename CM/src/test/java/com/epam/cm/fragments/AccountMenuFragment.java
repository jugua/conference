package com.epam.cm.fragments;


import net.serenitybdd.core.annotations.ImplementedBy;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.core.pages.WidgetObject;

@ImplementedBy(AccountMenuFragmentImpl.class)
public interface AccountMenuFragment extends WidgetObject {

    void clickAccountMenuButton();
    void clickSignInButton();
    void clickSignOutButton();
    void setLoginField(String login);
    void setPasswordField(String password);

    boolean isAccountMenuUnfolded();
    boolean isSignOutBtnExist();
    boolean isPasswordFieldHighlited();
    boolean isLoginFieldHighlited();

    String getAccountMenuTitle();
    String  getPasswordErrorMsgTxt();


}
