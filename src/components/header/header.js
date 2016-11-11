import controller from './header.controller';
import template from './header.html';

export default (app) => {
  app.config(($stateProvider, $urlRouterProvider) => {
    $urlRouterProvider.otherwise('/');
    $stateProvider
      .state('header', {
        url: '',
        resolve: {
          user: function getCurrent(Current) {
            Current.getInfo();
            return Current.current;
          }
        },
        abstract: true,
        template,
        controller,
        controllerAs: 'ctrl'
      });
  });
};