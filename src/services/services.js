/* global include_all_modules */
import usersService from './users/users';
import menusService from './menus/menus';
import signInService from './sign-in/sign-in';


export default (app) => {
  include_all_modules([usersService, menusService, signInService], app);
};