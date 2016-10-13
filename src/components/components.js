/* global include_all_modules */
import homeComponent from './home/home';
import signInComponent from './sign-in/sign-in';

export default (app) => {
  include_all_modules([homeComponent, signInComponent], app);
};
