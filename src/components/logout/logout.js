import logoutController from './logout.controller';
import logoutService from './logout.service';

export default (app) => {
  app.config(($stateProvider) => {
    $stateProvider
      .state('header.home.logout', {
        controller: logoutController
      });
  }).service('Logout', logoutService);
};
