/* global include_all_modules */
import usersService from './users/users';
import menusService from './menus/menus';
import signInService from './sign-in/sign-in';
import signUpService from './sign-up/sign-up';


export default (app) => {
  include_all_modules([
    usersService,
    menusService,
    signInService,
    signUpService
  ], app);
};