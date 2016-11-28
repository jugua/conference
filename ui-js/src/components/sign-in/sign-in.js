import signInComponent from './sign-in.component';
import signInService from './sign-in.service';

export default (app) => {
  app.component('signIn', signInComponent)
     .service('signInService', signInService);
};