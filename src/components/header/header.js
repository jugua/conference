import controller from './header.controller';
import template from './header.html';

export default (app) => {
  app.config(($stateProvider, $urlRouterProvider) => {
    $urlRouterProvider.otherwise('/');
    $stateProvider
      .state('header', {
        url: '',
        resolve: {
          user: function getCurrent(Users) {
            const current = Users.getInfo();
            Users.current = current;
            return Users.current;
          }
        },
        abstract: true,
        template,
        controller,
        controllerAs: 'ctrl'
      });
  });
};