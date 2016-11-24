import editPasswordComponent from './edit-password.component';

export default (app) => {
  app.config(($stateProvider) => {
    $stateProvider
      .state('editPassword', {
        url: '/edit-password',
        template: '<edit-password></edit-password>'
      });
  }).component('editPassword', editPasswordComponent);
};
