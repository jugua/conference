import SignIn from './sign-in.service.js';

export default (app) => {
  app.factory('SignIn', SignIn);
};