import tabsComponent from './tabs.component';

export default (app) => {
  app.config(($stateProvider) => {
    'ngInject';

    $stateProvider
      .state('header.tabs', {
        template: '<tabs></tabs>'
      });
  }).component('tabs', tabsComponent);
};
