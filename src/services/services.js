/* global include_all_modules */
import usersService from './users/users';

export default (app) => {
  include_all_modules([usersService], app);
};
