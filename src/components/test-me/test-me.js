import testMeComponent from './test-me.component';

export default (app) => {
  app.config(($stateProvider, $urlRouterProvider) => {
    $urlRouterProvider.otherwise('/');

    $stateProvider
      .state('testMe', {
        url: '/test-me',
        template: '<test-me></test-me>' // Essentially Treats the Home Directive as the Route View.
      });
  }).directive('testMe', testMeComponent);
};
