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
        controller: function myTalkPreController(currentUser, $scope) {
          'ngInject';
          this.resolved = true;
          if (!currentUser || currentUser.roles.indexOf('s') === -1) {
            $scope.$emit('signInEvent');
          }
          this.currentUser = currentUser;
        },
        controllerAs: 'ctrl'
      });
  }).component('myTalks', myTalksComponent);
};
