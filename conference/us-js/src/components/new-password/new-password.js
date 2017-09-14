import newPasswordComponent from './new-password.component';
import newPasswordService from './new-password.service';

export default (app) => {
  app.config(($stateProvider) => {
    'ngInject';

    $stateProvider
      .state('header.newPassword', {
        url: '/forgotPassword/:tokenId',
        template: '<new-password></new-password>'
      });
  }).component('newPassword', newPasswordComponent)
    .service('newPasswordService', newPasswordService);
};
