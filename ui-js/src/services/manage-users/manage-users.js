import ManageUsers from './manage-users.service';

export default (app) => {
  app.service('ManageUsers', ManageUsers);
};
