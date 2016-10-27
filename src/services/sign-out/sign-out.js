import SignOut from './sign-out.service';

export default (app) => {
  app.factory('SignOut', SignOut);
};