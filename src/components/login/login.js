import loginComponent from './login.component';

export default app => {
  app.config(($stateProvider, $urlRouterProvider) => {
    $urlRouterProvider.otherwise('/');

    $stateProvider
      .state('login', {
        url: '/login',
        template: '<login></login>' //Essentially Treats the Home Directive as the Route View.
      });
  }).directive('login', loginComponent);
}
