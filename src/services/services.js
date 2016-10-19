/* global include_all_modules */
import usersService from './users/users';
import menusService from './menus/menus';
import signInService from './sign-in/sign-in';
import signUpService from './sign-up/sign-up';
import currrentUserInfoService from './get-current-info/get-current-info';


export default (app) => {
  include_all_modules([
    usersService,
    menusService,
    signInService,
    signUpService,
    currrentUserInfoService
  ], app);
};