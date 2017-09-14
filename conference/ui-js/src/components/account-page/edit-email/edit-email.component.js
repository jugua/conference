import template from './edit-email.html';
import controller from './edit-email.controller';

const editEmailComponent = {
  bindings: {
    editing: '=',
    user: '='
  },
  template,
  controller
};

export default editEmailComponent;
