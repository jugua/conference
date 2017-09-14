package com.epam.cm.fragments;


import com.epam.cm.dto.AccountButtonDTO;
import net.serenitybdd.core.annotations.ImplementedBy;
import net.serenitybdd.core.pages.WidgetObject;

import java.util.List;
import java.util.Map;

@ImplementedBy(AccountMenuFragmentImpl.class)
public interface AccountMenuFragment extends WidgetObject {



    void clickAccountMenuButton();

    void clickMyInfoButton();

    void clickSignInButton();

    void clickSignOutButton();

    void clickForgotPwLink();

    void clickContinueButton();

    void clickCancelBtn();

    void clickMyTalksBtn();

    void setLoginField(String login);

    void setPasswordField(String password);

    void setEmailForgotPwFieldField(String email);

    void clickSettingsOption();

    void clickMyTalksOrg();

    void clickSaveBtn();

    void clickManageUserBtn();

    void setNewPwForConfirmationEmail(String pw);

    void setConfirmNewPwForConfirmationEmail(String confPw);

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

    public boolean checkSignOutBtnIsLastItem();

    String getAccountMenuTitle();

    String getPasswordErrorMsgTxt();

    String forgotLblText();

    String getLoginErrorMsgTxt();

    String getPopUpNotification();

    String getEmtyEmailMsgTxt();

    String getInvalidEmailMsg();

    String forgotErrorMsg();

    List<AccountButtonDTO> getAccountMenuItems();

}
