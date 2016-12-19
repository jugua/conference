import registrationConfirmControler from './registration-confirm.controller';
import RegistrationConfirmService from './registration-confirm.service';

export default (app) => {
  app.config(($stateProvider) => {
    $stateProvider
      .state('header.registrationConfirm', {
        url: '/registrationConfirm/:token',
        controller: registrationConfirmControler
      });
  }).service('RegistrationConfirmService', RegistrationConfirmService);
};
