import listComponent from './list.component';

export default (app) => {
  app.config(($stateProvider, $urlRouterProvider) => {
    $urlRouterProvider.otherwise('/');

    $stateProvider
      .state('list', {
        url: '/list',
        template: '<list></list>' // Essentially Treats the Home Directive as the Route View.
      });
  }).directive('list', listComponent);
};
