import talksComponent from './talks.component';

export default (app) => {
  app.config(($stateProvider) => {
    $stateProvider
      .state('talks', {
        url: '/talks',
        template: '<talks></talks>'
      });
  }).component('talks', talksComponent);
};
