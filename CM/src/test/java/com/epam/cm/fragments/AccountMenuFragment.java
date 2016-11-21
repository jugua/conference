package com.epam.cm.fragments;


import net.serenitybdd.core.annotations.ImplementedBy;
import net.serenitybdd.core.pages.WidgetObject;

@ImplementedBy(AccountMenuFragmentImpl.class)
public interface AccountMenuFragment extends WidgetObject {



    void clickAccountMenuButton();

    void clickSignInButton();

    void clickSignOutButton();

    void clickForgotPwLink();

    void clickContinueButton();

    void clickCancelBtn();

    void setLoginField(String login);

    void setPasswordField(String password);

    void setEmailForgotPwFieldField(String email);

    boolean isAccountMenuUnfolded();

    boolean isSignOutBtnExist();

    boolean isPasswordFieldHighlited();

    boolean isLoginFieldHighlited();

    boolean popUpISPresent();

    boolean forgotLblIsPresent();

    boolean cancelBtnIsPresent();

    boolean continiuBtnIsPresent();

    boolean isEmailForgotPwHighlighted();

    boolean cancelNotifiPopUpBtnisPresent();

    String getAccountMenuTitle();

    String getPasswordErrorMsgTxt();

    String forgotLblText();

    String getLoginErrorMsgTxt();

    String getPopUpNotification();

    String getEmtyEmailMsgTxt();

    String getInvalidEmailMsg();

}
