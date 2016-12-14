import confirmRegistrationControler from './confirm-registration.controller';
import confirmRegistrationService from './confirm-registration.service';

export default (app) => {
  app.config(($stateProvider) => {
    $stateProvider
      .state('header.confirmRegistration', {
        url: '/confirmRegistration/:token',
        controller: confirmRegistrationControler
      });
  }).service('confirmRegistrationService', confirmRegistrationService);
};
