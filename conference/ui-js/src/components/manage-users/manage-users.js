import manageUsersComponent from './manage-users.component';
import addUserPopupComponent from './add-user-popup/add-user-popup.component';
import AddUserService from './add-user-popup/add-user.service';

export default (app) => {
  app.config(($stateProvider) => {
    'ngInject';

    $stateProvider
      .state('header.manageUsers', {
        url: '/manage-users',
        template: '<manage-users ng-if="ctrl.resolved" users="ctrl.users"></manage-users>',
        resolve: {
          currentUser: Current => Current.current,
          users: ManageUsers => ManageUsers.getAll()
        },
        controller: function Controller(Permissions, currentUser, users) {
          Permissions.permitted('ROLE_ADMIN', currentUser);
          this.resolved = true;
          this.users = users;
        },
        controllerAs: 'ctrl'
      });
  }).component('manageUsers', manageUsersComponent)
    .component('addUserPopup', addUserPopupComponent)
    .service('AddUserService', AddUserService);
};