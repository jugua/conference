import testRouteComponent from './test-route.component';

export default (app) => {
  app.config(($stateProvider) => {
    $stateProvider
      .state('header.testRoute', {
        url: '/test-route',
        template: '<test-route></test-route>'
      });
  }).component('testRoute', testRouteComponent);
};
