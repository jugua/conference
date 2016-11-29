import tokenInjector from './node-intercepter.service';

export default (app) => {
  app.factory('tokenInjector', tokenInjector);
};
