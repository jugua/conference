import newPasswordComponent from './new-password.component';

export default (app) => {
  app.config(($stateProvider) => {
    $stateProvider
      .state('header.newPassword', {
        url: '/forgotPassword/:tokenId',
        template: '<new-password></new-password>'
      });
  }).component('newPassword', newPasswordComponent);
};
