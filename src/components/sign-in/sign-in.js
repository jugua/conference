import signInComponent from './sign-in.component';

export default (app) => {
  app.config(($stateProvider, $urlRouterProvider) => {
    $urlRouterProvider.otherwise('/');

    $stateProvider
      .state('signIn', {
        url: '/sign-in',
        template: '<sign-in></sign-in>' // Essentially Treats the Home Directive as the Route View.
      });
  }).component('signIn', signInComponent);
};
