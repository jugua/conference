import adminPageComponent from './admin-page.component';

export default (app) => {
  app.config(($stateProvider) => {
    $stateProvider
      .state('adminPage', {
        url: '/admin-page',
        template: '<admin-page></admin-page>'
      });
  }).component('adminPage', adminPageComponent);
};
