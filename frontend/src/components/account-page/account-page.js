import accountPageComponent from './account-page.component';

export default (app) => {
  app.config(($stateProvider) => {
    $stateProvider
      .state('header.account', {
        url: '/account',
        template: '<account-page></account-page>'
      });
  }).component('accountPage', accountPageComponent);
};
