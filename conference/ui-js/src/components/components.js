import headerComponent from './header/header';
import homeComponent from './home/home';
import signInComponent from './sign-in/sign-in';
import accountPageComponent from './account-page/account-page';
import myInfoComponent from './my-info/my-info';
import tabsComponent from './tabs/tabs';
import signUpComponent from './sign-up/sign-up';
import myTalksComponent from './my-talks/my-talks';
import forgotPasswordComponent from './forgot-password/forgot-password';
import userPhotoComponent from './user-photo/user-photo';
import logoutComponent from './logout/logout';
import newTalkComponent from './newtalk/newtalk';
import talksComponent from './talks/talks';
import newPasswordComponent from './new-password/new-password';
import registrationConfirm from './registration-confirm/registration-confirm';
import invalidLink from './invalid-link/invalid-link';
import newEmailConfirm from './new-email-confirm/new-email-confirm';
import manageUsers from './manage-users/manage-users';


export default (app) => {
  include_all_modules([
    headerComponent,
    homeComponent,
    signInComponent,
    accountPageComponent,
    myInfoComponent,
    tabsComponent,
    myTalksComponent,
    signUpComponent,
    forgotPasswordComponent,
    userPhotoComponent,
    newTalkComponent,
    talksComponent,
    logoutComponent,
    newPasswordComponent,
    registrationConfirm,
    invalidLink,
    newEmailConfirm,
    manageUsers,
  ], app);
};