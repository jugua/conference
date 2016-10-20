import tabsComponent from './tabs.component';

export default (app) => {
  app.config(($stateProvider) => {
    $stateProvider
      .state('header.tabs', {
        url: '',
        template: '<tabs></tabs>',
        abstract: true
      });
  }).component('tabs', tabsComponent);
};
