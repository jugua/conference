import SignIn from './sign-in.service';

export default (app) => {
  app.factory('SignIn', SignIn);
};