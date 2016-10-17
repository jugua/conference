import Users from './users.service';

export default (app) => {
  app.factory('Users', Users);
};
