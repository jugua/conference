import singInComponent from './sing-in.component';

export default (app) => {
  app.config(($stateProvider, $urlRouterProvider) => {
    $urlRouterProvider.otherwise('/');

    $stateProvider
      .state('singIn', {
        url: '/sing-in',
        template: '<sing-in></sing-in>' // Essentially Treats the Home Directive as the Route View.
      });
  }).component('singIn', singInComponent);
};
