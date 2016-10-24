import myTalksComponent from './my-talks.component';

export default (app) => {
  app.config(($stateProvider) => {
    $stateProvider
      .state('header.tabs.myTalks', {
        url: '/my-talks',
        template: '<my-talks></my-talks>'
      });
  }).component('myTalks', myTalksComponent);
};
