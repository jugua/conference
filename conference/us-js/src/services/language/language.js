import service from './language.service';

export default (app) => {
  app.service('Language', service);
};