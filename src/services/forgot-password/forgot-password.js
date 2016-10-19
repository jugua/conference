import ForgotPassword from './forgot-password.service';

export default (app) => {
  app.factory('ForgotPassword', ForgotPassword);
};