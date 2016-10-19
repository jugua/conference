import CurrentUserInfo from './get-current-info.service';

export default (app) => {
  app.factory('CurrentUserInfo', CurrentUserInfo);
};