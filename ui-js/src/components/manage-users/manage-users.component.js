import template from './manage-users.html';
import controller from './manage-users.controller';

const manageUsersComponent = {
  bindings: {
    users: '='
  },
  template,
  controller
};

export default manageUsersComponent;
