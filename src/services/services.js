/* global include_all_modules */
import currentUserService from './current/current';
import menusService from './menus/menus';
import signUpService from './sign-up/sign-up';
import forgotPasswordService from './forgot-password/forgot-password';
import talksService from './talk/talk';
import localStorage from './local-storage/local-storage';


export default (app) => {
  include_all_modules([
    localStorage,
    currentUserService,
    menusService,
    signUpService,
    forgotPasswordService,
    talksService
  ], app);
};