/* global include_all_modules */
import currentUserService from './current/current';
import menusService from './menus/menus';
import signInService from './sign-in/sign-in';
import signUpService from './sign-up/sign-up';
import forgotPasswordService from './forgot-password/forgot-password';
import talksService from './talk/talk';
import localStorage from './local-storage/local-strorage';


export default (app) => {
  include_all_modules([
    currentUserService,
    menusService,
    signInService,
    signUpService,
    forgotPasswordService,
    talksService,
    localStorage
  ], app);
};