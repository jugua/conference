import myInfoComponent from './my-info.component';

export default (app) => {
  app.config(($stateProvider) => {
    'ngInject';

    $stateProvider
      .state('header.tabs.myInfo', {
        url: '/my-info',
        template: '<my-info user="ctrl.currentUser" ng-if="ctrl.resolved"></my-info>',
        resolve: {
          currentUser: Current => Current.current
        },
        controller: function myInfoController(currentUser, Permissions) {
          Permissions.permitted('s', currentUser);
          this.resolved = true;
          this.currentUser = currentUser;
        },
        controllerAs: 'ctrl'
      });
  }).component('myInfo', myInfoComponent);
};