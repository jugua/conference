import myInfoComponent from './my-info.component';

export default (app) => {
  app.config(($stateProvider) => {
    $stateProvider
      .state('header.tabs.myInfo', {
        url: '/my-info',
        template: '<my-info user="ctrl.currentUser"></my-info>',
        resolve: {
          currentUser: (Users) => {
            return Users.current;
          }
        },
        controller: function (currentUser, $scope) {
          if (!currentUser || currentUser.roles.indexOf('s') === -1) {
            $scope.$emit('signInEvent');
          }
          this.currentUser = currentUser;
        },
        controllerAs: 'ctrl'
      });
  }).component('myInfo', myInfoComponent);
};