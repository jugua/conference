/* global include_all_modules */
import currentUserService from './current/current';
import menusService from './menus/menus';
import signInService from './sign-in/sign-in';
import signUpService from './sign-up/sign-up';
import forgotPasswordService from './forgot-password/forgot-password';
import talksService from './talk/talk';


export default (app) => {
  include_all_modules([
    currentUserService,
    menusService,
    signInService,
    signUpService,
    forgotPasswordService,
    talksService
  ], app);
};