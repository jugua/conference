import Permissions from './permissions.service';

export default (app) => {
  app.factory('Permissions', Permissions);
};
