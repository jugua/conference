import dropdownComponent from './dropdown.component';

export default (app) => {
  app.config(($stateProvider, $urlRouterProvider) => {
    $urlRouterProvider.otherwise('/');

    $stateProvider
      .state('dropdown', {
        url: '/dropdown',
        template: '<dropdown></dropdown>' // Essentially Treats the Home Directive as the Route View.
      });
  }).component('dropdown', dropdownComponent);
};
