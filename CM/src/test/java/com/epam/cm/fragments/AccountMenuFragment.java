package com.epam.cm.fragments;


import net.serenitybdd.core.annotations.ImplementedBy;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.core.pages.WidgetObject;

@ImplementedBy(AccountMenuFragmentImpl.class)
public interface AccountMenuFragment extends WidgetObject {

    void clickAccountMenuButton();
    void clickSignInButton();
    void clickSignOutButton();
    void clickForgotPwLink();
    void setLoginField(String login);
    void setPasswordField(String password);

    boolean isAccountMenuUnfolded();
    boolean isSignOutBtnExist();
    boolean isPasswordFieldHighlited();
    boolean isLoginFieldHighlited();
    boolean popUpISPresent();
    boolean forgotLblIsPresent();
    boolean cancelBtnIsPresent();
    boolean continiuBtnIsPresent();

    String getAccountMenuTitle();
    String  getPasswordErrorMsgTxt();
    String forgotLblText();


}
