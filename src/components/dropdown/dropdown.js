import dropdownComponent from './dropdown.component';

export default (app) => {
  app.config(($stateProvider) => {
 // don't forget remove this !!!
    $stateProvider
      .state('dropdown', {
        url: '/dropdown',
        template: '<dropdown></dropdown>'
      });
  }).component('dropdown', dropdownComponent);
};
