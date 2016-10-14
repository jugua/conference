/* global include_all_modules */
import headerComponent from './header/header';
import homeComponent from './home/home';
import signInComponent from './sign-in/sign-in';
import dropdownComponent from './dropdown/dropdown';
import testComponent from './test-route/test-route';

export default (app) => {
  include_all_modules([headerComponent, homeComponent, signInComponent, dropdownComponent, testComponent], app);
};
