import accountPageComponent from './account-page.component';
import editPasswordComponent from './edit-password/edit-password.component';
import editNameComponent from './edit-name/edit-name.component';
import editEmailComponent from './edit-email/edit-email.component';
import EditPasswordService from './edit-password/edit-password.service';
import EditEmailService from './edit-email/edit-email.service';

export default (app) => {
  app.config(($stateProvider) => {
    'ngInject';

    $stateProvider
      .state('header.account', {
        url: '/account',
        template: '<account-page user="ctrl.currentUser" ng-if="ctrl.resolved"></account-page>',
        resolve: {
          currentUser: Current => Current.current
        },
        controller: function myInfoController(currentUser, $rootScope) {
          if (!currentUser) {
            $rootScope.$broadcast('signInEvent');
          }
          this.resolved = true;
          this.currentUser = currentUser;
        },
        controllerAs: 'ctrl'
      });
  }).component('accountPage', accountPageComponent)
    .component('editPassword', editPasswordComponent)
    .component('editName', editNameComponent)
    .component('editEmail', editEmailComponent)
    .service('EditPasswordService', EditPasswordService)
    .service('EditEmailService', EditEmailService);
};
