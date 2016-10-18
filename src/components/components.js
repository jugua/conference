/* global include_all_modules */
import headerComponent from './header/header';
import homeComponent from './home/home';
import signInComponent from './sign-in/sign-in';
import accountPageComponent from './account-page/account-page';
import tabsComponent from './tabs/tabs';

export default (app) => {
  include_all_modules([headerComponent, homeComponent, signInComponent, accountPageComponent, tabsComponent], app);
};
