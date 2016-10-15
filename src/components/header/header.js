import controller from './header.controller';
import template from './header.html';

export default (app) => {
  app.config(($stateProvider, $urlRouterProvider) => {
    $urlRouterProvider.otherwise('/');
    $stateProvider
      .state('header', {
        url: '/',
        resolve: {
          Users: 'Users',
          user: function (Users) {
            let Current = new Users();
            var user =  Current.$getCurrentUser();
            return user;
          }
        },
        abstract: true,
        template,
        controller,
        controllerAs: 'ctrl'
      });
  });
};
