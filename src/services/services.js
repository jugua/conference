/* global include_all_modules */
import usersService from './users/users';
import menusService from './menus/menus';

export default (app) => {
  include_all_modules([usersService, menusService], app);
};
