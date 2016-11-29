import myTalksComponent from './my-talks.component';

export default (app) => {
  app.config(($stateProvider) => {
    'ngInject';

    $stateProvider
      .state('header.tabs.myTalks', {
        url: '/my-talks',
        template: '<my-talks ng-if="ctrl.resolved"></my-talks>',
        resolve: {
          currentUser: Current => Current.current
        },
        controller: function myTalkPreController(currentUser, Permissions) {
          Permissions.permitted('s', currentUser);
          this.resolved = true;
          this.currentUser = currentUser;
        },
        controllerAs: 'ctrl'
      });
  }).component('myTalks', myTalksComponent);
};