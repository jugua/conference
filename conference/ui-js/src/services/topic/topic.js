import service from './topic.service';

export default (app) => {
  app.service('Topic', service);
};