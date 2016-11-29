/* global include_all_modules */
import currentUserService from './current/current';
import menusService from './menus/menus';
import talksService from './talk/talk';
import localStorage from './local-storage/local-storage';
import constants from './constants/constants';
import permissionsService from './permissions/permissions';
import tokenInjector from './node-intercepter/node-intercepter';


export default (app) => {
  include_all_modules([
    localStorage,
    constants,
    currentUserService,
    menusService,
    talksService,
    permissionsService,
    tokenInjector
  ], app);
};