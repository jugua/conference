import Users from './users.service.js';

export default (app) => {
  app.factory('Users', Users);
};
