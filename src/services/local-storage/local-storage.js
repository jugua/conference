import LocalStorage from './local-storage.service';

export default (app) => {
  app.factory('LocalStorage', LocalStorage);
};
