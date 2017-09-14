import service from './conference.service';

export default (app) => {
  app.service('Conference', service);
};