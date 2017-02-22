import invalidLinkComponent from './invalid-link.component';

export default (app) => {
  app.config(($stateProvider) => {
    'ngInject';

    $stateProvider
      .state('header.invalidLink', {
        url: '/invalid-link',
        template: '<invalid-link></invalid-link>'
      });
  }).component('invalidLink', invalidLinkComponent);
};
