import accountPageComponent from './account-page.component';
import editPasswordComponent from './edit-password/edit-password.component';
import EditPasswordService from './edit-password/edit-password.service';

export default (app) => {
  app.config(($stateProvider) => {
    'ngInject';

    $stateProvider
      .state('header.account', {
        url: '/account',
        template: '<account-page></account-page>'
      });
  }).component('accountPage', accountPageComponent)
    .component('editPassword', editPasswordComponent)
    .service('EditPasswordService', EditPasswordService);
};
