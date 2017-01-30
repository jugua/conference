import manageUsersComponent from './manage-users.component';

export default (app) => {
  app.config(($stateProvider) => {
    $stateProvider
      .state('header.manageUsers', {
        url: '/manage-users',
        template: '<manage-users></manage-users>'
      });
  }).component('manageUsers', manageUsersComponent);
};
