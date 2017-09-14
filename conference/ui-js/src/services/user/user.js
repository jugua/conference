import User from './user.service';

export default (app) => {
  app.service('User', User);
};