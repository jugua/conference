import headerComponent from './header.component';

export default (app) => {
  app.config(($stateProvider, $urlRouterProvider) => {
    //$urlRouterProvider.otherwise('/');

    $stateProvider
      .state('header', {
        url: '/header',
        template: '<header></header>' // Essentially Treats the Home Directive as the Route View.
      });
  }).component('header', headerComponent);
};
