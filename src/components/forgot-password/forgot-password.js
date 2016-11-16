import forgotPasswordComponent from './forgot-password.component';

export default (app) => {
  app.config(($stateProvider) => {
    'ngInject';
    $stateProvider
      .state('header.home.forgotPassword', {
        template: '<forgot-password></forgot-password>'
      });
  }).component('forgotPassword', forgotPasswordComponent);
};
