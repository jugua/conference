import signUpComponent from './sign-up.component';

export default (app) => {
  app.config(($stateProvider) => {
    $stateProvider
      .state('signUp', {
        url: '/sign-up',
        template: '<sign-up></sign-up>'
      });
  }).component('signUp', signUpComponent);
};
