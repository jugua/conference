import signUpComponent from './sign-up.component';
import SignUpService from './sign-up.service';

export default (app) => {
  app.config(($stateProvider) => {
    'ngInject';

    $stateProvider
      .state('header.sign-up', {
        url: '/sign-up',
        template: '<sign-up></sign-up>'
      });
  }).component('signUp', signUpComponent)
    .service('SignUpService', SignUpService);
};
