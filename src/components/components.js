/* global include_all_modules */
import homeComponent from './home/home';

export default (app) => {
  include_all_modules([homeComponent], app);
};
