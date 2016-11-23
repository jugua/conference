import accountPageComponent from './account-page.component';
import editPasswordComponent from './edit-password/edit-password.component';

export default (app) => {
  app.config(($stateProvider) => {
    'ngInject';

    $stateProvider
      .state('header.account', {
        url: '/account',
        template: '<account-page></account-page>'
      });
  }).component('accountPage', accountPageComponent)
    .component('editPassword', editPasswordComponent);
};
