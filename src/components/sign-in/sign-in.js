import signInComponent from './sign-in.component';

export default (app) => {
  app.config(($stateProvider) => {

    $stateProvider
      .state('header.signIn', {
        url: '/sss',
        template: '<sign-in></sign-in>'
      });
  }).component('signIn', signInComponent);
};
