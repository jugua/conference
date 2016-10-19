import myInfoComponent from './my-info.component';

export default (app) => {
  app.config(($stateProvider) => {
    $stateProvider
      .state('header.tabs.myinfo', {
        url: '/my-info',
        template: '<my-info></my-info>'
      });
  }).component('myInfo', myInfoComponent);
};
