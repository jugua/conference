import homeComponent from './home.component';

export default (app) => {
  app.config(($stateProvider) => {
    $stateProvider
      .state('header.home', {
        url: '/',
        template: '<home></home>'
      });
  }).component('home', homeComponent);
};
