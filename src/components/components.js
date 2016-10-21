/* global include_all_modules */
import headerComponent from './header/header';
import homeComponent from './home/home';
import signInComponent from './sign-in/sign-in';
import accountPageComponent from './account-page/account-page';
import signUpComponent from './sign-up/sign-up';
import forgotPasswordComponent from './forgot-password/forgot-password';

export default (app) => {
  include_all_modules([
    headerComponent,
    homeComponent,
    signInComponent,
    accountPageComponent,
    signUpComponent,
    forgotPasswordComponent
  ], app);
};
