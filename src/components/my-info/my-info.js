import myInfoComponent from './my-info.component';

export default (app) => {
  app.config(($stateProvider) => {
    $stateProvider
      .state('header.tabs.myInfo', {
        url: '/my-info',
        template: '<my-info user="ctrl.user"></my-info>',
        resolve: {
          user: (Users) => {
            return Users.current;
          }
        },
        controller: function (user, $scope) {
          if (!user || user.roles.indexOf('s') === -1) {
            $scope.$emit('signInEvent');
            return;
          }
          this.user = user;
        },
        controllerAs: 'ctrl'
      });
  }).component('myInfo', myInfoComponent);
};