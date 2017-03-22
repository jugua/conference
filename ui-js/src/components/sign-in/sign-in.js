import signInComponent from './sign-in.component';
import signInService from './sign-in.service';

export default (app) => {
  app.config(($stateProvider) => {
    'ngInject';

    $stateProvider
      .state('header.sign-in', {
        url: '/sign-in',
        template: '<sign-in display="full"></sign-in>',
      });
  }).component('signIn', signInComponent)
     .service('signInService', signInService);
};