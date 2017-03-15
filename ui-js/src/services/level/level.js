import service from './level.service';

export default (app) => {
  app.service('Level', service);
};