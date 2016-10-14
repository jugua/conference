/* global include_all_modules */
import homeComponent from './home/home';
import headerComponent from './header/header';

export default (app) => {
  include_all_modules([homeComponent, headerComponent], app);
};
