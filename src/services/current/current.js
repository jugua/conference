import Current from './current.service';

export default (app) => {
  app.factory('Current', Current);
};
