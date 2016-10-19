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

            console.log(Users.getInfo)
            return Users.getInfo();

        }
        },
        abstract: true,
        template,
        controller,
        controllerAs: 'ctrl'
      });
  });
};