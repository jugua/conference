import SignUp from './sign-up.service';

export default (app) => {
  app.factory('SignUp', SignUp);
};