import forgotPasswordComponent from './forgot-password.component';
import ForgotPasswordService from './forgot-password.service';

export default (app) => {
  app.config(($stateProvider) => {
    'ngInject';

    $stateProvider
      .state('header.home.forgotPassword', {
        template: '<forgot-password></forgot-password>'
      });
  }).component('forgotPassword', forgotPasswordComponent)
    .service('ForgotPasswordService', ForgotPasswordService);
};